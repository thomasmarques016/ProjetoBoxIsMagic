package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.util.List;
import java.util.Random;

public class Baba extends GameObject {

    private String direction;

    public Baba(Point2D position, String direction) {
        super(position);
        this.direction= direction;
    }

    @Override
    public String getName() {
        return "baba-" + direction;
    }

    @Override
    public int getLayer() {
        return 25;
    }

    public void setDirection (Direction d){
		this.direction= d.getLetter();
	}

    @Override 
    public String getNameWithoutTraco(){
        return "baba";
    }

}

