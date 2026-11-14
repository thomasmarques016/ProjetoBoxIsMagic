package objects;

import pt.iscte.poo.utils.Point2D;

public class CrackedWall extends GameObject{
    
    public CrackedWall(Point2D position) {
        super(position);
    }
    

    @Override
    public String getName() {
        return "cracked-wall";
    }


    @Override
    public int getLayer() {
        return 2;
    }

}
