package test;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import application.Ship;

public class ShipTest {
	static Ship ship;
	int hit;

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@BeforeClass
	public static void check() {
		ship =new Ship(1,true);
		
	}
	
	@Test
	public void testShipIsAliveSuccess() {
		boolean checkAlive =ship.shipIsAlive();
		assertTrue(checkAlive);
		
	}
	
	@Test
	public void testshipPartHitSuccess() {
		ship.percentageDestroyed++;
		ship.shipPartHit();
		assertEquals(1, ship.percentageDestroyed);
	}
	
	@Test
	public void testShipIsAliveFailure() {
		ship.percentageDestroyed -= 1;
	
		boolean checkAlive =ship.shipIsAlive();
		assertFalse(checkAlive);
	}
	
	
	
	

	
	

}
