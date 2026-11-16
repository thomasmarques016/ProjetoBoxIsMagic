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

    public void move(Direction d) {

        Room roomAtual = GameEngine.getInstance().getRoom();
        List<ImageTile> objects = roomAtual.getObjects();
        Point2D newPosition = getPosition().plus(d.asVector());

        if(isOutOfBounds(newPosition)) return;
        for(ImageTile o : objects){
            if((o.getPosition().equals(newPosition) && ((GameObject) o).isSolid())){
                return;
            }
        }


        
        setPosition(newPosition);
    }

    public boolean isOutOfBounds(Point2D newPosition){
        int x = newPosition.getX();
        int y = newPosition.getY();
        Room r = GameEngine.getInstance().getRoom();
        int xSala = r.getX();
        int ySala = r.getY();
        if(x < 0 || x >= xSala || y < 0 || y >= ySala){
            return true;
        }
        return false;
    }
}
