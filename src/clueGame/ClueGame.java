package clueGame;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.plaf.TreeUI;

public class ClueGame extends JFrame {
    private Board board;
    private String setupConfigFile;
    private String layoutConfigFile;
    private String playerName; 

    private BoardPanel boardPanel;
    private GameControlPanel gameControlPanel;
    private KnownCardsPanel knownCardsPanel;
    private PlayersPanel playersPanel;

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
        

        playerName = board.getHumanPlayer().getName(); 
        
        playersPanel = new PlayersPanel(playerName); 
        gameControlPanel = new GameControlPanel(playersPanel);
        knownCardsPanel = new KnownCardsPanel();
        boardPanel = new BoardPanel();
        
        
        gameControlPanel.setPreferredSize(new Dimension(800, 200));
        playersPanel.setPreferredSize(new Dimension(800, 800));
        boardPanel.setPreferredSize(new Dimension(800, 800));
        knownCardsPanel.setPreferredSize(new Dimension(200, 800));
        
        
        setExtendedState(JFrame.MAXIMIZED_BOTH); //opens in full screen for sprite appearances
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        add(playersPanel, "West");
        add(boardPanel, "Center");
        add(gameControlPanel, "South");
        add(knownCardsPanel, "East");
        
        JOptionPane intro = new JOptionPane("Welcome to clue!");
        intro.showMessageDialog(null, "Welcome to Clue! \n" + "You are " + playerName + ". \n" + "Since the one and only detective is out, can you solve the murder (that you maybe commited?)");
        add(intro);

      //  setSize(1000,1000);
    }

    public static final void main(String args[]) {
        new ClueGame("ClueLayout.csv", "ClueSetup.txt");
    }

}
