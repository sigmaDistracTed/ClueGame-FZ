package clueGame;

import java.util.Set;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
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
	// Variable initializing
	private int row; 
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean isRoomLabel;
	private boolean isRoomCenter;
	private boolean isDoorway;
	private boolean isSecretPassage;
	private boolean isUnused;
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

	public void draw(Graphics g, int cellSize, int offsetX, int offsetY) {
		int x = offsetX + col * cellSize;
		int y = offsetY + row * cellSize;

		if (getIsRoom()) {
			g.setColor(Color.GRAY);
			g.fillRect(x, y, cellSize, cellSize);
		}
		else if (isUnused()) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, cellSize, cellSize);
		}
		else if (isDoorway()) {
			g.setColor(Color.BLUE);
			g.fillRect(x, y, cellSize, cellSize);
		}
		else {
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, cellSize, cellSize);
		}

		g.setColor(Color.BLACK);
		g.drawRect(x, y, cellSize - 1, cellSize - 1);
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
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}

	// Get cell isSecretPassage
	public boolean isSecretPassage() {
		return isSecretPassage;
	}

	// Set cell isSecretPassage
	public void setIsSecretPassagey(boolean isSecretPassage) {
		this.isSecretPassage = isSecretPassage;
	}

	// Get cell isUnused
	public boolean isUnused() {
		return isUnused;
	}

	// Set cell isUnused
	public void setIsUnused(boolean isUnused) {
		this.isUnused = isUnused;
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
		//current problem: getting the room names to call correctly
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
