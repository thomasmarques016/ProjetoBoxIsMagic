package objects;

import pt.iscte.poo.utils.Point2D;

public class Wall extends GameObject implements Solid{

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
    
}
