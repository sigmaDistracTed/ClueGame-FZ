package clueGame;

import java.util.Set;
import java.util.HashSet;

/**
* BoardCell Class
*
* @author Alexandra Field
* @author Aiden Zeigler
*
* Purpose: Creates the BoardCell class, which provides the list for cells
* and setters and getters for rooms
*/

public class BoardCell {
	//variable initializing
	private int row; 
	private int col; 

	boolean isRoom; 
	boolean isOccupied; 
	
	Set<BoardCell> adjList = new HashSet<>();
	
	//functions
	public BoardCell(int row, int col) { 
		this.row = row;
		this.col = col;
	}

	//setter for adjacency list
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<BoardCell> getAdjList() {
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