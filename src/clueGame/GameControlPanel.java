package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class GameControlPanel extends JPanel {
	private GameControlPanel gui;
	private static Board board;
    private final JTextField nameTF;
    private final JTextField rollTF;
	private JButton next;
	private JPanel turns;
	private ArrayList<Player> playerList = new ArrayList<Player>(); 
	private int idx;
	private int roll;
	private String path;
	
	public GameControlPanel() {
		gui = this;
		setLayout(new GridLayout(2, 0));
		
		board = Board.getInstance(); 
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize(); //so we have the players
		
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
		nameTF.setText(p.getName());
		nameTF.setBackground(p.getColor());
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
		rollp.add(rollLabel);
		rollp.add(rollTF);
		
		// Buttons
		JButton makeAccusation = new JButton("Make Accusation"); 
		next = new JButton("Next Turn"); 
		makeAccusation.addActionListener(new ButtonListener());
		next.addActionListener(new ButtonListener());

		// Guess
		JPanel guessPanel = new JPanel(new GridLayout(1,0));
		JTextField guessField = new JTextField("No Guess!");
		guessPanel.setBorder(BorderFactory.createTitledBorder("Guess"));
		guessPanel.setName("Guess");
		guessPanel.add(guessField);

		// Guess Result
		JPanel guessResultPanel = new JPanel(new GridLayout(1,0));
		JTextField guessResultField = new JTextField("So you have nothing?");
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
		
		
	}
	
	private class ButtonListener implements ActionListener {
		private int idx = 0;
        @Override 
        public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == next) {
        		++idx; //gets wonky and misses turns somehow?????
        		Player nextPlayer = playerList.get(idx%playerList.size());
        		nameTF.setBackground(nextPlayer.getColor());
        		if(nextPlayer.getColor().equals(Color.decode("#000000"))) { 
        			nameTF.setForeground(Color.WHITE);
        		}
        		else { 
        			nameTF.setForeground(Color.BLACK);
        		}
        		nameTF.setText(nextPlayer.getName()); 
        		roll = (int)((Math.random() * 6)+1);
        		rollTF.setText(String.valueOf(roll));
        	//	sprites.add(new JLabel(new ImageIcon(path+nextPlayer.getName()+".png")));
        		//turns.add(sprites);
        	}
        	
        //	if(e.getSource() == makeAccusation) {
        		
        	//}
        	
        }
	}
	

	
	public static void main(String[] args) {
		
		GameControlPanel panel = new GameControlPanel();  // create the panel
		PlayersPanel players = new PlayersPanel();
		JFrame frame = new JFrame("Clue Controls"); 
		frame.add(players, BorderLayout.EAST);// create the frame 
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