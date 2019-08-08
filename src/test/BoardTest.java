package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.Board;
import application.Ship;
import application.Board.Cell;
import javafx.geometry.Point2D;

/**
 * @author k3
 *
 */

public class BoardTest {

	static Board board;
	static Cell cell;
	
	

	
	
	/**
	*test method for valid point test
	*/
	
	@Test
	public void IsValidPointTest()
	{
		
		Point2D p = new Point2D(3, 4);
		assertEquals(true, board.isValidPoint(p));
	
	}
	
	
	@Test
	public void IsInValidPointTest()
	{
		
		Point2D p = new Point2D(-1, 5);
		assertEquals(false, board.isValidPoint(p));
	
	}
	/**
	 * Test method for invalidpoint.
	 */
	
	
	@Test
	public void IsInValidPointTest2()
	{
		
		Point2D p = new Point2D(0, 20);
		assertEquals(false, board.isValidPoint(p));
	
	}
	/**
	 * Test method for positionship.
	 */
	
	@Test
	public void testPositionShip() {
		Ship s = new Ship(5, true);
		
		boolean placed = board.positionShip(s, 3, 5, false);
		
		assertTrue(placed);
	}
	
	
	@Test
	public void testInvShipPos() {
		Ship s = new Ship(5, true);
		
		boolean placed = board.positionShip(s, 20, 5, false);
		
		assertFalse(placed);
	}
	
	@Test
	public void testHorizShipPos() {
		
		Ship s = new Ship(5, false);
		
		boolean placed = board.positionShip(s, 5, 5, false);
		
		assertTrue(placed);
	}
	
	
	@Test
	public void testInvHorizShipPos() {
		
		Ship s = new Ship(5, false);
		
		boolean placed = board.positionShip(s, 5, 12, false);
		
		assertFalse(placed);
	}
	
	
	@Test
	public void testMovableShip() {
		
		Ship s = new Ship(5, false);
		board.positionShip(s, 5, 4 , false);
		
		
		boolean removed = board.positionShip(s, 5, 4, true);
		
		assertTrue(removed);
	}
	
	@Test
	public void testInvMovableShip() {
		
		Ship s1 = new Ship(3, false);
		board.positionShip(s1, 3, 6 , false);
		
		
		
		
		assertNull(cell.ship);
	}

	@Test
	public void testInvMovableShip2() {
		
		Ship s1 = new Ship(3, false);
		
		
		
		board.positionShip(s1, 5, 6, true);
		
		assertNull(cell.ship);
	}

	

}
