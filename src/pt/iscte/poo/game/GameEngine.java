package pt.iscte.poo.game;

import java.awt.event.KeyEvent; //codigos das teclas
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import objects.Baba; 
import pt.iscte.poo.gui.ImageGUI;  //janela do jogo
import pt.iscte.poo.observer.Observed; //observado(observavel)
import pt.iscte.poo.observer.Observer; //observador
import pt.iscte.poo.utils.Direction; //direcoes(up, down, left, right)

public class GameEngine implements Observer { //motor do jogo, é observador da janela(ImageGUI)

    private static GameEngine INSTANCE = new GameEngine(); //cria motor do jogo
    private static final String ROOMS_DIRECTORY = "rooms";
    private static final String HIGHSCORES_FILE = "highscores.txt"; 
    private int level;

    private Room currentRoom;
    private int lastTickProcessed = 0; //quantos ticks ja foram tratados pelo motor
    private int moves = 0;
    private long tempoInicial;
    private ArrayList<String> melhoresScores= new ArrayList<>();


    private GameEngine() {
        this.level = 0;
        tempoInicial = System.currentTimeMillis();
        currentRoom = Room.readRoom(new File(ROOMS_DIRECTORY + "/room" + level + ".txt")); //vai ver o mapa   
        updateGUI(); //desenhar mapa
        readFile();
    }

	public static GameEngine getInstance() {  //retorna o motor de jogo atual
		return INSTANCE;
	}

    @Override
    public void update(Observed source) { //E chamado sempre que acontece algo, como uma tecla ou um tick, recebe a janela atual

        if (ImageGUI.getInstance().wasKeyPressed()) {    //carregaram numa tecla na janela atual?
            int k = ImageGUI.getInstance().keyPressed();   //que tecla carregaram na janela atual??

        if (k == KeyEvent.VK_R) {          // NOVO: tecla R recomeca o nivel
            restartLevel();
            updateGUI();
            ImageGUI.getInstance().showMessage("Nível reiniciado", "Nível: " + level);
        }

            if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN) { //A tecla pressionada foi uma seta?
                Direction d = Direction.directionFor(k); //Se sim, guardamos a direcao
                //getPlayer().move(d); //mover baba
                if(currentRoom.move(d)){
                    moves++ ;
                }
                if(currentRoom.isWin()){
                    if (level==2) {
                        int tempoFinal = getTotalSeconds();
                        String nome = ImageGUI.getInstance().showInputDialog("Dados do Jogador", "Nome: ");
                        if (nome == null || nome.isBlank()) {
                            nome = "Anonimo";   // se carregar em Cancelar ou nao escrever nada
                        }
                        addmelhoresScores(nome, tempoFinal, moves);
                        writeFile();
                        ImageGUI.getInstance().showMessage("Fim do jogo", "Parabesns! Terminaste todos os niveis \n moves:" + moves +"\n Time:" + tempoFinal);
                        ImageGUI.getInstance().showMessage("Melhores tempos", readScores());
                    }
                    else{
                        levelUp();  
                    }
                }
                updateGUI(); //atualizar a posicao nova do baba, não aparece na janela, ou seja é guardado em memória
            }
        }
        int t = ImageGUI.getInstance().getTicks(); //pergunta a janela quantos ticks ja passaram desde o inicio do jogo
        while (lastTickProcessed < t) { //se ainda não atualizou 
            processTick(); //processar o tick
        }
        ImageGUI.getInstance().update(); //Diz a janela para redesenhar, este sim redesenha na parte gráfica
    }

    private void updateStatus() {
    ImageGUI.getInstance().setStatusMessage("Nivel " + level + " | moves: " + moves + " | Time: " + timeToString() + "| R: reiniciar");
    }

    private void processTick() {
        lastTickProcessed++;
        currentRoom.tick();
        if(!currentRoom.isBabaAlive() || currentRoom.conflito()){
            restartLevel();
            ImageGUI.getInstance().showMessage("Morreste", "Tenta Novamente \n Nível: " + this.level);
        }
        updateStatus();
        updateGUI();
    }

    public void updateGUI() {
        if (currentRoom != null) { //se o mapa nao for null
            ImageGUI.getInstance().clearImages(); //limpa todas as imagens do mapa
            ImageGUI.getInstance().addImages(currentRoom.getObjects()); //redesenha todas as imagens atualizadas
        }
    }


    public Baba getPlayer() {
        return currentRoom.getPlayer(); 
    }

    public void levelUp(){
        ImageGUI.getInstance().showMessage("Nivel concluido", "Passaste o nivel " + level + "!" + "\nmoves: " + moves + "\nTime: " + timeToString());
        level++;
        this.currentRoom = Room.readRoom(new File(ROOMS_DIRECTORY + "/room" + level + ".txt"));
    }

    public void restartLevel(){
        currentRoom = Room.readRoom(new File(ROOMS_DIRECTORY + "/room" + level + ".txt"));
    }

    public int getTotalSeconds() {
    return (int) ((System.currentTimeMillis() - tempoInicial) / 1000);
    }

    public String timeToString() {
    int total = getTotalSeconds();
    int min = total / 60;
    int seg = total % 60;
    if (seg < 10) {
        return min + ":0" + seg;
    }
    return min + ":" + seg;
}

    public void addmelhoresScores(String nome, int tempo, int moves){
        boolean adicionou = false;
        int score = tempo * moves;
        for(int i = 0; i < melhoresScores.size(); i++){
            String [] partes = melhoresScores.get(i).split(",");
            int t = Integer.parseInt(partes[1]);
            int mov = Integer.parseInt(partes[2]);
            int scoreF = t * mov;
        if (score <= scoreF){
            melhoresScores.add(i, nome + "," + tempo + "," + moves);
            adicionou = true;
            break;
        }
        }
        if(!adicionou){
            melhoresScores.add(nome + "," + tempo + "," + moves);
        }
        while (melhoresScores.size()>10) {
            melhoresScores.removeLast();
        }  
    }

    private void writeFile(){
        try{
            PrintWriter pw = new PrintWriter(new File(HIGHSCORES_FILE));
            for ( String m : melhoresScores){
                pw.println(m);
            }
            pw.close();
        }
        catch (FileNotFoundException e) {
        System.err.println("Nao foi possivel guardar os highscores");
    }
    }

    public void readFile (){
        File file = new File(HIGHSCORES_FILE);
        String line = "";
        try{
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                melhoresScores.add(line);
            }
            sc.close();
        }
        catch (FileNotFoundException e){
            // primeira vez que se joga: ainda nao ha pontuacoes
        } 
    }

    private String readScores(){
        String retorno = "POSICAO | NOME | TEMPO | MOVES \n";
        for(int i = 0; i < melhoresScores.size(); i++){
            int pos = i + 1;
            String[] partes = melhoresScores.get(i).split(",");
            retorno += pos + " | " + partes[0] + " | " + partes[1] + " | " + partes[2] + "\n";
        }
        return retorno;
    }

}
