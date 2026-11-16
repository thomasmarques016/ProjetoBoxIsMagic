package objects;

import pt.iscte.poo.utils.Point2D;

public class Wall extends GameObject {

    public Wall(Point2D position){
        super(position);
    }

    @Override 
    public String getName(){
        return "wall";
    }

    @Override 
    public int getLayer() {
        return 2;
    }

    public boolean isSolid(){
        return true;
    }
}
