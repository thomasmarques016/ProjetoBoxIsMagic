package objects;

import pt.iscte.poo.utils.Point2D;

public class Box extends GameObject {

    private boolean isStuck = false;

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

    @Override
    public boolean isStuck(){
        return this.isStuck;
    }

    public void fallInHole(){
        this.isStuck = true;
    }
    
}
