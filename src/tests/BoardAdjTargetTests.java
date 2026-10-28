package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {

	//Initialize one board only once
private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");		
		// Initialize will load config files 
		board.initialize();
		board.calcAdj();
	}
	
	@Test
	public void walkwayAdjTest() {
		//Testing to see adj for walkways surrounded by walkways 
		Set<BoardCell> testList = board.getAdjList(7, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(7, 4)));
		assertTrue(testList.contains(board.getCell(7, 2)));
		assertTrue(testList.contains(board.getCell(6, 3)));
		assertTrue(testList.contains(board.getCell(8, 3)));
		
		testList = board.getAdjList(18, 16);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(18, 15)));
		assertTrue(testList.contains(board.getCell(18, 17)));
		assertTrue(testList.contains(board.getCell(17, 16)));
		assertTrue(testList.contains(board.getCell(19, 16)));
		
		testList = board.getAdjList(13, 14);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(13, 15)));
		assertTrue(testList.contains(board.getCell(13, 13)));
		assertTrue(testList.contains(board.getCell(14, 14)));
		assertTrue(testList.contains(board.getCell(12, 14)));
	} //currently fails
	
//Locations within rooms not center. Note, this test is allowed to pass even for failing test. 
	//(Should have empty adjacency list)  
	@Test
	public void testRoomsNotCenter() {
		Set<BoardCell> testList = board.getAdjList(1, 1); //observatory
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(15, 11); //main office
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(12, 17); //hedge maze
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(25, 20); //tower
		assertEquals(0, testList.size());
	} //currently fails as we need to exclude this
	
	@Test
	public void testEdgesAdj() { //Locations that are at each edge of the board
		//corners? they're all rooms and idk about secret passageway tests
		Set<BoardCell> testList = board.getAdjList(7, 0); // top edge
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(7, 1)));
		
		testList = board.getAdjList(25, 7); //right edge
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(24, 7)));
		
		testList = board.getAdjList(0, 17); //left edge
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(1, 17)));
		
		testList = board.getAdjList(18, 25); //bottom edge
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(18, 24)));
		
		
	}
	
	@Test 
	public void testByRooms() { //Locations that are beside a room cell that is not a doorway
		Set<BoardCell> testList = board.getAdjList(8, 3);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(8, 2)));
		assertTrue(testList.contains(board.getCell(8, 4)));
		assertTrue(testList.contains(board.getCell(7, 3)));
		
		testList = board.getAdjList(21, 16);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(20, 16)));
		assertTrue(testList.contains(board.getCell(22, 16)));
		assertTrue(testList.contains(board.getCell(21, 17)));
		
		testList = board.getAdjList(8, 12);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(8, 11)));
		assertTrue(testList.contains(board.getCell(8, 13)));
		
		testList = board.getAdjList(15, 17);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(14, 17)));
		assertTrue(testList.contains(board.getCell(16, 17)));
		assertTrue(testList.contains(board.getCell(15, 16)));
		
	}
	
	@Test
	public void testDoorwayAdj() { //Locations that are doorways
		Set<BoardCell> testList = board.getAdjList(3, 1);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		
		testList = board.getAdjList(13, 8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(12, 8)));
		assertTrue(testList.contains(board.getCell(14, 8)));
		assertTrue(testList.contains(board.getCell(13, 7)));
		assertTrue(testList.contains(board.getCell(13, 11)));
		
		testList = board.getAdjList(17, 2);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(17, 1)));
		assertTrue(testList.contains(board.getCell(18, 2)));
		assertTrue(testList.contains(board.getCell(17, 3)));
		assertTrue(testList.contains(board.getCell(13, 3)));
		
		testList = board.getAdjList(17, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(17, 2)));
		assertTrue(testList.contains(board.getCell(18, 3)));
		assertTrue(testList.contains(board.getCell(17, 4)));
		assertTrue(testList.contains(board.getCell(13, 3)));
		
	
		
	}
	
	@Test
	public void testSecretPassages() { //Locations that are connected by secret passage
		Set<BoardCell> testList = board.getAdjList(22, 3); //center of rooftop
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(21, 6))); //doorway
		assertTrue(testList.contains(board.getCell(1, 3))); //observatory
		
		testList = board.getAdjList(1, 3); //center of observatory
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3, 1))); //doorway
		assertTrue(testList.contains(board.getCell(22, 3))); //rooftop
		
		testList = board.getAdjList(13, 11); //center of main office
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(12, 13))); //doorway
		assertTrue(testList.contains(board.getCell(13, 8))); //doorway
		assertTrue(testList.contains(board.getCell(24, 23))); //tower
		
		testList = board.getAdjList(24, 23); //center of tower
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(21, 24))); //doorway
		assertTrue(testList.contains(board.getCell(13, 11))); //main office
		
		
		
	}
	
	

}
