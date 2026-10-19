package tests;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

/**
* BoardTestsExp Class
*
* @author Alexandra Field
* @author Aiden Zeigler
*
* Purpose: Creates the BoardTextsExp class, which sets up tests for different
* scenarios during the board game.
*/

public class BoardTestsExp {
    private TestBoard board;

    // Clean board for each test
    @BeforeEach
    void setUp() {
        board = new TestBoard();
        board.calcAdj();
    }

    // Failing test for top left cell
    @Test 
    public void testAdjacencyTopLeft() {
        TestBoardCell cell = board.getCell(0, 0);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(1,0)));
        assertTrue(testList.contains(board.getCell(0,1)));
        assertEquals(2, testList.size());
    }

    // Failing test for bottom right cell
    @Test 
    public void testAdjacencyBottomRight() {
        TestBoardCell cell = board.getCell(3, 3);
        board.calcAdj();
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(3,2)));
        assertTrue(testList.contains(board.getCell(2,3)));
        assertEquals(2, testList.size());
    }

    // Failing test for right edge cell
    @Test 
    public void testAdjacencyRightEdge() {
        TestBoardCell cell = board.getCell(1, 3);
        board.calcAdj();
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(0,3)));
        assertTrue(testList.contains(board.getCell(2,3)));
        assertTrue(testList.contains(board.getCell(1,2)));
        assertEquals(3, testList.size());
    }

    // Failing test for left edge cell
    @Test 
    public void testAdjacencyLeftEdge() {
        TestBoardCell cell = board.getCell(2, 0);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(1,0)));
        assertTrue(testList.contains(board.getCell(2,1)));
        assertTrue(testList.contains(board.getCell(3,0)));
        assertEquals(3, testList.size());
    }

    // Failing test for middle cell
    @Test 
    public void testAdjacencyMiddle() {
        TestBoardCell cell = board.getCell(2, 2);
        board.calcAdj();
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(2,1)));
        assertTrue(testList.contains(board.getCell(3,2)));
        assertTrue(testList.contains(board.getCell(2,3)));
        assertTrue(testList.contains(board.getCell(1,2)));
        assertEquals(4, testList.size());
    }

    // Failing test for normal movement
    @Test 
    public void testTargetsNormal() {
        TestBoardCell cell = board.getCell(0, 0);
        board.calcTargets(cell, 3);
        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(6, targets.size());
        assertTrue(targets.contains(board.getCell(3, 0)));
        assertTrue(targets.contains(board.getCell(2, 1)));
        assertTrue(targets.contains(board.getCell(0, 1)));
        assertTrue(targets.contains(board.getCell(1, 2)));
        assertTrue(targets.contains(board.getCell(0, 3)));
        assertTrue(targets.contains(board.getCell(1, 0)));
    }

    // Failing test for a max dice roll of 6 from bottom-right corner
    @Test 
    public void testTargetsMaxRoll() {
        TestBoardCell cell = board.getCell(3, 3);
        board.calcTargets(cell, 6);
        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(7, targets.size());
        assertTrue(targets.contains(board.getCell(0, 0)));
        assertTrue(targets.contains(board.getCell(0, 2)));
        assertTrue(targets.contains(board.getCell(1, 1)));
        assertTrue(targets.contains(board.getCell(1, 3)));
        assertTrue(targets.contains(board.getCell(2, 0)));
        assertTrue(targets.contains(board.getCell(2, 2)));
        assertTrue(targets.contains(board.getCell(3, 1)));
    }

    // Test for room movement
    @Test 
    public void testTargetsRoom() {
        board.getCell(0, 1).setRoom(true);

        TestBoardCell cell = board.getCell(0, 0);
        board.calcTargets(cell, 2);

        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(2, targets.size()); // Test was written wrong, only can move to two cells and not (0, 1)
        assertTrue(targets.contains(board.getCell(2, 0)));
        assertTrue(targets.contains(board.getCell(1, 1)));
    }

    // Test for occupied walkway movement
    @Test 
    public void testTargetsOccupied() {
        board.getCell(0, 1).setOccupied(true);
        TestBoardCell cell = board.getCell(0, 0);
        board.calcTargets(cell, 2);
        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(2, targets.size());
        assertTrue(targets.contains(board.getCell(1, 1)));
        assertTrue(targets.contains(board.getCell(2, 0)));
    }

    // Test for occupied walkway movement
    // Original test was written wrong, rewrote all cells
    @Test 
    public void testTargetsMixed() {
        board.getCell(0, 2).setOccupied(true);
        board.getCell(2, 0).setRoom(true);

        TestBoardCell cell = board.getCell(0, 0);
        board.calcTargets(cell, 3);

        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(4, targets.size());
        assertTrue(targets.contains(board.getCell(0, 1)));
        assertTrue(targets.contains(board.getCell(1, 0)));
        assertTrue(targets.contains(board.getCell(2, 1)));
        assertTrue(targets.contains(board.getCell(1, 2)));
    }

}
