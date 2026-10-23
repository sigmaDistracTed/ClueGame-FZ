package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell; 

	// Constructor
    public Room(String name) {
        this.name = name;
    }

	// Get room name
	public String getName() {
		return name;
		
	}

	// Set room name
	public void setName(String name) {
		this.name = name;
	}
	
	// Get room center cell
	public BoardCell getCenterCell() { 
		return centerCell;
	}

	// Set room center cell
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	// Get room board cell
	public BoardCell getLabelCell() { 
		return labelCell;
	}

	// Set room label cell
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
    
}
