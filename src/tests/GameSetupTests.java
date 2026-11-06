package tests;

import static org.junit.Assert.*;
import java.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;
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
	}

	@BeforeEach
	void setUpCards() {
		board.getPlayers().clear();
		board.getCards().clear();
		// Initalize each time so that our player and card lists will remain constant
		board.initialize();
		board.dealCards(board.getCards());
	}
	
	@Test
	public void testLoading() {
		boolean foundPlayer = false;
		boolean foundRoom = false;
		boolean foundPerson = false;
		boolean foundWeapon = false;
		
		System.out.println("testLoading");
		for(Player p : board.getPlayers()) {
			System.out.println(p.getHand());
		}

		for (Player p : board.getPlayers()) {
			if (p.getName().equals("Numerologist Night")) {
				foundPlayer = true;
				assertEquals("BLACK", p.getColor());
				assertEquals(0, p.getRow());
				assertEquals(7, p.getCol());
			}
		}

		for (Card c : board.getCards()) {
			if (c.getCardName().equals("Hedge Maze")) {
				foundRoom = true;
				assertEquals(CardType.ROOM, c.getCardType());
			}

			if (c.getCardName().equals("Numerologist Night")) {
				foundPerson = true;
				assertEquals(CardType.PERSON, c.getCardType());
			}

			if (c.getCardName().equals("Crystal Ball")) {
				foundWeapon = true;
				assertEquals(CardType.WEAPON, c.getCardType());
			}
		}

		assertTrue(foundPlayer);
		assertTrue(foundRoom);
		assertTrue(foundPerson);
		assertTrue(foundWeapon);
	}
	
	@Test
	public void testRandomHumanPlayer() { 
		
		System.out.println("randomHumanPlayer");
		for(Player p : board.getPlayers()) {
			System.out.println(p.getHand());
		}
		//Counts for each player
		int nn, ii, aa, cc, su, ds;
		nn = ii = aa = cc = su = ds = 0;
		Set<Player> playerList = board.getPlayers();
		Set<Card> cardList = board.getCards(); //otherwise, the set is way too big
		for (int i = 0; i < 200; i++) {
			try {
				playerList.clear(); //new player list every time
				cardList.clear();
				board.loadSetupConfig(); 
				playerList = board.getPlayers();
			//	System.out.println(playerList.size());
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
		
		assertEquals(200, nn+ii+aa+cc+su+ds);
		assertTrue(nn > 10);
		assertTrue(ii > 10);
		assertTrue(aa > 10);
		assertTrue(cc > 10);
		assertTrue(su > 10);
		assertTrue(ds > 10);

	}
	
	@Test
	public void countTests() {
		
		System.out.println("countTests");
		for(Player p : board.getPlayers()) {
			System.out.println(p.getHand());
		}
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
	public void dealCardsTest() { 

		Set<Card> cardList = board.getCards();
		Set<Player> playerList = board.getPlayers();

	
		Solution s = board.getSol();
		Card sp = s.getPerson();
		Card sr = s.getRoom();
		Card sw = s.getWeapon();
	
		assertEquals(3, s.getSolSize());
		
		//Making sure no player has the solution
		for(Player p : playerList) {
			Set<Card> hand = p.getHand();
			assertFalse(hand.contains(sp));
			assertFalse(hand.contains(sr));
			assertFalse(hand.contains(sw));
		}
		
		//Making sure every player has roughly the same size hand
		ArrayList<Integer> handSizes = new ArrayList<Integer>();
		for(Player p : playerList) { 
			Set<Card> hand = p.getHand();
			handSizes.add(hand.size());
		}
		
		for(int size : handSizes) {
			assertTrue(size > 0 && size < cardList.size()/4);
		}
		Integer sum = handSizes.stream().reduce(0, (a, b) -> a + b); //source: https://www.baeldung.com/java-stream-sum
		assertTrue(sum.equals(cardList.size()-3)); //all cards but 3 were dealt
		
		//Testing to see if all cards were dealt and dealt only once
		//Adds 1 each time a card is recognized. Should be equal to size -3
		int cardCount = 0;
		for(Player p : playerList) { 
			Set<Card> hand = p.getHand();
			for(Card c : hand) {
				if(cardList.contains(c)) {
					cardCount++;
				}
				
			}
		}
		
		assertEquals(cardCount, cardList.size()-3);
		

	}
	
	

}
