package pt.iscte.poo.game;

import java.awt.event.KeyEvent; //codigos das teclas
import java.io.File; 

import objects.Baba; 
import pt.iscte.poo.gui.ImageGUI;  //janela do jogo
import pt.iscte.poo.observer.Observed; //observado(observavel)
import pt.iscte.poo.observer.Observer; //observador
import pt.iscte.poo.utils.Direction; //direcoes(up, down, left, right)

public class GameEngine implements Observer { //motor do jogo, é observador da janela(ImageGUI)

    private static GameEngine INSTANCE = new GameEngine(); //cria motor do jogo
    private static final String ROOMS_DIRECTORY = "rooms"; 

    private Room currentRoom;
    private int lastTickProcessed = 0; //quantos ticks ja foram tratados pelo motor

    private GameEngine() {
        currentRoom = Room.readRoom(new File(ROOMS_DIRECTORY + "/room0.txt")); //vai ver o mapa     VER SE FICA SEMPRE AQ, O MAPA TEM DE MUDAR
        updateGUI(); //desenhar mapa
    }

	public static GameEngine getInstance() {  //retorna o motor de jogo atual
		return INSTANCE;
	}

    @Override
    public void update(Observed source) { //E chamado sempre que acontece algo, como uma tecla ou um tick, recebe a janela atual

        if (ImageGUI.getInstance().wasKeyPressed()) {    //carregaram numa tecla na janela atual?
            int k = ImageGUI.getInstance().keyPressed();   //que tecla carregaram na janela atual??

            if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN) { //A tecla pressionada foi uma seta?
                Direction d = Direction.directionFor(k); //Se sim, guardamos a direcao
                getPlayer().move(d); //mover baba
                updateGUI(); //atualizar a posicao nova do baba, não aparece na janela, ou seja é guardado em memória
            }
        }
        int t = ImageGUI.getInstance().getTicks(); //pergunta a janela quantos ticks ja passaram desde o inicio do jogo
        while (lastTickProcessed < t) { //se ainda não atualizou 
            processTick(); //processar o tick
        }
        ImageGUI.getInstance().update(); //Diz a janela para redesenhar, este sim redesenha na parte gráfica
    }

    private void processTick() {
        lastTickProcessed++;
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
}
