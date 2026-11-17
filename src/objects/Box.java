package objects;

import pt.iscte.poo.utils.Point2D;

public class Box extends GameObject {

    public Box(Point2D position) {
        super(position);
    }
    

    @Override
    public String getName() {
        return "box";
    }


    @Override
    public int getLayer() {
        return 3;
    }

    
    
}
