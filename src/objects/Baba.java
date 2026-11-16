package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

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

        Room roomAtual = GameEngine.getInstance().currentRoom;
        Point2D newPosition = getPosition().plus(d.asVector());
        
        setPosition(newPosition);
    }
}
