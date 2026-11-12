package objects;

import pt.iscte.poo.utils.Point2D;

public class Grass extends GameObject{

    public Grass(Point2D position) {
        super(position);
    }
    

    @Override
    public String getName() {
        return "grass";
    }


    @Override
    public int getLayer() {
        return 1;
    }
}
