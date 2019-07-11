package application;

import javafx.scene.Parent;

import java.util.ArrayList;

import application.Board.Cell;

/**
 * 
 * This is a POJO class which will help us build the ship Object that needs to be placed on the Board.
 * @author K3
 *
 */
public class Ship extends Parent {
	
	
	ArrayList<Cell> shotCellsOfShips = new ArrayList<Cell>();
	
    public int type;
    public boolean direction = true;

    private int percentageDestroyed;

    /**
     * Constructor for initializing the Object
     * @param type - Length of the Ship
     * @param vertical - Checks for Vertical or Horizontal
     */
    public Ship(int type, boolean vertical) {
    	
    	
        this.type = type;
        this.direction = vertical;
        percentageDestroyed = type;
        
    }

    /**
     * This Method will reduce the health of the ship if the ship has been hit
     */
    public void shipPartHit() {
    	
    	percentageDestroyed--;
    	
    }
    

    /**
     * This Method will help to check if the ship is still Alive or not.
     * @return true or false depending upon the if it is alive or not
     */
    public boolean shipIsAlive() {
        return percentageDestroyed > 0;
    }
}