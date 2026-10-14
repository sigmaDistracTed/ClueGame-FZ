package tests;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
    private TestBoard board;

    @BeforeEach
    void setUp() {
        board = new TestBoard();
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
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(3,2)));
        assertTrue(testList.contains(board.getCell(2,3)));
        assertEquals(2, testList.size());
    }

    // Failing test for right edge cell
    @Test 
    public void testAdjacencyRightEdge() {
        TestBoardCell cell = board.getCell(1, 3);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(0,3)));
        assertTrue(testList.contains(board.getCell(2,3)));
        assertTrue(testList.contains(board.getCell(1,2)));
        assertEquals(3, testList.size());
    }

    // Failing test for left edge cell
    @Test 
    public void testAdjacencyLeftEdge() {
        TestBoardCell cell = board.getCell(3, 1);
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
        Set<TestBoardCell> testList = cell.getAdjList();
        assertTrue(testList.contains(board.getCell(2,1)));
        assertTrue(testList.contains(board.getCell(3,2)));
        assertTrue(testList.contains(board.getCell(2,3)));
        assertTrue(testList.contains(board.getCell(1,2)));
        assertEquals(4, testList.size());
    }

    // Failing tests for calcTarget() code

}
