package tests;

import static org.junit.Assert.*;
import java.util.*;
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
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;
import clueGame.HumanPlayer;
import clueGame.ComputerPlayer;

public class ComputerAITest {
    private static Board board;
    private static ComputerPlayer computer;

    @BeforeEach
    public void setUp() {
        // Board is singleton, get the only instance
        board = Board.getInstance();
        // set the file names to use my config files
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        // Initialize will load BOTH config files
        board.initialize();

        // Create a computer player at the Foyer center
        computer = new ComputerPlayer("Test", "WHITE", 12, 3);
    }

    // Room matches current location
    @Test
    public void testRoomMatchesLocation() {
        Solution suggestion = computer.createSuggestion();

        assertEquals("Foyer", suggestion.getRoom().getCardName());
    }

    // If only one weapon not seen, it's selected
    @Test
    public void testOneWeaponNotSeen() {
        Card unseenWeapon = null;

        for (Card card : board.getCards()) {
            if (card.getCardType() == CardType.WEAPON) {
                if (unseenWeapon == null) {
                    unseenWeapon = card;
                } else {
                    computer.updateSeen(card);
                }
            }
        }

        assertEquals(unseenWeapon, computer.createSuggestion().getWeapon());
    }

    // If only one person not seen, it's selected (can be same test as weapon)
    @Test
    public void testOnePersonNotSeen() {
        Card unseenPerson = null;

        for (Card card : board.getCards()) {
            if (card.getCardType() == CardType.PERSON) {
                if (unseenPerson == null) {
                    unseenPerson = card;
                } else {
                    computer.updateSeen(card);
                }
            }
        }

        assertEquals(unseenPerson, computer.createSuggestion().getPerson());
    }

    // If multiple weapons not seen, one of them is randomly selected
    @Test
    public void testMultipleWeaponsNotSeen() {
        ArrayList<Card> unseen = new ArrayList<>();

        for (Card card : board.getCards()) {
            if (card.getCardType() == CardType.WEAPON) {
                if (unseen.size() < 2) {
                    unseen.add(card);
                } else {
                    computer.updateSeen(card);
                }
            }
        }

        Set<Card> selected = new HashSet<>();

        for (int i = 0; i < 200; i++) {
            Card weapon = computer.createSuggestion().getWeapon();

            assertTrue(unseen.contains(weapon));
            selected.add(weapon);
        }

        assertEquals(2, selected.size());
    }

    // If multiple persons not seen, one of them is randomly selected
    @Test
    public void testMultiplePeopleNotSeen() {
        ArrayList<Card> unseen = new ArrayList<>();

        for (Card card : board.getCards()) {
            if (card.getCardType() == CardType.PERSON) {
                if (unseen.size() < 2) {
                    unseen.add(card);
                } else {
                    computer.updateSeen(card);
                }
            }
        }

        Set<Card> selected = new HashSet<>();

        for (int i = 0; i < 200; i++) {
            Card person = computer.createSuggestion().getPerson();

            assertTrue(unseen.contains(person));
            selected.add(person);
        }

        assertEquals(2, selected.size());
    }

    // If no rooms in list, select randomly
    @Test
    public void testNoRoomsInList() {
        BoardCell first = board.getCell(7, 3);
        BoardCell second = board.getCell(7, 4);

        // Give computer two specific choices
        board.getTargets().clear();
        board.getTargets().add(first);
        board.getTargets().add(second);

        Set<BoardCell> selected = new HashSet<>();

        for (int i = 0; i < 200; i++) {
            BoardCell target = computer.selectTarget();

            assertTrue(target == first || target == second);
            selected.add(target);
        }

        assertEquals(2, selected.size());
    }

    // IF room in list that has not been seen, select it
    @Test
    public void testRoomInListNotSeen() {
        BoardCell foyer = board.getRoom('F').getCenterCell();
        BoardCell walkway = board.getCell(7, 3);

        board.getTargets().clear();
        board.getTargets().add(foyer);
        board.getTargets().add(walkway);

        for (int i = 0; i < 200; i++) {
            assertEquals(foyer, computer.selectTarget());
        }
    }

    // If room in list that has been seen, each target (including room) selected
    // randomly
    @Test
    public void testRoomSeenTargetSelection() {
        BoardCell foyer = board.getRoom('F').getCenterCell();
        BoardCell walkway = board.getCell(7, 3);

        for (Card card : board.getCards()) {
            if (card.getCardType() == CardType.ROOM
                    && card.getCardName().equals("Foyer")) {
                computer.updateSeen(card);
            }
        }

        board.getTargets().clear();
        board.getTargets().add(foyer);
        board.getTargets().add(walkway);

        Set<BoardCell> selected = new HashSet<>();

        for (int i = 0; i < 200; i++) {
            BoardCell target = computer.selectTarget();

            assertTrue(target == foyer || target == walkway);
            selected.add(target);
        }

        assertEquals(2, selected.size());
    }
}
