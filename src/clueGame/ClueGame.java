package clueGame;

import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
    private Board board;
    private String setupConfigFile;
    private String layoutConfigFile;

    private BoardPanel boardPanel;
    private GameControlPanel gameControlPanel;
    private KnownCardsPanel knownCardsPanel;

    public ClueGame() {
        initializeGame();
    }

    public ClueGame(String setupConfigFile, String layoutConfigFile) {
        this.setupConfigFile = setupConfigFile;
        this.layoutConfigFile = layoutConfigFile;
        initializeGame();
    }

    public void initializeGame() {
        setTitle("CSCI306 ClueGame");

        board = Board.getInstance();
        board.setConfigFiles(setupConfigFile, layoutConfigFile);

        try {
            board.initialize();
        } catch (Exception e) {
            System.out.println("Could not load ClueGame");
        }

        gameControlPanel = new GameControlPanel();
        knownCardsPanel = new KnownCardsPanel();
        boardPanel = new BoardPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(boardPanel, "Center");
        add(gameControlPanel, "South");
        add(knownCardsPanel, "East");
    }

    public static final void main(String args[]) {
        new ClueGame();
    }

}
