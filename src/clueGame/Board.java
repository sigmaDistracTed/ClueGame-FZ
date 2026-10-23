package clueGame;

import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//import clueGame.Room;

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
	
    /*
     * variable and methods used for singleton pattern
     */
     private static Board theInstance = new Board();
     // constructor is private to ensure only one can be created
     private Board() {
            super() ;
     }
     // this method returns the only Board
     public static Board getInstance() {
            return theInstance;
     }
     /*
      * initialize the board (since we are using singleton pattern)
      */
     public void initialize() {
       try {
              loadSetupConfig();
              loadLayoutConfig();
       } catch (BadConfigFormatException e) {
              throw new IllegalStateException("Could not initialize board.", e);
       }
     }
     
     //setting config files
     public void setConfigFiles(String csv, String txt) {
    	 this.layoutConfigFile = csv;
    	 this.setupConfigFile = txt;
    	 
     }

     //Read in setup file and connect room characters to names
     public void loadSetupConfig() throws BadConfigFormatException {
       try (Scanner in = new Scanner(new File("data", setupConfigFile))) {
              while(in.hasNextLine()) {
                     String line = in.nextLine();
                     if(line.charAt(0) != '/') { //Comment safeguard from class
                            String[] info = line.split(",");
                            if(info[0].trim().equals("Room")) {
                                   char icon = info[2].trim().charAt(0);
                                   roomMap.put(icon, new Room(info[1]));
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
                     
                     //doorway checking
                     switch(info.charAt(info.length() - 1)) {
                     case '^': 
                    	 cell.setIsDoorway(true);
                    	 break;
                     case 'V':
                    	 cell.setIsDoorway(true);
                    	 break;
                     case '>':
                    	 cell.setIsDoorway(true);
                    	 break;
                     case '<': 
                    	 cell.setIsDoorway(true);
                    	 break;
                    default: 
                    	cell.setIsDoorway(false);
                    	break;
                     }
                     
                     //checking to see if we need to label anything
                     if(info.charAt(info.length() - 1) == '#') {
                    	 cell.setIsRoomLabel(true);
                     }
                     
                     else if (info.charAt(info.length() - 1) == '*' ) {
                    	 cell.setIsRoomCenter(true);
                     }
                    
                     
                     else { 
                    	 cell.setIsRoomLabel(false);
                    	 cell.setIsRoomCenter(false);
                     }
                 
              }
        }
     }

     // Calculate adjacency list for cell
     public void calcAdj() {
       for (int row = 0; row < numRows; row++) {
              for (int col = 0; col < numCols; col++) {
                     BoardCell cell = grid[row][col];
                     if (row - 1 >= 0) {
                            cell.addAdjacency(getCell(row - 1, col));
                     }
                     if (row + 1 < numRows) {
                            cell.addAdjacency(getCell(row + 1, col));
                     }
                     if (col - 1 >= 0) {
                            cell.addAdjacency(getCell(row, col - 1));
                     }
                     if (col + 1 < numCols) {
                            cell.addAdjacency(getCell(row, col + 1));
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
        for (BoardCell adjCell: startCell.getAdjList()) {
        	//ignore if cell is occupied or if its a room 
        	if (adjCell.getIsOccupied()) {
        		continue;
        	}

            if (adjCell.getIsRoom()) {
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
     
     // Retrieves room name from character
     public Room getRoom(char icon) {
    	 return(roomMap.get(icon));
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
     
     public Room getRoom(BoardCell cell) {
       return roomMap.get(cell.getInitial());
     }
     
     // Return cell's adjacency list
     public Set<BoardCell> getAdjList(int x, int y) {
    	 BoardCell cell = getCell(x, y);
 		return cell.adjList;	
 	}

}
