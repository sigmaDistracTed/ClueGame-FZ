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
	
	@Test
	public void testTargetsWalkwaysMed() { //rolls of 3
		//Im not to sure on finding the targets logic manually, so I'll leave that for now
		board.calcTargets(board.getCell(7, 3), 3); //walkway
		Set<BoardCell> targets= board.getTargets();
		
		//assert equals/true block here
		
		board.calcTargets(board.getCell(8, 20), 3); //next to a room
		targets= board.getTargets();
		
		//assert equals/true block here
		
		board.calcTargets(board.getCell(25, 17), 3); //by edge with walls
		targets= board.getTargets();
		
		//assert equals/true block here
		
		
	}
	
	@Test
	public void testTargetsWalkwaysMax() { //rolls of 6
		//Im not to sure on finding the targets logic manually, so I'll leave that for now
		board.calcTargets(board.getCell(7, 3), 6); //walkway
		Set<BoardCell> targets= board.getTargets();
		
		//assert equals/true block here
		
		board.calcTargets(board.getCell(8, 20), 6); //next to a room
		targets= board.getTargets();
		
		//assert equals/true block here
		
		board.calcTargets(board.getCell(25, 17), 6); //by edge with walls
		targets= board.getTargets();
		
		//assert equals/true block here
		
		
	}
	
	@Test
	public void testEntrances() { //Targets that allow the user to enter a room
		//on a doorway
		board.calcTargets(board.getCell(8, 3), 1);
		Set<BoardCell> targets= board.getTargets(); 
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 3)));
		assertTrue(targets.contains(board.getCell(7, 3)));	
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(8, 4)));
		
		//Nearby a doorway
		
		//far from a doorway 
		
	}
	
	@Test
	public void testExits() { 
		
	}
	
	@Test
	public void testExitSecretPassage() { 
		
	}
	
	@Test
	public void testOccupied() { 
		
	}
	
	

} 
