package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicButtonListener;

public class SpritesTesting extends JPanel {
		private SpritesTesting gui;
		private static Board board;
	    private final JTextField nameTF;
	    private final JTextField rollTF;
	    private JLabel sprites;
		private JButton next;
		private ArrayList<Player> playerList = new ArrayList<Player>(); 
		private int idx;
		private int roll;
		private String path;
		
		public SpritesTesting() {
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
			
			//Turn tracking -> may add sprites here? 
			JPanel turns = new JPanel(); 
			JLabel whosTurn = new JLabel("Who's turn is it?: "); 
			nameTF = new JTextField(20);
			
			//putting in human player to start
			Player p = playerList.get(idx);
			nameTF.setText(p.getName());
			nameTF.setBackground(p.getColor());
			if(p.getColor() == Color.BLACK) { 
				nameTF.setForeground(Color.WHITE); //not quite working
			}
			else { 
				nameTF.setForeground(Color.BLACK);
			}
			
			//sprites
			sprites = new JLabel();
			path = "/ClueGame/Sprites/";
			sprites.setIcon(new ImageIcon(new ImageIcon("/ClueGame/Sprites/Dr. Seashell DDS.png").getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH)));
			System.out.println(path+p.getName()+".png");
			//adding to panel
			turns.add(whosTurn);
			turns.add(nameTF);

			//Roll panel
			JPanel rollp = new JPanel();
			JLabel rollLabel = new JLabel("Roll: ");
			rollTF = new JTextField(10);
			roll = (int)(Math.random() * 6);
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
			
			
			


	/* 		// In hand and seen
			JPanel tempName = new JPanel();
			JLabel knownCards = new JLabel("Known Cards");
			JTextField inHand = new JTextField(20);
			tempName.add(knownCards);
			tempName.add(inHand);
			rightSide.add(tempName); */

			topHalf.add(turns);
			topHalf.add(rollp);
			topHalf.add(makeAccusation);
			topHalf.add(next);
			topHalf.add(sprites);
			add(topHalf);

			bottomHalf.add(guessPanel);
			bottomHalf.add(guessResultPanel);
			add(bottomHalf);


			//add(rightSide, BorderLayout.EAST);
			
			
			
		}
		
		private class ButtonListener implements ActionListener {
			private int idx = 0;
	        @Override 
	        public void actionPerformed(ActionEvent e) {
	        	if(e.getSource() == next) {
	        		++idx; //gets wonky and misses turns somehow?????
	        		Player nextPlayer = playerList.get(idx%playerList.size());
	        		nameTF.setBackground(nextPlayer.getColor());
	        		if(nextPlayer.getColor() == Color.BLACK) { 
	        			nameTF.setForeground(Color.WHITE);
	        		}
	        		else { 
	        			nameTF.setForeground(Color.BLACK);
	        		}
	        		nameTF.setText(nextPlayer.getName()); 
	        		roll = (int)(Math.random() * 6);
	        		rollTF.setText(String.valueOf(roll));
	        		sprites.add(new JLabel(new ImageIcon(path+nextPlayer.getName()+".png")));
	        		System.out.println(path+nextPlayer.getName()+".png");
	        	}
	        	
	        }
		}
		
		

		
		public static void main(String[] args) {
			
			JPanel panel = new JPanel();  // create the panel
			JFrame frame = new JFrame("Clue Controls");  // create the frame 
			JLabel sprites = new JLabel();
			sprites.setIcon(new ImageIcon(new ImageIcon("../ClueGame/Sprites/Dr. Seashell DDS.png").getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH)));
			frame.add(sprites);
	        frame.pack();
	        frame.setVisible(true);  
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // make it visible
			
			// test filling in the data
		/*	panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
			panel.setGuess( "I have no guess!");
			panel.setGuessResult( "So you have nothing?"); */
		}

	}