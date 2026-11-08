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

public class GameSolutionTest {
	
private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.dealCards(board.getCards());
	}
	
	@Test
	public void testAccusation() {
		//initializing cards for solution 
		Solution s = board.getSol();
		Card p = s.getPerson();
		Card r = s.getRoom();
		Card w = s.getWeapon();
		
		//making new cards because the solution is randomized and i dont wanna
		//ruin the tests if i accidentally make a card that equals the sol
		//and there's no guarantee a player has all three types of card
		Card p2 = new Card("PCJ", CardType.PERSON);
		Card r2 = new Card ("Coolbaugh 209", CardType.ROOM);
		Card w2 = new Card ("Notebook", CardType.WEAPON);
				
		
		//tests
		assertTrue(board.checkAccusation(r, p, w)); 
		assertFalse(board.checkAccusation(r2, p, w));
		assertFalse(board.checkAccusation(r, p2, w));
		assertFalse(board.checkAccusation(r, p, w2));
		assertFalse(board.checkAccusation(r2, p2, w2));
	}
	
	@Test
	public void testDisproveSuggestion() {
		/* If a player has one matching card, return it. 
		 * Simple way to test: create a player with known cards, call the disprove method, 
		 * ensure desired card is returned. Consider testing returning a room, 
		 * a weapon and a person, but this may be overkill 
		 * (does ensure matches happen regardless of card type).
If a player has no matching cards, return null. Test as above, but 
obviously with no matching card.
If the player has multiple cards that match, return should be random. 
How to test? Set up player, use a loop, ensure that each matching card 
is returned at least once.*/
		
		//creating person with known hand
		Card testPerson = new Card("PCJ", CardType.PERSON);
		Card testRoom = new Card ("Coolbaugh 209", CardType.ROOM);
		Card testWeapon = new Card ("Notebook", CardType.WEAPON);
		Card person2 = new Card("Blaster", CardType.PERSON); 
		Card room2 = new Card("Green Center", CardType.ROOM); 
		Card weapon2 = new Card("Protractor", CardType.WEAPON); 
		
		Player pTest = new HumanPlayer("Test", "White", 0, 0);
		pTest.updateHand(testPerson);
		pTest.updateHand(testRoom);
		pTest.updateHand(testWeapon);
		
		Player pOneCard = new HumanPlayer("Known", "White", 0,0);
		
		//testing if one card matches
		Card seenCard = pTest.disproveSuggestion(testRoom, person2, weapon2);
		if(seenCard != null) {
			pOneCard.updateSeen(seenCard);
		}
		
		assertEquals(seenCard, testRoom);
		assertEquals(pOneCard.getSeen().size(), 1);
		
		seenCard = pTest.disproveSuggestion(room2, testPerson, weapon2);
		if(seenCard != null) {
			pOneCard.updateSeen(seenCard);
		}
		
		assertEquals(seenCard, testPerson);
		assertEquals(pOneCard.getSeen().size(), 2);
		
		seenCard = pTest.disproveSuggestion(room2, person2, testWeapon);
		if(seenCard != null) {
			pOneCard.updateSeen(seenCard);
		}
		
		assertEquals(seenCard, testWeapon);
		assertEquals(pOneCard.getSeen().size(),3);
		
		//testing if no cards match
		seenCard = pTest.disproveSuggestion(room2, person2, weapon2);
		if(seenCard != null) {
			pOneCard.updateSeen(seenCard);
		}
		assertEquals(seenCard, null);
		assertEquals(pOneCard.getSeen().size(),3);
		
		//testing if multiple cards match, only one is returned
		seenCard = pTest.disproveSuggestion(testRoom, testPerson, testWeapon);
		if(seenCard != null) {
			pOneCard.updateSeen(seenCard);
		}

		assertEquals(pOneCard.getSeen().size(), 4); //may get fucky
		
	}
	
	@Test
	public void testHandleSuggestion() { 
		
	}

}
