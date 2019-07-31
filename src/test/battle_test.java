package application;

import static org.junit.Assert.*;

import org.junit.Test;

public class Battle_test {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void IsValidPointTest()
	{
		Board board = new Board();
		boolean actualrestult = board.test_isValidPoint(3,4);
		assertEquals(true,actualrestult);
	}

}
