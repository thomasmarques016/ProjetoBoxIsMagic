package objects;

import pt.iscte.poo.utils.Point2D;

public class Bomb extends GameObject implements Tickable{

    private static final int FUSE_TICKS = 10;
    private int ticksLeft;
    private boolean isActive;

    public Bomb(Point2D position){
        super(position);
        this.ticksLeft = FUSE_TICKS;
        this.isActive = false;
    }

    

    @Override 
    public String getName(){
        return "bomb";
    }

    @Override 
    public int getLayer(){
        return 3;
    }

    @Override
    public boolean isPushable(){
        return true;
    }

    @Override 
    public void tick(){
        if(this.isActive && this.ticksLeft > 0)
        this.ticksLeft--;
    }

    public boolean isGoingToExplode(){
        return this.isActive && this.ticksLeft == 0;
    }

    public void acender(){
        this.isActive = true;
    }
    
}
