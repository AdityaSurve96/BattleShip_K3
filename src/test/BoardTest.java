package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import application.Board;
import application.Ship;
import application.Board.Cell;
import javafx.geometry.Point2D;

public class BoardTest {

	static Board board;
	static Cell cell;
	
	
	@BeforeClass
	public static void check() {
		board = new Board(false, null);
	
		cell = new Cell(3, 4,board);
		
	
	}
	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void IsValidPointTest()
	{
		
		
		Point2D p = new Point2D(3, 4);
		assertEquals(true, board.isValidPoint(p));
	
	}
	public void IsInValidPointTest()
	{
		
		Point2D p = new Point2D(-1, 5);
		assertEquals(false, board.isValidPoint(p));
	
	}
	public void IsInValidPointTest2()
	{
		
		Point2D p = new Point2D(0, 20);
		assertEquals(false, board.isValidPoint(p));
	
	}
	
	
	

}
