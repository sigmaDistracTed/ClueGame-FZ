package clueGame;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
	private Map<String, String> roomMap; 
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
     
     public void setConfigFiles(String csv, String txt) {
    	 this.layoutConfigFile = csv;
    	 this.setupConfigFile = txt;
    	 
     }
     
     //Read in setup file and connect room characters to names
     public void loadsetupConfig() {
    	 FileReader fr = null; 
    	 Scanner in = new Scanner(fr);
    	 
    	 while(in.hasNextLine()) {
    		 String line = in.nextLine(); 
    		 String[] info = line.split(",");
    		 if(info[0] == "Room") {
    			 roomMap.put(info[1], info[2]);
    		 }
    	 }
     }
     

}
