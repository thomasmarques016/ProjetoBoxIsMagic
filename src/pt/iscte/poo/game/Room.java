package pt.iscte.poo.game;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import objects.Baba;
import objects.Bomb;
import objects.Box;
import objects.CrackedWall;
import objects.Dirt;
import objects.Flag;
import objects.GameObject;
import objects.Grass;
import objects.Hole;
import objects.Rock;
import objects.Tickable;
import objects.Trigger;
import objects.Wall;
import objects.Word;
import objects.WordBox;
import objects.WordIs;
import objects.WordWin;
import objects.WordMove;
import objects.WordStop;
import objects.WordType;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private List<GameObject> objects; //mudamos de ImageTile para GameOject para nao ter que estar constantemente a converter
    private String roomName;
    private Baba player;
    private Set<String> rules = new HashSet<>(); //adicionamos para as rules ativas. E um set para nao haver regras repetidas
    private List<Hole> holes = new ArrayList<>();

    private boolean playerIsAlive = true;
    private int x;
    private int y;

    public Room() {
        objects = new ArrayList<>();
    }

    private void setName(String name) {
        roomName = name;
    }

    public String getName() {
        return roomName;
    }

    public void addObject(ImageTile obj) { 
        objects.add((GameObject)obj);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public static Room readRoom(File f) {
        Room r = new Room();
        r.setName(f.getName());
        try{
        Scanner sc = new Scanner(f);
        int cols = 0;
        int rows = 0;
        if(sc.hasNextLine()){
            String [] dim = sc.nextLine().split(",", 2);
            cols = Integer.parseInt(dim[0].trim());
            rows = Integer.parseInt(dim[1].trim());
            r.x=cols;
            r.y=rows;
        }
        

        for(int i = 0; i < rows; i++){ 
                String linha = sc.nextLine(); // vai ate a ultima linha indicada na primeira linha do ficheiro
                for (int j = 0; j< cols; j++){ 
                    Point2D posicao = new Point2D(j, i); // no enunciado ta que x e a coluna e o y e a linha
                    r.addObject(new Dirt(posicao)); // vamos por chao em tudo
                    char c = linha.charAt(j);
                    if(c=='X') r.addObject(new Wall(posicao));
                    else if(c=='x') r.addObject(new CrackedWall(posicao));
                    else if(c=='H') r.addObject(new Hole(posicao));
                    else if(c=='g')r.addObject(new Grass(posicao));
                    else continue;

                }
            }

        while(sc.hasNextLine()){
        String linha = sc.nextLine().trim(); // a partir dos objetos
        if(linha.isEmpty()) continue;
        String [] comp = linha.split(",");
        try{

            Point2D posicao = new Point2D(Integer.parseInt(comp[1]), Integer.parseInt(comp[2]));
            GameObject obj = createObject(comp[0], posicao, comp);
            r.addObject(obj);
            if(obj instanceof Baba){
                r.player = (Baba) obj;

            }

        }
        catch(NumberFormatException | IndexOutOfBoundsException e){
            System.err.println("Erro na linha: " + linha);
        }
        }
        sc.close();
        }
        catch( FileNotFoundException e){
            throw new IllegalArgumentException("Ficheiro nao encontrado");
        }
        r.createHoles();
        r.updateRules(); // podem ja estar regras formadas sem nenhum movimento
        return r;
    }

    private static GameObject createObject(String name, Point2D pos, String[] comp){
        switch (name) {
            case "baba":     return new Baba(pos, comp[3]);
            case "rock":     return new Rock(pos);
            case "bomb":     return new Bomb(pos);
            case "box":      return new Box(pos);
            case "flag":     return new Flag(pos);
            case "wordBox":  return new WordBox(pos);
            case "wordIs":   return new WordIs(pos);
            case "wordWin":  return new WordWin(pos);
            case "wordMove": return new WordMove(pos);
            case "wordStop": return new WordStop(pos);
        
            default:
                throw new IllegalArgumentException("Objeto desconhecido: " + name);
        }
    }

    public int getX(){ // dimensao da sala
        return this.x;
    }

    public int getY(){ // dimensao da sala
         return this.y;
    }
    
    public Baba getPlayer() {
        return player;
    }



    public boolean move(Direction d) {
        boolean andou = false;
        Baba baba = getPlayer(); //vai buscar o player
        baba.setDirection(d); //vira o baba para a direcao para onde o mesmo vai andar
        if(moveObject(baba, d)) andou = true; //comeca o movimento recursivo do Baba(vai empurar objetos pushable) para direcao 
        updateRules(); //apos cada movimento, verifica todas as regras da sala
        return andou;
    }
        
      
    public boolean moveObject(GameObject gameObject, Direction d){ //funcao recusiva

        Point2D newPosition = gameObject.getPosition().plus(d.asVector()); //calcula a nova posicao

        if(isBlocked(newPosition)){ //verifica se a nova posicao esta fora dos limites do mapa e se na nova posicao existe algum objeto solido ou com a regra isStop ativa
            return false; //nao faz o movimento
        }

        for(GameObject o : getPushables(newPosition)){ //percorre todos os objetos que podem ser empurrados na nova posicao
            boolean conseguiu = moveObject(o, d); //chamada recursiva: tenta empurrar o objeto da frente na mesma direcao

            if(!conseguiu){ //se o objeto da frente nao se conseguir mover(ex: bateu numa parede)
                return false; //cancela o movimento desse obj e consequentemente dos de tras
            }

            if(gameObject instanceof Trigger){ //ve se foi um trigger que empurrou o objeto
                if(o instanceof Bomb){ //se for uma bomba
                    ((Bomb)o).acender(); //ativa a bomba
                } 
        }
    }
        gameObject.setPosition(newPosition); // como o objeto pode mover, atualiza a posicao do objeto
        if(hasHole(newPosition)) fallInHole(gameObject);
        return true; //confirma que o objeto se moveu
    }

    public boolean isBlocked(Point2D pos){
        if(isOutOfBounds(pos)) return true; //se sair do mapa e uma posicao bloqueada
        for(GameObject o : objects){
            if((o.getPosition().equals(pos) && !o.isStuck() && (o.isSolid() || hasRule(o.getNameWithoutTraco(), "is", "stop")))){
                return true;
            }
        }
        return false;
    }

    private List<GameObject> getPushables(Point2D pos){ //vai buscar todos os objetos que podem ser empuarrados numa determinada posicao
        List<GameObject> pushables = new ArrayList<>(); //cria a lista que vai guardar os objetos empurraveis
        for(GameObject o : objects){ 
            if(o.getPosition().equals(pos) && !o.isStuck() && (o.isPushable() || hasRule(o.getNameWithoutTraco(), "is", "move"))){
                pushables.add((GameObject) o);
            }
        }
        return pushables;
    }

    public boolean isOutOfBounds(Point2D newPosition){ //verificar se a nova posicao nao sai do mapa
        int xPos = newPosition.getX(); 
        int yPos = newPosition.getY();
        int xSala = this.getX();
        int ySala = this.getY();
        if(xPos < 0 || xPos >= xSala || yPos < 0 || yPos >= ySala){
            return true;
        }
        return false;
    }

    public void checkRule(Point2D pos, Direction d){ //vai ver se o mapa existe a regra se sim adiciona-a a lista de regras

        Word verb = getObjWord(pos); //vai buscar a palavra do meio
        Word before = getObjWord(pos.plus(d.asVector())); //vai buscar a palavra no bloco antes
        Word after = getObjWord(pos.plus((d.opposite().asVector()))); //vai buscar a palavra no bloco depois, utilizando o opposite
            
        if(before != null && after != null && before.tipo() == WordType.SUJEITO && after.tipo() == WordType.PROPRIEDADE){
            rules.add(before.getWord() + verb.getWord() + after.getWord()); //se formar uma frase adiciona regra a lista
        }
    }

    public void updateRules(){ //chama o checkRule para ver se existe alguma regra a adicionar

        rules.clear();   // apaga as regras antigas antes de voltar a ler, uma vez que ao formar uma regra podemos desfazer outra

        for(GameObject o : objects){ //vamos percorrer todos os objetos do mapa para ver onde ha algum verbo
            if (isWord(o) && ((Word) o).tipo() == WordType.VERBO) { 
                checkRule(o.getPosition(), Direction.LEFT); //vai ver se na linha horizontal ha regra
                checkRule(o.getPosition(), Direction.UP); //vai ver se na linha vertical ha regra
            }
        }
    }

    public boolean conflito(){
        boolean stop = hasRule("box", "is", "stop");
        boolean move = hasRule("box", "is", "move");
        boolean win  = hasRule("box", "is", "win");
        return (stop && move) || (move && win);
    }

    public boolean isWord(GameObject obj){
        return obj instanceof Word;
    } 

    public Word getObjWord(Point2D pos){
        for(GameObject o : objects){
            if (o.getPosition().equals(pos) && isWord(o)) {
               return (Word) o; 
            }
        }
        return null;
    }

    public boolean hasRule(String name, String connecter, String rule){ //verifica se existe a regra na lista
        return rules.contains(name + connecter + rule);
    }

    public boolean isWin(){
        for(GameObject o : objects){
            if(o.getPosition().equals(getPlayer().getPosition()) && (o instanceof Flag || hasRule(o.getNameWithoutTraco(), "is", "win"))){
                return true;
            }
        }
        return false;
    }

    public void explodeBomb(Point2D pos){
        List<Point2D> posAExplodir = pos.getWideNeighbourhoodPoints();
        List<GameObject> objARemover = new ArrayList<>();
        List<GameObject> objASubstituir = new ArrayList<>();

        posAExplodir.add(pos);

        //adicionar a lista os objetos que vamos remover e substituir as Wall por CrackedWalls
        for(GameObject o : objects){
            if(posAExplodir.contains(o.getPosition()) && !(o instanceof Dirt)){
                if(o instanceof Wall){
                    objASubstituir.add(new CrackedWall(o.getPosition()));
                }
                objARemover.add(o);
            }
        }
        if(posAExplodir.contains(getPlayer().getPosition())){
            this.playerIsAlive = false;
        }
        this.objects.removeAll(objARemover);
        this.objects.addAll(objASubstituir);
        updateRules();

    }

    public boolean isBabaAlive(){
        return this.playerIsAlive;
    }

    public void tick(){
        List<Tickable> areGoingToExplode = new ArrayList<>();

        for(GameObject o : objects){
            if(o instanceof Tickable){
                ((Tickable)o).tick();
            }

            if(o instanceof Bomb && ((Bomb)o).isGoingToExplode()){
                areGoingToExplode.add((Bomb)o);
            }
        }
        for(Tickable b : areGoingToExplode){
            explodeBomb(((Bomb)b).getPosition());
        }
    }

    /*private GameObject getObj(Point2D pos){
        for(GameObject o : objects){
            if(o.getPosition().equals(pos)){
                return o;
            }
        }
        return null;
    } */

    public boolean hasHole(Point2D pos){
        for(Hole h: holes){
            if(h.getPosition().equals(pos)) return true;
        }
        return false;
    }

    public void createHoles(){
        for( GameObject o: objects)
            if(o instanceof Hole) holes.add((Hole) o);
    }

    public void fallInHole(GameObject obj){
        if(obj instanceof Box){
            ((Box) obj).fallInHole();
            holes.remove(getHole(obj.getPosition())); 
        } 
        else if(obj == player){
            playerIsAlive = false;
        }
        else {
            objects.remove(obj);
        }
    }

    public Hole getHole( Point2D pos){
        for(Hole h: holes){
            if(h.getPosition().equals(pos)) {
                return h;
            }
        }
        return null;
    }
}