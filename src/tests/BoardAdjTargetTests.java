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
	} //currently passes as it uses same logic
	
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
	
	
	
	

}
