package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Ghost extends GameObject{

    private String direction;

    public Ghost(Point2D position, String direction) {
        super(position);
        this.direction = direction;
    }

    @Override
    public String getName() {
        return "ghost-" + direction;
    }

    @Override
    public int getLayer() {
        return 25;
    }

    public void setDirection (Direction d){
		this.direction= d.getLetter();
	}

    @Override 
    public String getNameWithoutTraco(){
        return "ghost";
    }
    
}
