package experiment;

import java.util.Set;

public class TestBoardCell {
	//variable initializing
	private int row; 
	private int col; 
	boolean isRoom; 
	boolean isOccupied; 
	
	Set<TestBoardCell> adjList; 
	
	//functions
	public TestBoardCell(int row, int col) { 
		this.row = row;
		this.col = col;
	}
	//setter for adjacency list
	void addAdjacency(TestBoardCell cell) {
		
	}
	
	Set<TestBoardCell> getAdjList() {
		return adjList;
		
	}
	void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	boolean getRoom(boolean isRoom) {
		return false;
	}
	
	void setOccupied(boolean isOccupied) { 
		this.isOccupied = isOccupied; 
	}
	
	boolean getOccupied(boolean isOccupied) {
		return false;
	}

}
