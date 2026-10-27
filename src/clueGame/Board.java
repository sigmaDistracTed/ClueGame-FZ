package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Board Class
 *
 * @author Alexandra Field
 * @author Aiden Zeigler
 *
 * Purpose: Creates the Board class, which sets up the board.
 */

public class Board {
	private Set<BoardCell> targets = new HashSet<>();
	private Set<BoardCell> visited = new HashSet<>();

	private Map<Character, Room> roomMap = new HashMap<>();
	private String setupConfigFile;
	private String layoutConfigFile;

	private BoardCell[][] grid;
	private int numRows;
	private int numCols;

	private static Board theInstance = new Board();

	// Constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// This method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	// Initialize the board (since we are using singleton pattern)
	public void initialize() {
		try {
			loadSetupConfig();
			loadLayoutConfig();
			calcAdj();
		} catch (BadConfigFormatException e) {
			throw new IllegalStateException("Could not initialize board.", e);
		}
	}

	// setting config files
	public void setConfigFiles(String csv, String txt) {
		this.layoutConfigFile = csv;
		this.setupConfigFile = txt;

	}

	// Read in setup file and connect room characters to names
	public void loadSetupConfig() throws BadConfigFormatException {
		try (Scanner in = new Scanner(new File("data", setupConfigFile))) {
			while (in.hasNextLine()) {
				String line = in.nextLine();
				if (line.charAt(0) != '/') { // Comment safeguard from class
					String[] info = line.split(",");
					if (info[0].trim().equals("Room")) {
						char icon = info[2].trim().charAt(0);
						String roomName = info[1].trim();
						roomMap.put(icon, new Room(roomName));
					} else if (info[0].trim().equals("Space")) {
						char icon = info[2].trim().charAt(0);
						String roomName = info[1].trim();
						roomMap.put(icon, new Room(roomName));
					}
				}
			}
		} catch (FileNotFoundException err) {
			throw new BadConfigFormatException("Cannot open setup file: " + setupConfigFile);
		}
	}

	// Load the layout config file
	public void loadLayoutConfig() throws BadConfigFormatException {
		List<String[]> rows = new ArrayList<>();

		try (Scanner in = new Scanner(new File("data", layoutConfigFile))) {
			while (in.hasNextLine()) {
				rows.add(in.nextLine().split(","));
			}
		} catch (FileNotFoundException e) {
			throw new BadConfigFormatException("Cannot open setup file: " + layoutConfigFile);
		}

		if (rows.isEmpty()) {
			throw new BadConfigFormatException("Layout is empty.");
		}

		numRows = rows.size();
		numCols = rows.get(0).length;
		grid = new BoardCell[numRows][numCols];

		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				String info = rows.get(row)[col].trim();

				BoardCell cell = new BoardCell(row, col);
				cell.setInital(info.charAt(0));
				grid[row][col] = cell;

				// doorway checking
				switch (info.charAt(info.length() - 1)) {
					case '^':
						cell.setIsDoorway(true);
						cell.setDoorDirection(DoorDirection.UP);
						break;
					case 'v':
						cell.setIsDoorway(true);
						cell.setDoorDirection(DoorDirection.DOWN);
						break;
					case '>':
						cell.setIsDoorway(true);
						cell.setDoorDirection(DoorDirection.RIGHT);
						break;
					case '<':
						cell.setIsDoorway(true);
						cell.setDoorDirection(DoorDirection.LEFT);
						break;
					default:
						cell.setIsDoorway(false);
						cell.setDoorDirection(DoorDirection.NONE);
						break;
				}

				// If the cell isn't a room label, center, or passage
				if (info.length() == 1) {
					cell.setIsRoomLabel(false);
					cell.setIsRoomCenter(false);
				}
				// Check if the cell is the label cell of the room
				else if (info.charAt(info.length() - 1) == '#') {
					cell.setIsRoomLabel(true);
					getRoom(cell).setLabelCell(cell);
				}
				// Check if the cell is the center of the room
				else if (info.charAt(info.length() - 1) == '*') {
					cell.setIsRoomCenter(true);
					getRoom(cell).setCenterCell(cell);
				}
				// Otherwise, it's a secret passage
				else if (!cell.isDoorway()) {
					cell.setSecretPassage(info.charAt(info.length() - 1));
					cell.setIsSecretPassage(true);
				}
			}
		}
	}

	// Calculate adjacency list for cell
	public void calcAdj() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				BoardCell cell = grid[row][col];

				// Add adjacencies for secret passage
				if (cell.isSecretPassage()) {
					BoardCell sourceCenter = getRoom(cell).getCenterCell();
					BoardCell destinationCenter = getRoom(cell.getSecretPassage()).getCenterCell();

					sourceCenter.addAdjacency(destinationCenter);
				}

				// Add adjacencies for secret passage
				if (cell.getInitial() == 'W') {
					if (row > 0 && getCell(row - 1, col).getInitial() == 'W') {
						cell.addAdjacency(getCell(row - 1, col));
					}
					if (row + 1 < numRows && getCell(row + 1, col).getInitial() == 'W') {
						cell.addAdjacency(getCell(row + 1, col));
					}
					if (col > 0 && getCell(row, col - 1).getInitial() == 'W') {
						cell.addAdjacency(getCell(row, col - 1));
					}
					if (col + 1 < numCols && getCell(row, col + 1).getInitial() == 'W') {
						cell.addAdjacency(getCell(row, col + 1));
					}
				}

				// If the cell is a doorway
				if (cell.isDoorway()) {
					int roomRow = row;
					int roomCol = col;

					switch (cell.getDoorDirection()) {
						case UP:
							roomRow--;
							break;
						case DOWN:
							roomRow++;
							break;
						case LEFT:
							roomCol--;
							break;
						case RIGHT:
							roomCol++;
							break;
						default:
							break;
					}

					BoardCell roomCell = getCell(roomRow, roomCol);
					Room room = roomMap.get(roomCell.getInitial());
					BoardCell centerCell = room.getCenterCell();

					cell.addAdjacency(centerCell);
					centerCell.addAdjacency(cell);
				}
			}
		}
	}

	// Calculate valid targets
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.clear();
		targets.clear();

		visited.add(startCell);

		findAllTargets(startCell, pathLength);
	}

	// Find all possible targets
	public void findAllTargets(BoardCell startCell, int pathLength) {
		for (BoardCell adjCell : startCell.getAdjList()) {
			// Continue if the room is occupied but isn't a room
			if (adjCell.getIsOccupied() && !adjCell.isRoomCenter()) {
				continue;
			}

			// if visited test
			if (visited.contains(adjCell)) {
				continue;
			}

			visited.add(adjCell);

			if (pathLength == 1 || adjCell.isRoomCenter()) {
				targets.add(adjCell);
			} else {
				findAllTargets(adjCell, pathLength - 1);
			}

			visited.remove(adjCell);
		}
	}

	// Retrieves room name from character
	public Room getRoom(char icon) {
		return (roomMap.get(icon));
	}

	public String getName() {
		return "";
	}

	// Return number of rows in board
	public int getNumRows() {
		return numRows;
	}

	// Return number of columns in the board
	public int getNumColumns() {
		return numCols;
	}

	// Return a cell
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	// Get cell targets
	public Set<BoardCell> getTargets() {
		return targets;
	}

	// Get cell room
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	// Return cell's adjacency list
	public Set<BoardCell> getAdjList(int x, int y) {
		BoardCell cell = getCell(x, y);
		return cell.adjList;
	}

}
