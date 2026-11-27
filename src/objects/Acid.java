package objects;

import pt.iscte.poo.utils.Point2D;

public class Acid extends GameObject{

    public Acid(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "acid";
    }

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public boolean isPushable(){
        return true;
    }
    
}
