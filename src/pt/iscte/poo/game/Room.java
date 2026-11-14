package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.Baba;
import objects.CrackedWall;
import objects.Dirt;
import objects.GameObject;
import objects.Grass;
import objects.Hole;
import objects.Wall;
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
        while(sc.hasNextLine()){
            for(int i = 0; i < rows; i++){ 
                String linha = sc.nextLine();
                for (int j = 0; j< cols; j++){ 
                    Point2D posicao = new Point2D(i, j);
                    r.addObject(new Dirt(posicao)); // vamos por chao em tudo
                    char c = linha.charAt(j);
                    if(c=='X') r.addObject(new Wall(posicao));
                    else if(c=='x') r.addObject(new CrackedWall(posicao));
                    else if(c=='H') r.addObject(new Hole(posicao));
                    else if(c=='g')r.addObject(new Grass(posicao));

                }
            }
            
        }



        }
        catch( FileNotFoundException e){

        }

        Baba baba = new Baba(new Point2D(5, 5));
        r.addObject(baba);

        r.player = baba;
        return r;
    }
    
    public Baba getPlayer() {
        return player;
    }
}