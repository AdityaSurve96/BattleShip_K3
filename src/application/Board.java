package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class Board extends Parent {


	private VBox rows = new VBox();

	private boolean opponent = false;


	public int amountOfships = 5;

	/**
	 * 
	 * @param opponent
	 * @param EventHandler
	 */

	public Board(boolean opponent, EventHandler<? super MouseEvent> handler) {

		this.opponent = opponent;
		for (int y = 0; y < 10; y++) {

			HBox row = new HBox();

			for (int x = 0; x < 10; x++) {

				Cell c = new Cell(x, y, this);
				c.setOnMouseClicked(handler);
				row.getChildren().add(c);
			}

			rows.getChildren().add(row);
		}

		getChildren().add(rows);
	}

	/**
	 * 
	 * @param ship
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean positionShip(Ship ship, int x, int y) {
		//Considering the direction for placement if true then placement of ship is vertical else its in the horizontal direction.

		if (validPlacementShip(ship, x, y)) {
			int length = ship.type;

			if (ship.direction) {
				//horizontal thus considering Y coordinates for ship placement of various type.
				for (int i = y; i < y + length; i++) {

					Cell cell = getCell(x, i);
					cell.ship = ship;
					// Appropriate Placement of ships i.e Checking if the placement is on opponent grid then placement should not happen else place the type of ship specified on player 1 grid.
					if (!opponent) {
						cell.setFill(Color.GREEN);
						cell.setStroke(Color.BLACK);
					}
				}
			}
			else {
				//horizontal thus considering X coordinates for ship placement of various type.

				for (int i = x; i < x + length; i++) {

					Cell cell = getCell(i, y);
					cell.ship = ship;

					if (!opponent) {

						cell.setFill(Color.GREEN);
						cell.setStroke(Color.BLACK);

					}
				}
			}

			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Cell getCell(int x, int y) {

		return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);

	}



	private Cell[] checkAndGetNeighbors(int x, int y) {

		Point2D[] points = new Point2D[] {
				new Point2D(x - 1, y),
				new Point2D(x + 1, y),
				new Point2D(x, y - 1),
				new Point2D(x, y + 1)
		};

		List<Cell> neighbors = new ArrayList<Cell>();

		for (Point2D p : points) {
			if (isValidPoint(p)) {
				neighbors.add(getCell((int)p.getX(), (int)p.getY()));
			}
		}

		return neighbors.toArray(new Cell[0]);
	}

	/**
	 * Checking constraints i.e. all cells that form ships are within the grids, also tracking its associated neighboring cells are within the grid for further hitting related cells.
	 * @param ship it takes type(size) of ship along with direction whether the ship placed is vertical or horizontal.
	 * @param x X coordinate for placement of ship
	 * @param y Y coordinate for placement of ship
	 * @return
	 */
	private boolean validPlacementShip(Ship ship, int x, int y) {

		int length = ship.type;

		if (ship.direction) {
			//Checking the ship placement validity for vertical ships.
			for (int i = y; i < y + length; i++) {
				if (!isValidPoint(x, i))
					return false;

				Cell cell = getCell(x, i);
				if (cell.ship != null)
					return false;

				for (Cell neighbor : checkAndGetNeighbors(x, i)) {
					if (!isValidPoint(x, i))
						return false;

					if (neighbor.ship != null)
						return false;
				}
			}
		}
		else {
			//Checking the ship placement validity for horizontal ships.
			for (int i = x; i < x + length; i++) {
				if (!isValidPoint(i, y))
					return false;

				Cell cell = getCell(i, y);
				if (cell.ship != null)
					return false;

				for (Cell neighbor : checkAndGetNeighbors(i, y)) {
					if (!isValidPoint(i, y))
						return false;

					if (neighbor.ship != null)
						return false;
				}
			}
		}

		return true;
	}

	/**
	 * 
	 * @param point takes x and y coordinate of the ship together as a 2D point and checks their placement validity i.e if they are properly placed within the grid,two ships coordinates do not interfere etc.
	 * @return
	 */
	private boolean isValidPoint(Point2D point) {
		return isValidPoint(point.getX(), point.getY());
	}

	/**
	 * 
	 * @param x X coordinate of ship
	 * @param y Y coordinate of ship
	 * @return
	 */
	private boolean isValidPoint(double x, double y) {
		return x >= 0 && x < 10 && y >= 0 && y < 10;
	}

	
	public class Cell extends Rectangle {

		public int row, col;

		public Ship ship = null;

		public boolean targetHit = false;

		private Board board;

		/**
		 * 
		 * @param x X coordinate of ship 
		 * @param y Y coordinate of ship
		 * @param board checks for opponent or player 1 board and accordingly does further mouse events for playing the game.
		 */
		public Cell(int x, int y, Board board) {

			super(30, 30);
			this.row = x;
			this.col = y;
			this.board = board;

			setFill(Color.WHITE);
			setStroke(Color.BLACK);

		}

		/**
		 * 
		 * @return
		 */
		public boolean shoot() {
			File hitRate = new File(".");

			Image hitFile=null;
			try {
				hitFile = new Image("file:///"+hitRate.getCanonicalFile()+"/hitShip.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			targetHit = true;
			setFill(Color.BLACK);

			if (ship != null) {


				ship.shipPartHit();
				ship.shotCellsOfShips.add(this);
				setFill(new ImagePattern(hitFile));


				if (!ship.shipIsAlive()) {
					for (Cell c : ship.shotCellsOfShips) {
						
                		c.setFill(Color.RED);
					}
                    board.amountOfships--;
                    ship.shotCellsOfShips.clear();
				}
				return true;
			}

			return false;
		}
	}
}