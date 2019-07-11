package application;

import javafx.scene.Parent;

import java.util.ArrayList;

import application.Board.Cell;
public class Ship extends Parent {
	
	
	ArrayList<Cell> shotCellsOfShips = new ArrayList<Cell>();
	
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