package experiment;

import java.util.Set;
import java.util.HashSet;

/**
* TestBoardCell Class
*
* @author Alexandra Field
* @author Aiden Zeigler
*
* Purpose: Creates the TestBoardCell class, which provides the list for cells
* and setters and getters for rooms
*/

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
		adjList.add(cell);
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
	
	@Override
	public String toString() {
		return(this.row + "," + this.col);
	}

}