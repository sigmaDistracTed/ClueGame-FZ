package experiment;

import java.util.Set;
import java.util.HashSet;

public class TestBoard {
    private TestBoardCell[][] grid = new TestBoardCell[4][4];
    private Set<TestBoardCell> targets = new HashSet<>();

    public TestBoard() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                grid[row][col] = new TestBoardCell(row, col);
            }
        }
    }

    public void calcTargets(TestBoardCell startCell, int pathLength) {

    }

    public TestBoardCell getCell(int row, int col) {
        return grid[row][col];
    }

    public Set<TestBoardCell> getTargets() {
        return targets;
    }

}
