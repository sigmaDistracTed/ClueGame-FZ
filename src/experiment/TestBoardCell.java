package experiment;

import java.util.Set;
import java.util.HashSet;

public class TestBoardCell {
	//variable initializing
	private int row; 
	private int col; 

	boolean isRoom; 
	boolean isOccupied; 
	
	Set<TestBoardCell> adjList = new HashSet<>();
	
	//functions
	public TestBoardCell(int row, int col) { 
		this.row = row;
		this.col = col;
	}

	//setter for adjacency list
	public void addAdjacency(TestBoardCell cell) {
		
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
		
	}
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean isOccupied) { 
		this.isOccupied = isOccupied; 
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}

}
