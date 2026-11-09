package clueGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class ComputerPlayer extends Player {

    // Constructor
    public ComputerPlayer(String name, String color, int row, int col) {
        super(name, color, row, col);
    }

    public Solution createSuggestion() {
        Board board = Board.getInstance();
        BoardCell location = board.getCell(getRow(), getCol());

        String roomName = board.getRoom(location).getName();
        Card room = null;
        List<Card> people = new ArrayList<>();
        List<Card> weapons = new ArrayList<>();

        for (Card card : board.getCards()) {
            if (card.getCardType() == CardType.ROOM
        && card.getCardName().equals(roomName)) {
            room = card;
            }

            if (isKnown(card)) {
                continue;
            }

            if (card.getCardType() == CardType.PERSON) {
                people.add(card);
            } else if (card.getCardType() == CardType.WEAPON) {
                weapons.add(card);
            }
        }

        if (room == null) {
            System.out.println("Not found.");
        }

        Random rand = new Random();
        int personRand = rand.nextInt(people.size());
        int weaponsRandom = rand.nextInt(weapons.size());

        return new Solution(room, people.get(personRand), weapons.get(weaponsRandom));
    }

    public BoardCell selectTarget() {
        Board board = Board.getInstance();
        Set<BoardCell> targets = board.getTargets();
        List<BoardCell> unseenRooms = new ArrayList<>();

        for (BoardCell target : targets) {
            if (!target.isRoomCenter()) {
                continue;
            }

            String roomName = board.getRoom(target).getName();

            for (Card card : board.getCards()) {
                if (card.getCardType() == CardType.ROOM
                        && card.getCardName().equals(roomName)
                        && !isKnown(card)) {
                    unseenRooms.add(target);
                    break;
                }
            }
        }

        Random rand = new Random();

        if (!unseenRooms.isEmpty()) {
            int unseenRand = rand.nextInt(unseenRooms.size());
            return unseenRooms.get(unseenRand);
        }

        if (targets.isEmpty()) {
            return null;
        }

        List<BoardCell> targetList = new ArrayList<>(targets);
        int targetsRandom = rand.nextInt(targetList.size());
        return targetList.get(targetsRandom);
    }

    public boolean containsCard(Set<Card> cards, Card target) {
        for (Card card : cards) {
            if (card.getCardType() == target.getCardType()
                && card.getCardName().equals(target.getCardName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isKnown(Card card) {
        return containsCard(getHand(), card) || containsCard(getSeen(), card);
    }

}
