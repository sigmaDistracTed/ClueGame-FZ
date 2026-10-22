package clueGame;

import java.io.FileReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import clueGame.Room; 

public class Board {
	private Map<String, Character> roomMap; 
	private String setupConfigFile;
	private String layoutConfigFile; 
	
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
    	 Scanner in = new Scanner(setupConfigFile);
    	 
    	 while(in.hasNextLine()) {
    		 String line = in.nextLine(); 
    		 if(line.charAt(0) != '/') { //safeguarding with format from class
	    		 String[] info = line.split(",");
	    		 if(info[0] == "Room") {
	    			 char icon = info[2].charAt(0);
	    			 roomMap.put(info[1], icon);
	    			 
	    		 }
	    	 }
    	 }
     }
     
     public void loadLayoutConfig() throws BadConfigFormatException { 
    	 //placeholder for compilation
     }
     
     public String getRoom(char icon) {
    	 return(roomMap.get(icon)); //Rusty on hashmaps and how to get this to work
    	 //since there's the .get.get
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

}
