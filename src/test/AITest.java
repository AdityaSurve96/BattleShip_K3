package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.AI;

public class AITest {

	static AI ai;
	
	
	@Before
	public void initailse() {
		ai = new AI();
	}
	
	@Test
	public void testGenerate() {
		int x=ai.nextX();
		int y=ai.nextY();
		assertEquals(true, (x>=0 && x<=10) && (y>=0 && y<=10)?true:false);
	}
	
	
	@Test
	public void testGuessingDirection(){
		ai.feedback(true,false);
		ai.generate();
		assertNotNull(ai.stack.isEmpty());
		
	}
	
	@Test
	public void testMove(){
		boolean checkMoves = ai.move(2);
		assertTrue(checkMoves);
		
	}

	
	@Test
	public void testReset() {
		int firstX = ai.nextX();
		ai.reset();
		int secondX = ai.nextX();
		assertNotEquals(firstX, secondX);
	}
	
	
	@After
	public void removeReferences() {
		ai = null;
	}
	
}
