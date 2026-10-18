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
    private Set<TestBoardCell> targets = new HashSet<>();
    private Set<TestBoardCell> visited = new HashSet<>();

    final static int ROWS = 4;
    final static int COLS = 4;
    private TestBoardCell[][] grid = new TestBoardCell[ROWS][COLS];

    // Builds the grid for the board
    public TestBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col] = new TestBoardCell(row, col);
            }
        }
    }
    
    public void calcAdj() { //function to calculate adj for cells 
    	for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
            	TestBoardCell cell = grid[row][col];
            	if (row -1 > -1 ) {
            		cell.addAdjacency(getCell(row-1, col)); //up 	
            	}
            	if (row +1 < ROWS ) {
            		cell.addAdjacency(getCell(row+1, col)); //down 
            	}
            	if (col -1 > -1  ) {
            		cell.addAdjacency(getCell(row, col-1)); //left 
            	}
            	if (col +1 < COLS ) {
            		cell.addAdjacency(getCell(row, col+1)); //right 
            	}
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
        	//ignore if cell is occupied or if its a room 
        	if (adjCell.getOccupied()) {
        		continue;
        	}

        	//if visited test
            if (visited.contains(adjCell)) {
                continue;
            }
            
            visited.add(adjCell);
           
            if (pathLength == 1) {
                targets.add(adjCell);
            }
            else {
                findAllTargets(adjCell, pathLength - 1);
            }
            
            visited.remove(adjCell);
        }
    }

    public TestBoardCell getCell(int row, int col) {
        return grid[row][col];
        
    }

    public Set<TestBoardCell> getTargets() {
        return targets;
    }

}
