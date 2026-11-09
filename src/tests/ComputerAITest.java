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

public class ComputerAITest {
    private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

    // Room matches current location
    @Test 
    public void testRoomMatchesLocation() {

    }

    // If only one weapon not seen, it's selected
    @Test
    public void testOneWeaponNotSeen() {

    }

    // If only one person not seen, it's selected (can be same test as weapon)
    @Test 
    public void testOnePersonNotSeen() {

    }

    // If multiple weapons not seen, one of them is randomly selected
    @Test
    public void testMultipleWeaponsNotSeen() {

    }

    // If multiple persons not seen, one of them is randomly selected
    @Test 
    public void testMultiplePeopleNotSeen() {

    }

    // If no rooms in list, select randomly
    @Test 
    public void testNoRoomsInList() {

    }

    // IF room in list that has not been seen, select it
    @Test 
    public void testRoomInListNotSeen() {

    }

    // If room in list that has been seen, each target (including room) selected randomly
    @Test 
    public void testRoomSeenTargetSelection() {

    }
}
