package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Dirt implements GameObject {

    public Dirt(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "dirt";
    }

    @Override
    public Point2D getPosition() {
        super.getPosition();
    }

    @Override
    public int getLayer() {
        return 5;
    }
}
