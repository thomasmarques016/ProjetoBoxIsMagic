package objects;

import pt.iscte.poo.utils.Point2D;

public class Flag extends GameObject implements Pushable{
    
    public Flag(Point2D position) {
        super(position);
    }
    

    @Override
    public String getName() {
        return "flag";
    }


    @Override
    public int getLayer() {
        return 3;
    }

}
