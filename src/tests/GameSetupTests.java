package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
				System.out.println(p);
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

}
