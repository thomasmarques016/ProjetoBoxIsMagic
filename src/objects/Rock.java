package objects;

import pt.iscte.poo.utils.Point2D;

public class Rock extends GameObject {
    
    public Rock(Point2D position) {
        super(position);
    }
    
    @Override
    public String getName() {
        return "rock";
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
