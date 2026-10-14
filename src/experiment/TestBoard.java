package experiment;

import java.util.Set;
import java.util.HashSet;

/**
* TestBoard Class
*
* @author Alexandra Field
* @author Aiden Zeigler
*
* Purpose: Creates the TestBoard class, which sets up the testing board
* and calculates valid moves
*/

public class TestBoard {
    private TestBoardCell[][] grid = new TestBoardCell[4][4];
    private Set<TestBoardCell> targets = new HashSet<>();

    // Builds the grid for the board
    public TestBoard() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                grid[row][col] = new TestBoardCell(row, col);
            }
        }
    }

    // Will calculate the valid targets
    public void calcTargets(TestBoardCell startCell, int pathLength) {

    }

    public TestBoardCell getCell(int row, int col) {
        return grid[row][col];
    }

    public Set<TestBoardCell> getTargets() {
        return targets;
    }

}
