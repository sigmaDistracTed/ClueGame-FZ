package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonListener;

public class GameControlPanel2 extends JPanel {
	private GameControlPanel2 gui;
    private JTextField nameTF;
	
	public GameControlPanel2()  {
		gui = this;
		
		//Initial panels
		JPanel gcp = new JPanel(new GridLayout(2, 0)); 
		JPanel topHalf = new JPanel(new GridLayout(1, 4));
		
		//Turn tracking -> may add sprites here? 
		JPanel turns = new JPanel(); 
		JLabel whosTurn = new JLabel("Who's turn is it?: "); 
		JTextField nameTF = new JTextField(20);
	
		turns.add(whosTurn);
		turns.add(nameTF);
		topHalf.add(turns);
		
		//Roll panel
		JPanel rollp = new JPanel();
		JLabel rollLabel = new JLabel("Roll: ");
		JTextField rollTF = new JTextField(20);
		rollp.add(rollLabel);
		rollp.add(rollTF);
		
		JButton makeAccusation = new JButton("Make Accusation"); 
		JButton next = new JButton("Next Turn"); 
		
		
		topHalf.add(rollp);
		topHalf.add(makeAccusation);
		topHalf.add(next);
		add(topHalf);

		
	}
	
	
	
	public static void main(String[] args) {
		GameControlPanel2 panel = new GameControlPanel2();  // create the panel
		JFrame frame = new JFrame("Clue Controls");  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
	/*	panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?"); */
	}

}


