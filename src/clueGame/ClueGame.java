package clueGame;

import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.plaf.TreeUI;

public class ClueGame extends JFrame {
    private Board board;
    private String setupConfigFile;
    private String layoutConfigFile;

    private BoardPanel boardPanel;
    private GameControlPanel gameControlPanel;
    private KnownCardsPanel knownCardsPanel;
    private KnownCardsPanel playersPanel;

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
            System.out.println(e.getMessage());
        }

        gameControlPanel = new GameControlPanel();
        knownCardsPanel = new KnownCardsPanel();
        playersPanel = new KnownCardsPanel();
        boardPanel = new BoardPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        add(boardPanel, "Center");
        add(gameControlPanel, "South");
        add(knownCardsPanel, "East");
        add(playersPanel, "West");

        setSize(1000,1000);
    }

    public static final void main(String args[]) {
        new ClueGame("ClueLayout.csv", "ClueSetup.txt");
    }

}
