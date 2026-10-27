package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

// TODO: Implement the tests
// Create the planning sheet (just make a copy of our excel)

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;

    @BeforeAll
    public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

    // Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms() {
        assertTrue(false);
    }

    // Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor() {
        assertTrue(false);
    }

	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways() {
        assertTrue(false);
    }

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInDiningRoom() {
        assertTrue(false);
    }

	@Test
	public void testTargetsInKitchen() {
        assertTrue(false);
    }

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
        assertTrue(false);
    }

    @Test
	public void testTargetsInWalkway1() {
        assertTrue(false);
    }

	@Test
	public void testTargetsInWalkway2() {
        assertTrue(false);
    }

	// test to make sure occupied locations do not cause problems
    @Test
	public void testTargetsOccupied() {
        assertTrue(false);
    }
}
