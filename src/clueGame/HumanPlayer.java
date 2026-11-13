package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {
    private KnownCardsPanel knownCardsPanel;

    // Constructor
    public HumanPlayer(String name, String color, int row, int col) {
        super(name, color, row, col);
    }

    @Override
    public void updateSeen(Card card) {
    	super.updateSeen(card);

        if (knownCardsPanel != null) {
            knownCardsPanel.refreshCardsGUI();
        }
    }

    public void setKnownCardsPanel(KnownCardsPanel panel) {
        this.knownCardsPanel = panel;
    }
}
