package clueGame;

import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.Room;
import experiment.TestBoardCell; 

public class Board {
	private Map<Character, Room> roomMap; 
	private String setupConfigFile;
	private String layoutConfigFile;

       private BoardCell[][] grid;
       private int rows;
       private int cols;
	
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
     
     public void loadLayoutConfig() throws BadConfigFormatException { 
    	 //placeholder for compilation
     }
     
     // Retrieves room name from character
     public Room getRoom(char icon) {
    	 return(roomMap.get(icon));
     }
     
     public String getName() { 
    	 return "";
     }
     
     public int getNumRows() {
    	 return -1; 
     }
     
     public int getNumColumns() {
    	 return -1; 
     }
     
     public BoardCell getCell(int x, int y) { 
    	 return new BoardCell(x, y); //placeholder to get tests to fail then pass
     }
     
     public Room getRoom(BoardCell cell) {
    	 Room room = null;
    	 return room; //placeholder for compilation
    	 
     }
     
     public Set<BoardCell> getAdjList(int x, int y) {
    	 BoardCell cell = getCell(x, y);
 		return cell.adjList;	
 	}

}
