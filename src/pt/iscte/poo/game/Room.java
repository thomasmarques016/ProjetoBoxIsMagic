package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.Baba;
import objects.Dirt;
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

    public void addObject(ImageTile obj) {
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
        if(sc.hasNextLine()){
            String [] dim = sc.nextLine().split(",", 2);
            int cols = Integer.parseInt(dim[0]);
            int rows = Integer.parseInt(dim[1]);
        }
        while(sc.hasNextLine()){
            String linha = sc.nextLine();
            
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