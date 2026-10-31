package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {

	//Initialize one board only once
private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		board.calcAdj();
	}
	
	@Test
	public void walkwayAdjTest() {
		//Testing to see adj for walkways surrounded by walkways 
		Set<BoardCell> testList = board.getAdjList(16 ,18);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(15, 18)));
		assertTrue(testList.contains(board.getCell(17, 18)));
		assertTrue(testList.contains(board.getCell(16, 17)));
		assertTrue(testList.contains(board.getCell(16, 19)));
		
		testList = board.getAdjList(7, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(7, 4)));
		assertTrue(testList.contains(board.getCell(7, 2)));
		assertTrue(testList.contains(board.getCell(6, 3)));
		assertTrue(testList.contains(board.getCell(8, 3)));
		
		
		testList = board.getAdjList(14, 13);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(15, 13)));
		assertTrue(testList.contains(board.getCell(13, 13)));
		assertTrue(testList.contains(board.getCell(14, 14)));
		assertTrue(testList.contains(board.getCell(14, 12))); 
	} 
	
//Locations within rooms not center. Note, this test is allowed to pass even for failing test. 
	//(Should have empty adjacency list)  
	@Test
	public void testRoomsNotCenter() {
		Set<BoardCell> testList = board.getAdjList(1, 1); //observatory
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(11, 15); //main office
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(17, 12); //hedge maze
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(20, 25); //tower
		assertEquals(0, testList.size());
	} //currently fails as we need to exclude this
	
	@Test
	public void testEdgesAdj() { //Locations that are at each edge of the board
		//corners? they're all rooms and idk about secret passageway tests
		Set<BoardCell> testList = board.getAdjList(0, 7); // top edge
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(1, 7)));
		
		testList = board.getAdjList(7, 25); //right edge
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(7, 24)));
		
		testList = board.getAdjList(17, 0); //left edge
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(17, 1)));
		
		testList = board.getAdjList(25, 18); //bottom edge
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(24, 18)));
		
		
	}
	
	@Test 
	public void testByRooms() { //Locations that are beside a room cell that is not a doorway
		Set<BoardCell> testList = board.getAdjList(3, 8);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(2, 8)));
		assertTrue(testList.contains(board.getCell(4, 8)));
		assertTrue(testList.contains(board.getCell(3, 7)));
		
		testList = board.getAdjList(16, 21);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(16, 20)));
		assertTrue(testList.contains(board.getCell(16, 22)));
		assertTrue(testList.contains(board.getCell(17, 21)));
		
		testList = board.getAdjList(12, 8);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(11, 8)));
		assertTrue(testList.contains(board.getCell(13, 8)));
		
		testList = board.getAdjList(17, 15);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(17, 14)));
		assertTrue(testList.contains(board.getCell(17, 16)));
		assertTrue(testList.contains(board.getCell(16, 15)));
		
	}
	
	@Test
	public void testDoorwayAdj() { //Locations that are doorways
		Set<BoardCell> testList = board.getAdjList(1, 3);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		
		testList = board.getAdjList(8, 13);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(8, 12)));
		assertTrue(testList.contains(board.getCell(8, 14)));
		assertTrue(testList.contains(board.getCell(7, 13)));
		assertTrue(testList.contains(board.getCell(11, 13)));
		
		testList = board.getAdjList(2, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(1, 17)));
		assertTrue(testList.contains(board.getCell(2, 18)));
		assertTrue(testList.contains(board.getCell(3, 17)));
		assertTrue(testList.contains(board.getCell(3, 13)));
		
		testList = board.getAdjList(3, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 17)));
		assertTrue(testList.contains(board.getCell(3, 18)));
		assertTrue(testList.contains(board.getCell(4, 17)));
		assertTrue(testList.contains(board.getCell(3, 13)));
		
	
		
	}
	
	@Test
	public void testSecretPassages() { //Locations that are connected by secret passage
		Set<BoardCell> testList = board.getAdjList(3, 22); //center of rooftop
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(6, 21))); //doorway
		assertTrue(testList.contains(board.getCell(3, 1))); //observatory
		
		
		testList = board.getAdjList(11, 13); //center of main office
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(13, 12))); //doorway
		assertTrue(testList.contains(board.getCell(8, 13))); //doorway
		assertTrue(testList.contains(board.getCell(23, 24))); //tower
		
		testList = board.getAdjList(23, 24); //center of tower
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(24, 21))); //doorway
		assertTrue(testList.contains(board.getCell(11, 13))); //main office
		
		
		
	}
	
	//Target tests
	
	@Test
	public void testTargetsWalkwayShort() { //testing roll of 1 at several squares
				board.calcTargets(board.getCell(3, 7), 1);
				Set<BoardCell> targets= board.getTargets(); //surrounded by walkways
				assertEquals(4, targets.size());
				assertTrue(targets.contains(board.getCell(4, 7)));
				assertTrue(targets.contains(board.getCell(2, 7)));	
				assertTrue(targets.contains(board.getCell(3, 8)));
				assertTrue(targets.contains(board.getCell(3, 6)));
				
				board.calcTargets(board.getCell(20, 8), 1); //next to a room
				targets= board.getTargets();
				assertEquals(3, targets.size());
				assertTrue(targets.contains(board.getCell(20, 7)));
				assertTrue(targets.contains(board.getCell(21, 8)));
				assertTrue(targets.contains(board.getCell(19, 8)));	
				
				board.calcTargets(board.getCell(17, 25), 1); //by edge with walls
				targets= board.getTargets();
				assertEquals(1, targets.size());
				assertTrue(targets.contains(board.getCell(17, 24)));
	
	}
	
	// Tests rolls of 3
	@Test
	public void testTargetsWalkwaysMed() {
		// Walkway
		board.calcTargets(board.getCell(7, 3), 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(7, 2)));
		assertTrue(targets.contains(board.getCell(6, 1)));
		assertTrue(targets.contains(board.getCell(7, 0)));
		assertTrue(targets.contains(board.getCell(8, 1)));
		assertTrue(targets.contains(board.getCell(6, 3)));
		assertTrue(targets.contains(board.getCell(4, 3)));
		assertTrue(targets.contains(board.getCell(7, 4)));
		assertTrue(targets.contains(board.getCell(8, 5)));
		assertTrue(targets.contains(board.getCell(7, 6)));
		assertTrue(targets.contains(board.getCell(8, 3)));
		assertTrue(targets.contains(board.getCell(12, 3)));
		
		// Next to a room
		board.calcTargets(board.getCell(7, 20), 3); //next to a room
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(6, 20)));
		assertTrue(targets.contains(board.getCell(7,21)));
		assertTrue(targets.contains(board.getCell(6,22)));
		assertTrue(targets.contains(board.getCell(7,23)));
		assertTrue(targets.contains(board.getCell(7,17)));
		assertTrue(targets.contains(board.getCell(3,22)));
		assertTrue(targets.contains(board.getCell(8,18)));
		assertTrue(targets.contains(board.getCell(6,18)));
		assertTrue(targets.contains(board.getCell(5,19)));
		assertTrue(targets.contains(board.getCell(7,19)));

		// By edge with walls
		board.calcTargets(board.getCell(25, 18), 3); //by edge with walls
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(22,12)));
		assertTrue(targets.contains(board.getCell(22,18)));
		assertTrue(targets.contains(board.getCell(23,17)));
	}
	
	// Tests rolls of 6
	@Test
	public void testTargetsWalkwaysMax() {
		// Walkway
		board.calcTargets(board.getCell(7, 3), 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(17, targets.size());
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(7, 7)));
		assertTrue(targets.contains(board.getCell(8, 4)));
		assertTrue(targets.contains(board.getCell(6, 8)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(8, 8)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(8, 6)));
		assertTrue(targets.contains(board.getCell(5, 7)));
		assertTrue(targets.contains(board.getCell(4, 6)));
		assertTrue(targets.contains(board.getCell(7, 1)));
		assertTrue(targets.contains(board.getCell(7, 5)));
		assertTrue(targets.contains(board.getCell(12, 3)));
		assertTrue(targets.contains(board.getCell(5, 3)));
		assertTrue(targets.contains(board.getCell(6, 6)));
		assertTrue(targets.contains(board.getCell(7, 9)));
		assertTrue(targets.contains(board.getCell(6, 2)));
		
		//assert equals/true block here
		
		// Next to a room
		board.calcTargets(board.getCell(7, 20), 6);
		targets= board.getTargets();
		assertTrue(targets.contains(board.getCell(6, 21)));
		assertTrue(targets.contains(board.getCell(3, 18)));
		assertTrue(targets.contains(board.getCell(6, 19)));
		assertTrue(targets.contains(board.getCell(7, 16)));
		assertTrue(targets.contains(board.getCell(6, 15)));
		assertTrue(targets.contains(board.getCell(3, 22)));
		assertTrue(targets.contains(board.getCell(4, 19)));
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(5, 18)));
		assertTrue(targets.contains(board.getCell(7, 18)));
		assertTrue(targets.contains(board.getCell(8, 15)));
		assertTrue(targets.contains(board.getCell(10, 17)));
		assertTrue(targets.contains(board.getCell(8, 17)));
		assertTrue(targets.contains(board.getCell(6, 23)));
		assertTrue(targets.contains(board.getCell(7, 14)));
		assertTrue(targets.contains(board.getCell(7, 22)));
		assertTrue(targets.contains(board.getCell(11, 18)));
		assertTrue(targets.contains(board.getCell(7, 24)));
		assertTrue(targets.contains(board.getCell(4, 17)));
		assertTrue(targets.contains(board.getCell(9, 18)));
		assertTrue(targets.contains(board.getCell(2, 19)));
		
		// By edge with walls
		board.calcTargets(board.getCell(25, 18), 6);
		targets= board.getTargets();
		System.out.println(targets);
		assertTrue(targets.contains(board.getCell(22, 17)));
		assertTrue(targets.contains(board.getCell(21, 18)));
		assertTrue(targets.contains(board.getCell(19, 18)));
		assertTrue(targets.contains(board.getCell(20, 17)));
		assertTrue(targets.contains(board.getCell(22, 12)));
		assertTrue(targets.contains(board.getCell(24, 17)));
		assertTrue(targets.contains(board.getCell(23, 18)));
	}
	
	@Test
	public void testEntrances() { //Targets that allow the user to enter a room
		// On a doorway
		board.calcTargets(board.getCell(8, 3), 1);
		Set<BoardCell> targets= board.getTargets(); 
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 3)));
		assertTrue(targets.contains(board.getCell(7, 3)));	
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(8, 4)));
		
		// Nearby a doorway
		board.calcTargets(board.getCell(25,18), 3);
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(22, 18)));
		assertTrue(targets.contains(board.getCell(23, 17)));	
		assertTrue(targets.contains(board.getCell(22, 12)));
		
		// Far from a doorway
		board.calcTargets(board.getCell(7,25), 6);
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(6, 24)));
		assertTrue(targets.contains(board.getCell(7, 23)));
		assertTrue(targets.contains(board.getCell(6, 22)));
		assertTrue(targets.contains(board.getCell(7, 21)));
		assertTrue(targets.contains(board.getCell(6, 20)));
		assertTrue(targets.contains(board.getCell(7, 19)));
		assertTrue(targets.contains(board.getCell(3, 22)));
	}
	
	// Test exits from center of room
	@Test
	public void testExits() {
		// Room with one exit
		board.calcTargets(board.getCell(22, 12), 1);
		Set<BoardCell> targets= board.getTargets(); 
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(24, 17)));

		// Room with two exits, one on each side
		board.calcTargets(board.getCell(12, 3), 1);
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(8, 3)));
		assertTrue(targets.contains(board.getCell(16, 3)));
		
		// Room with two exits, both on same side
		board.calcTargets(board.getCell(12, 22), 1);
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(11, 18)));
		assertTrue(targets.contains(board.getCell(12, 18)));
	}
	
	// Test secret passages from center of room
	@Test
	public void testExitSecretPassage() { 
		// Room with one exit
		board.calcTargets(board.getCell(23, 24), 1);
		Set<BoardCell> targets= board.getTargets(); 
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(24, 21)));
		assertTrue(targets.contains(board.getCell(11,13)));

		// Room with two exits
		board.calcTargets(board.getCell(11, 13), 1);
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(8, 13)));
		assertTrue(targets.contains(board.getCell(13, 12)));
		assertTrue(targets.contains(board.getCell(23, 24)));
	}
	
	@Test
	public void testOccupied() { 
		// Test a roll of 4 blocked 2 squares up.
		board.getCell(23, 18).setOccupied(true);
		board.calcTargets(board.getCell(25, 18), 4);
		board.getCell(23, 18).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(22, 17)));
		assertTrue(targets.contains(board.getCell(22, 12)));
		assertFalse(targets.contains(board.getCell(23, 18)));
		assertFalse(targets.contains(board.getCell(21, 18)));

		// Make sure we can enter a room even if it is flagged as occupied.
		board.getCell(12, 3).setOccupied(true);
		board.getCell(8, 4).setOccupied(true);
		board.calcTargets(board.getCell(8, 3), 1);
		board.getCell(12, 3).setOccupied(false);
		board.getCell(8, 4).setOccupied(false);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(7, 3)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(12, 3)));

		// Check leaving a room with a blocked doorway.
		board.getCell(8, 3).setOccupied(true);
		board.calcTargets(board.getCell(12, 3), 3);
		board.getCell(8, 3).setOccupied(false);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(16, 1)));
		assertTrue(targets.contains(board.getCell(17, 2)));
		assertTrue(targets.contains(board.getCell(16, 5)));
		assertTrue(targets.contains(board.getCell(17, 4)));
		assertTrue(targets.contains(board.getCell(18, 3)));
	}
	
	

} 
