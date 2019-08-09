package test;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import application.Battle;
import application.Board;

public class BattleTest {
	
	@Test
	public void getBoardInformationTest() {
		Battle bat = new Battle();
		Board b = new Board(false, null);
	
		String info = bat.getBoardInformation(b);
		
		assertNotNull(info);
		
		
		
		
	}

}
