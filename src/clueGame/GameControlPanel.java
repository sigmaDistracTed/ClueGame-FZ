package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

public class GameControlPanel extends JPanel {
	private GameControlPanel gui;
	private static Board board;
    private final JTextField nameTF;
    private final JTextField rollTF;
    private JTextField guessField;
    private JTextField guessResultField;
	private JButton next;
	private JButton makeAccusation;
	private JPanel turns;
	private ArrayList<Player> playerList = new ArrayList<Player>(); 
	private int idx;
	private int roll;
	private String playerName;
	private static PlayersPanel players;
	private ModalBox mb;
	private JComboBox peopleBox;
	private JComboBox weaponBox;
	private JComboBox roomBox;
	private boolean humanFinished;
	private int rows, cols;
	
	
	
	public GameControlPanel(PlayersPanel players) {
		gui = this;
		this.players = players;
		setLayout(new GridLayout(2, 0));
		
		board = Board.getInstance(); 
		//board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		//board.initialize(); //so we have the players
		
		  rows = board.getNumRows();
	      cols = board.getNumColumns();
		
		for (Player p : board.getPlayers()) {
			playerList.add(p);
			if(p instanceof HumanPlayer) {
				idx = playerList.indexOf(p);
			}
		}
		
		
		//Initial panels
		JPanel topHalf = new JPanel(new GridLayout(1, 4));
		JPanel bottomHalf = new JPanel(new GridLayout(0,2));
		
		//Turn tracking
		turns = new JPanel(); 
		JLabel whosTurn = new JLabel("Who's turn is it?: "); 
		nameTF = new JTextField(20);
		
		//putting in human player to start
		Player p = playerList.get(idx);
		playerName = p.getName();
		//players = new PlayersPanel(playerName);
		nameTF.setText(playerName); 
		nameTF.setBackground(p.getColor());
		nameTF.setEditable(false);
		if(p.getColor().equals(Color.decode("#000000"))) { 
			nameTF.setForeground(Color.WHITE); 
		}
		else { 
			nameTF.setForeground(Color.BLACK);
		}
		
		
		
		//adding to panel
		turns.add(whosTurn);
		turns.add(nameTF);
	//	turns.add(sprites);

		//Roll panel
		JPanel rollp = new JPanel();
		JLabel rollLabel = new JLabel("Roll: ");
		rollTF = new JTextField(10);
		roll = (int)((Math.random() * 6)+1); 
		rollTF.setText(String.valueOf(roll));
		rollTF.setEditable(false);
		rollp.add(rollLabel);
		rollp.add(rollTF);
		
		int startX = p.getRow();
		int startY = p.getCol();
		 board.calcTargets(board.getCell(startX, startY), roll);
		 Set<BoardCell> targets = board.getTargets();
		 repaint(); 
		
		// Buttons
		makeAccusation = new JButton("Make Accusation"); 
		next = new JButton("Next Turn"); 
		makeAccusation.addActionListener(new ButtonListener());
		next.addActionListener(new ButtonListener());

		// Guess
		JPanel guessPanel = new JPanel(new GridLayout(1,0));
		guessField = new JTextField("No Guess!");
		guessPanel.setBorder(BorderFactory.createTitledBorder("Guess"));
		guessPanel.setName("Guess");
		guessPanel.add(guessField);

		// Guess Result
		JPanel guessResultPanel = new JPanel(new GridLayout(1,0));
		guessResultField = new JTextField("So you have nothing?");
		guessResultPanel.setBorder(BorderFactory.createTitledBorder("Guess Result"));
		guessResultPanel.setName("Guess Result");
		guessResultPanel.add(guessResultField);
		

		topHalf.add(turns);
		topHalf.add(rollp);
		topHalf.add(makeAccusation);
		topHalf.add(next);
		//topHalf.add(sprites);
		add(topHalf);

		bottomHalf.add(guessPanel);
		bottomHalf.add(guessResultPanel);
		add(bottomHalf);
		//add(players);
		
		
	}
	
	private class ButtonListener implements ActionListener {
        @Override 
        public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == next) {
        		Player currTurn = playerList.get(idx%playerList.size());
        		if(currTurn.equals(playerName) ) {
        			humanFinished = currTurn.getTurnFinished();
        		}
        		if(!humanFinished) { 
        			JOptionPane error = new JOptionPane();
        			error.showMessageDialog(null, "Please finish your turn.");
        		}
        		roll = (int)((Math.random() * 6)+1);
        		
        		int x = currTurn.getRow(); 
        		int y = currTurn.getCol();
        		
        		board.calcTargets(board.getCell(x, y), roll);
        		 Set<BoardCell> targets = board.getTargets();
        		 repaint(); 
        		 
   
        		++idx; 
        		Player nextPlayer = playerList.get(idx%playerList.size());
        		playerName = nextPlayer.getName();
        		nameTF.setBackground(nextPlayer.getColor());
        		if(nextPlayer.getColor().equals(Color.decode("#000000"))) { 
        			nameTF.setForeground(Color.WHITE);
        		}
        		else { 
        			nameTF.setForeground(Color.BLACK);
        		}
        		nameTF.setText(playerName); 
				nameTF.setEditable(false);
        		roll = (int)((Math.random() * 6)+1);
        		rollTF.setText(String.valueOf(roll));
				rollTF.setEditable(false);
				
				players.UpdatePanel(playerName);
				
				int currRow = currTurn.getRow();
				int currCol = currTurn.getCol();
				
				if (board.getCell(currRow, currCol).isRoomCenter()) {
					mb = new ModalBox(true, board.getName()); 
					add(mb);
					setGuessField(); 
					setGuessResult("card"); 
				}

        	}
        	
        	if(e.getSource() == makeAccusation) {
        		mb = new ModalBox(false, null); 
        	}

        	
        }
	}
	
	
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setGuessField(String p, String r, String w) {
		guessField.setText("It was " + p + "in the " + r + "with the " + w + "!");
	}
	
	public void setGuessField() {
		guessField.setText("No guess");
	}
	
	public void setGuessResult(String card) {
		guessResultField.setText(card);
		
	}
	

	
	public static void main(String[] args) {
		
		GameControlPanel panel = new GameControlPanel(players);  // create the panel
	//	players = new PlayersPanel(panel.getPlayerName());
		JFrame frame = new JFrame("Clue Controls"); 
		frame.add(players, BorderLayout.WEST);// create the frame 
		frame.add(panel, BorderLayout.CENTER); // put the panel in the frame
		//dont set content pane
		frame.setSize(950, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
	/*	panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?"); */
	}

}