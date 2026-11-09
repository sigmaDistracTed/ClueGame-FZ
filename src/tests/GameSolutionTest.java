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
		Player pManyCard = new HumanPlayer("Many", "White", 0,0);
		
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
			pManyCard.updateSeen(seenCard);
		}

		assertEquals(pManyCard.getSeen().size(), 1); //may get fucky
		
	}
	
	@Test
	public void testHandleSuggestion() { 
		/*  Handle a suggestion made, tests include:
Suggestion no one can disprove returns null
Suggestion only suggesting player can disprove returns null
Suggestion only human can disprove returns answer 
	(i.e., card that disproves suggestion)
Suggestion that two players can disprove, 
	correct player (based on starting with next player in list) returns answer
*/
	Player sp = new ComputerPlayer("Suggesting", "White", 0,0);
	Player p1 = new HumanPlayer("Player 1", "White", 0,0);
	Player p2 = new ComputerPlayer("Player 2", "White", 0,0);
	
	Set<Player> playerList = new HashSet<Player>();
	playerList.add(sp);
	playerList.add(p1);
	playerList.add(p2);
	
	Card p = new Card("PCJ", CardType.PERSON);
	Card r = new Card ("Coolbaugh 209", CardType.ROOM);
	Card w = new Card ("Notebook", CardType.WEAPON);
	Card person2 = new Card("Blaster", CardType.PERSON); 
	Card room2 = new Card("Green Center", CardType.ROOM); 
	Card weapon2 = new Card("Protractor", CardType.WEAPON); 
	
	sp.updateHand(p);
	p1.updateHand(r);
	p2.updateHand(w);
	
	//no one can disprove, returns null
	Card noProof = board.handleSuggestion(sp, playerList, room2, person2, weapon2);
	assertEquals(noProof, null);
	
	//suggesting player can disprove, returns null
	Card suggestingDisproves = board.handleSuggestion(sp, playerList, room2, p, weapon2);
	assertEquals(suggestingDisproves, null);
	
	//only human player can disprove, returns answer
	Card humanDisproves = board.handleSuggestion(sp, playerList, r, person2, weapon2);
	assertEquals(humanDisproves, r);
	
	//suggestion two players can disprove, returns first card
	Card twoPlayers = board.handleSuggestion(sp, playerList, r, p, weapon2);
	assertEquals(twoPlayers, r);
	
	
	
	
			
		
		
	}

}
