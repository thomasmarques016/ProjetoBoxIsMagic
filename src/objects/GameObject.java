package objects;


import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameObject implements ImageTile{

    private Point2D position;

    public GameObject(Point2D position){
        this.position = position;
    }

    @Override
    public Point2D getPosition(){
        return this.position;
    }

    public void setPosition(Point2D p){
        this.position = p;
    }

    @Override 
    public abstract String getName();

    @Override 
    public abstract int getLayer();
}
