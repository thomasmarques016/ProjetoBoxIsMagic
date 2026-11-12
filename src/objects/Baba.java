package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.Random;

public class Baba extends GameObject {

    private String direction = "r";

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
        Random rand = new Random();
        setPosition(new Point2D(rand.nextInt(10), rand.nextInt(10)));
    }
}
