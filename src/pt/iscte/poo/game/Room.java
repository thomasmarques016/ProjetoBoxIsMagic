package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
import objects.Wall;
import objects.Word;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private List<ImageTile> objects;
    private String roomName;
    private Baba player;

    public Room() {
        objects = new ArrayList<>();
    }

    private void setName(String name) {
        roomName = name;
    }

    public String getName() {
        return roomName;
    }

    public void addObject(ImageTile obj) { // alterei para gameObject em vez de ImageTile
        objects.add(obj);
    }

    public List<ImageTile> getObjects() {
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
        }

        for(int i = 0; i < rows; i++){ 
                String linha = sc.nextLine(); // vai ate á ultima linha indicada na primeira linha do ficheiro
                for (int j = 0; j< cols; j++){ 
                    Point2D posicao = new Point2D(j, i); // no enunciado ta que x é a coluna e o y é a linha
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
        Point2D posicao = new Point2D(Integer.parseInt(comp[1]), Integer.parseInt(comp[2]));
        if(comp[0].equals("baba")){
            Baba baba = new Baba(posicao, comp[3]);
            r.addObject(baba);
            r.player=baba;
        } 
        else if (comp[0].equals( "rock")) r.addObject(new Rock(posicao));
        else if (comp[0].equals("bomb")) r.addObject(new Bomb(posicao));
        else if (comp[0].equals("box"))  r.addObject(new Box(posicao));
        else if (comp[0].equals( "flag")) r.addObject(new Flag(posicao));
        /*else if (comp[0].equals("wordBox")) r.addObject(new WordBox(posicao));
        else if (comp[0].equals("wordIs")) r.addObject(new WordIs(posicao));
        else if (comp[0].equals("wordWin")) r.addObject(new WordWin(posicao));
        else if (comp[0].equals("wordMove")) r.addObject(new WordMove(posicao));
        else if (comp[0].equals("wordStop")) r.addObject(new WordStop(posicao));/* */
        }
        sc.close();
        }
        catch( FileNotFoundException e){
            throw new IllegalArgumentException("Ficheiro nao encontrado");
        }
        return r;
    }
    
    public Baba getPlayer() {
        return player;
    }
}