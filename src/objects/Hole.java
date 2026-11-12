package objects;

import pt.iscte.poo.utils.Point2D;

public class Hole extends GameObject {
    public Hole(Point2D position) {
        super(position);
    }
    

    @Override
    public String getName() {
        return "hole";
    }


    @Override
    public int getLayer() {
        return 1;
    }
}
