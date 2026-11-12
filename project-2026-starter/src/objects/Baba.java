package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.Random;

public class Baba implements ImageTile {

    private Point2D position;
    private String direction = "r";

    public Baba(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "baba-" + direction;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer() {
        return 25;
    }

    public void move(Direction d) {
        Random rand = new Random();
        position = new Point2D(rand.nextInt(10), rand.nextInt(10));
    }
}
