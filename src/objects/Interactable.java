package objects;

import pt.iscte.poo.game.Room;

public interface Interactable {

    void interact(GameObject other, Room room);
    
}
