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
	private char initial;
	private DoorDirection doorDirection;
	private boolean isRoomLabel;
	private boolean isRoomCenter;
	private boolean isDoorway;
	private char secretPassage;

	boolean isRoom; 
	boolean isOccupied; 
	
	Set<BoardCell> adjList = new HashSet<>();
	
	// Constructor
	public BoardCell(int row, int col) { 
		this.row = row;
		this.col = col;
	}

	// Get cell row
	public int getRow() {
		return row;
	}

	// Get cell col
	public int getCell() {
		return col;
	}

	// Get cell inital
	public char getInitial() {
		return initial;
	}

	// Set cell initial
	public void setInital(char initial) {
		this.initial = initial;
	}

	// Get cell DoorDirection
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	// Set cell DoorDirection
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	// Get cell roomLabel
	public boolean isLabel() {
		return isRoomLabel;
	}

	// Set cell isRoomLabel
	public void setIsRoomLabel(boolean isRoomLabel) {
		this.isRoomLabel = isRoomLabel;
	}

	// Get cell isRoomCenter
	public boolean isRoomCenter() {
		return isRoomCenter;
	}

	// Set cell isRoomCenter
	public void setIsRoomCenter(boolean isRoomCenter) {
		this.isRoomCenter = isRoomCenter;
	}

	// Get cell isDoorway
	public boolean isDoorway() {
		return isDoorway;
	}

	// Set cell isDoorway
	public void getIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}

	// Get cell secretPassage
	public char getSecretPassage() { 
		return secretPassage;
	}

	// Set cell secretPassage
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	// Get cell isRoom
	public boolean getIsRoom() {
		return isRoom;
	}
	
	// Set cell isRoom
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

	// Get cell isOccupied
	public boolean getIsOccupied() {
		return isOccupied;
	}

	// Set cell isOccupied
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	//setter for adjacency list
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;	
	}
	
	@Override
	public String toString() {
		return(this.row + "," + this.col);
	}
}
