package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

public class GameControlPanel extends JPanel {
	private GameControlPanel gui;
	private static Board board;
    private final JTextField nameTF;
    private final JTextField rollTF;
	private JButton next;
	private JButton makeAccusation;
	private JPanel turns;
	private ArrayList<Player> playerList = new ArrayList<Player>(); 
	private int idx;
	private int roll;
	private String playerName;
	private PlayersPanel players; 
	private JComboBox peopleBox;
	private JComboBox weaponBox;
	private JComboBox roomBox;
	
	
	public GameControlPanel(PlayersPanel players) {
		gui = this;
		this.players = players;
		setLayout(new GridLayout(2, 0));
		
		board = Board.getInstance(); 
		//board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		//board.initialize(); //so we have the players
		
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
		
		// Buttons
		makeAccusation = new JButton("Make Accusation"); 
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
		//add(players);
		
		
	}
	
	private class ButtonListener implements ActionListener {
        @Override 
        public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == next) {
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

        	}
        	
        	if(e.getSource() == makeAccusation) {
        	/*	ArrayList<String> people = new ArrayList<String>(); 
        		ArrayList<String> weapons = new ArrayList<String>(); 
        		ArrayList<String> room = new ArrayList<String>(); 
        		for(Card c : board.getCards()) {
        			switch(c.getCardType()) { 
        			case CardType.PERSON:
        				people.add(c.getCardName());
        				break;
        			case CardType.WEAPON:
        				weapons.add(c.getCardName());
        				break;
        			case CardType.ROOM:
        				room.add(c.getCardName());
        				break;
        			default: 
        				break;
        			}
        		}
        		
        		String[] people2 = people.toArray(new String[0]);
        		String[] weapon2 = weapons.toArray(new String[0]);
        		String[] room2 = room.toArray(new String[0]);
        		
        		
        		peopleBox = new JComboBox(people2);
        		peopleBox.setSelectedIndex(0);
        		peopleBox.addActionListener(this);
        		
        		weaponBox = new JComboBox(weapon2);
        		weaponBox.setSelectedIndex(0);
        		weaponBox.addActionListener(this);
        		
        		roomBox = new JComboBox(room2);
        		roomBox.setSelectedIndex(0);
        		roomBox.addActionListener(this);
        		
        		JPanel accusation = new JPanel();
        		accusation.add(peopleBox);
        		accusation.add(weaponBox);
        		accusation.add(roomBox);
        		add(accusation); */
        	}

        	
        }
	}
	
	private class ComboBox implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	if(e.getSource() == peopleBox) {
	    		Object pc = peopleBox.getSelectedItem(); 
	    		for(Card c : board.getCards()) { 
	    			if(c.getCardName().equals(pc)) {
	    				System.out.println("Get Person Card");
	    			}
	    		}
	    		
	    	}
	    	
	    	else if(e.getSource() == weaponBox) {
	    		Object wc = weaponBox.getSelectedItem(); 
	    		for(Card c : board.getCards()) { 
	    			if(c.getCardName().equals(wc)) {
	    				System.out.println("Get Weapon Card");
	    			}
	    		}
	    		
	    	}
	    	
	    	else if(e.getSource() == roomBox) {
	    		Object rc = roomBox.getSelectedItem(); 
	    		for(Card c : board.getCards()) { 
	    			if(c.getCardName().equals(rc)) {
	    				System.out.println("Get room Card");
	    			}
	    		}
	    		
	    	}
	    }

	}
	
	public String getPlayerName() {
		return playerName;
	}
	

	
	public static void main(String[] args) {
		
		GameControlPanel panel = new GameControlPanel();  // create the panel
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