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
    private TestBoardCell[][] grid;
    private Set<TestBoardCell> targets = new HashSet<>();
    private Set<TestBoardCell> visited = new HashSet<>();

    final static int ROWS = 4;
    final static int COLS = 4;

    // Builds the grid for the board
    public TestBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col] = new TestBoardCell(row, col);
            }
        }
    }

    // Calculate valid targets
    public void calcTargets(TestBoardCell startCell, int pathLength) {
        visited.clear();
        targets.clear();

        visited.add(startCell);

        findAllTargets(startCell, pathLength);
    }

    // Find all possible targets
    public void findAllTargets(TestBoardCell startCell, int pathLength) {
        for (TestBoardCell adjCell: startCell.getAdjList()) {
            if (visited.contains(adjCell)) {
                break;
            }

            visited.add(adjCell);
            
            if (pathLength == 1) {
                targets.add(adjCell);
            }
            else {
                findAllTargets(adjCell, pathLength - 1);
            }
        }
    }

    public TestBoardCell getCell(int row, int col) {
        return grid[row][col];
    }

    public Set<TestBoardCell> getTargets() {
        return targets;
    }

}
