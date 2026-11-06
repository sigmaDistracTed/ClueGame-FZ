package clueGame;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections;


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
	
	private Set<Player> players = new HashSet<>();
	private Set<Card> cards = new HashSet<>();

	private static Board theInstance = new Board();

	private static Solution theAnswer;
	
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
			//dealCards(cards); // Earlier tests fail because it tries to deal cards.
			// Eventually, something should call dealCards for the game
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
		//loading one random integer to determine who is the human player
		int randomNum = (int)(Math.random() * 6); //range of 0-5, index of playerList
		int playerNum = 0;
		
		//file reading
		try (Scanner in = new Scanner(new File("data", setupConfigFile))) {
			while (in.hasNextLine()) {
				String line = in.nextLine();
				String[] info = line.split(",");
				String type = info[0].trim();
				//Checking type
				switch (type) {
					case "Room":
						char icon = info[2].trim().charAt(0);
						String roomName = info[1].trim();
						roomMap.put(icon, new Room(roomName));
						Card rCard = new Card(roomName, CardType.ROOM);
						cards.add(rCard);
						break; 
						
					case "Space":
						char iconSpace = info[2].trim().charAt(0);
						String roomNameSpace = info[1].trim();
						roomMap.put(iconSpace, new Room(roomNameSpace));
						break; 
						
					case "Person":
						String playerName = info[1].trim();
						String color = info[2].trim();
						int row = Integer.parseInt(info[3].trim());
						int col = Integer.parseInt(info[4].trim());
						if(randomNum == playerNum) {
							HumanPlayer huP = new HumanPlayer(playerName, color, row, col);
							players.add(huP);
						}
						else { 
							ComputerPlayer coP = new ComputerPlayer(playerName, color, row, col);
							players.add(coP);
						}
						playerNum++;
						Card pCard = new Card(playerName, CardType.PERSON);
						cards.add(pCard);
						break;
						
					case "Weapon": 
						String weaponName = info[1].trim();
						Card wCard = new Card(weaponName, CardType.WEAPON);
						cards.add(wCard);
						break;
						
					default: 
						break;
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
				char lastChar = (info.charAt(info.length() - 1));

				BoardCell cell = new BoardCell(row, col);
				cell.setInital(info.charAt(0));
				grid[row][col] = cell;

				// doorway checking
				switch (lastChar) {
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
				else if (lastChar == '#') {
					cell.setIsRoomLabel(true);
					getRoom(cell).setLabelCell(cell);
				}
				// Check if the cell is the center of the room
				else if (lastChar == '*') {
					cell.setIsRoomCenter(true);
					getRoom(cell).setCenterCell(cell);
				}
				// Otherwise, it's a secret passage
				else if (!cell.isDoorway()) {
					cell.setSecretPassage(info.charAt(info.length() - 1));
					cell.setIsSecretPassagey(true);
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
			// Continue if the room is occupied but isn't a room, or if visited.
			if ((adjCell.getIsOccupied() && !adjCell.isRoomCenter()) || visited.contains(adjCell)) {
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
	
	public void dealCards(Set<Card> cards) { 
		//make a deck for different functionality like shuffling for extra randomness
		ArrayList<Card> cardDeck = new ArrayList<Card>();
		for (Card c :cards) {
			cardDeck.add(c);
		}
		Collections.shuffle(cardDeck);
		int rCount, pCount, wCount; 
		rCount = pCount = wCount = 0;
		Card room, person, weapon;
		room = person = weapon = new Card();
		//getting the room card
		int randomNum = (int)(Math.random() * cardDeck.size());
		while(rCount < 1) {
			Card rCandidate = cardDeck.get(randomNum);
			if(rCandidate.getCardType() == CardType.ROOM) {
				room = rCandidate;
				cardDeck.remove(rCandidate);
				rCount = 1;
			}
			else { 
				randomNum = (int)(Math.random() * cardDeck.size());
			}
			
			//getting person
			
		}
		
		while(pCount < 1) {
			randomNum = (int)(Math.random() * cardDeck.size());
			Card pCandidate = cardDeck.get(randomNum);
			if(pCandidate.getCardType() == CardType.PERSON) {
				person = pCandidate;
				cardDeck.remove(pCandidate);
				pCount = 1;
			}
			
			else { 
				randomNum = (int)(Math.random() * cardDeck.size());
			}
	}
		while(wCount < 1) {
			randomNum = (int)(Math.random() * cardDeck.size());
			Card wCandidate = cardDeck.get(randomNum);
			if(wCandidate.getCardType() == CardType.WEAPON) {
				weapon = wCandidate;
				cardDeck.remove(wCandidate);
				wCount = 1;
			}
			
			else { 
				randomNum = (int)(Math.random() * cardDeck.size());
			}
			
		}
		
		theAnswer = new Solution(room, person, weapon);
		
		Set<Player> playerList = getPlayers();
		
		while(!cardDeck.isEmpty()) {
			for(Player p : playerList) {
				p.updateHand(cardDeck.get(0));
				cardDeck.remove(0);
			}
		}
	}
		

	// Checks an accusation to the answer
	public boolean checkAccusation(Card room, Card person, Card weapon) {
		if (room == theAnswer.getRoom() && person == theAnswer.getPerson() && weapon == theAnswer.getWeapon() ) {
			return true;
		}
		return false;
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
	
	public Set<Player> getPlayers() { 
		return players; 
	}
	
	public Set<Card> getCards() {
		return cards;
	}
	
	public Solution getSol() {
		return theAnswer;
	}
	
	

}
