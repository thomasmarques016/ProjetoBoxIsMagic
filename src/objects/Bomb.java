package objects;

import pt.iscte.poo.utils.Point2D;

public class Bomb extends GameObject implements Tickable, Pushable{

    private static final int FUSE_TICKS = 10;
    private int ticksLeft;
    private boolean isActive;

    public Bomb(Point2D position){
        super(position);
        this.ticksLeft = FUSE_TICKS;
        this.isActive = false;
    }

    //public void light(){
    //    if()
    //}

    @Override 
    public String getName(){
        return "bomb";
    }

    @Override 
    public int getLayer(){
        return 3;
    }
    
}
