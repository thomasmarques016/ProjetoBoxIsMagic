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

<<<<<<< HEAD
    @
=======
    @Override
    public int getLayer() {
        return 2;
    }  
>>>>>>> 734cdd22024dba317d4af22d05ebbdfc7f98d9c9
    
    
}
