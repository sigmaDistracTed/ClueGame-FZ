package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.HumanPlayer;
import clueGame.ComputerPlayer;

public class GameSetupTests {
private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		board.calcAdj();
	}
	
	@Test
	public void countTests() {
		//Counting number of players
		Set<Player> playerList = board.getPlayers();
		assertEquals(6, playerList.size()); 
		int humanCount = 0; 
		int computerCount = 0;
		for(Player p : playerList) {
			if (p instanceof HumanPlayer) { 
				humanCount++; 
			}
			else if (p instanceof ComputerPlayer) {
				computerCount++;
			}
		}
		assertEquals(1, humanCount);
		assertEquals(5, computerCount);
		
		//Counting number of cards
		Set<Card> cardList = board.getCards(); 
		assertEquals(21, cardList.size());
		
		int roomCount, playerCount, weaponCount;
		roomCount =  playerCount = weaponCount = 0;
		
		for(Card c : cardList) {
			switch (c.getCardType()) {
				case ROOM:
					roomCount++;
					break;
				case PERSON: 
					playerCount++; 
					break;
				case WEAPON:
					weaponCount++;
					break;
				default: 
					break;
			} 
		}
		assertEquals(9, roomCount);
		assertEquals(6, playerCount);
		assertEquals(6, weaponCount);
				 
	} 
	
	@Test
	public void testRandomHumanPlayer() { 
		//Counts for each player
		int nn, ii, aa, cc, su, ds;
		nn = ii = aa = cc = su = ds = 0;
		
		for (int i = 0; i < 5; i++) {
			try {
				board.loadSetupConfig(); //todo: new playerlist every time
				Set<Player> playerList = board.getPlayers();
				System.out.println(playerList.size());
				for(Player p : playerList) {
					if (p instanceof HumanPlayer) { 
					switch (p.getName()) {
						case "Numerologist Night":
							nn++; 
							break;
						case "Inspector Irratino":
							ii++;
							break;
						case "Astrologer Azure":
							aa++;
							break;
						case "Cryptozoologist Cloud":
							cc++;
							break;
						case "Sociologist Umber": 
							su++;
							break;
						case "Dr. Seashell DDS":
							ds++;
							break;
						default:
							break;
						}	//end of switch block;
				
					} // if p is human player
				} // for player in player list
			} //end of try block
			catch (BadConfigFormatException e) {
				
			}
			
		} //end of for loop
/*	System.out.println(nn);
	System.out.println(ii);
	System.out.println(aa);
	System.out.println(cc);
	System.out.println(su);
	System.out.println(ds); */
	}
	

}
