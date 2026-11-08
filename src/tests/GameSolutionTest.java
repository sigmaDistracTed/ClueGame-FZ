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
		
		System.out.println(s);
		System.out.println(r + " " +  p + " "+ w);
		
		
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

}
