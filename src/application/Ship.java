package application;

import javafx.scene.Parent;

public class Ship extends Parent {
	
	
    public int type;
    public boolean direction = true;

    private int percentageDestroyed;

    public Ship(int type, boolean vertical) {
    	
    	
        this.type = type;
        this.direction = vertical;
        percentageDestroyed = type;
        
    }

    public void shipPartHit() {
    	
    	percentageDestroyed--;
    	
    }

    public boolean shipIsAlive() {
        return percentageDestroyed > 0;
    }
}