package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.plaf.basic.BasicButtonListener;

public class GameControlPanel3 extends JPanel {
	private GameControlPanel3 gui;
    private JTextField nameTF;
	
	public GameControlPanel3()  {
		gui = this;
		setLayout(new GridLayout(3, 0));
		setBorder(BorderFactory.createTitledBorder("Known Cards"));
		
		//Initial panels
		JPanel peoplePanel = new JPanel(new GridLayout(4, 0));
		JPanel roomsPanel = new JPanel(new GridLayout(4,0));
		JPanel weaponsPanel = new JPanel(new GridLayout(4,0));
		peoplePanel.setBorder(BorderFactory.createTitledBorder("People"));
		roomsPanel.setBorder(BorderFactory.createTitledBorder("Rooms"));
		weaponsPanel.setBorder(BorderFactory.createTitledBorder("Weapons"));

		// Define people panel contents
		JLabel peopleInHandLabel = new JLabel("In Hand:");
		JTextField peopleInHandText = new JTextField("Colonel Mustard");
		JLabel peopleSeenLabel = new JLabel("Seen:");
		JTextField peopleSeenText = new JTextField("Mrs. White");

		// Build people panel
		peoplePanel.add(peopleInHandLabel);
		peoplePanel.add(peopleInHandText);
		peoplePanel.add(peopleSeenLabel);
		peoplePanel.add(peopleSeenText);
		add(peoplePanel);

		// Define rooms panel contents
		JLabel roomsInHandLabel = new JLabel("In Hand:");
		JTextField roomsInHandText = new JTextField("Library");
		JLabel roomsSeenLabel = new JLabel("Seen:");
		JTextField roomsSeenText = new JTextField("Hedge Maze");

		// Build rooms panel
		roomsPanel.add(roomsInHandLabel);
		roomsPanel.add(roomsInHandText);
		roomsPanel.add(roomsSeenLabel);
		roomsPanel.add(roomsSeenText);
		add(roomsPanel);

		// Define weapons panel contents
		JLabel weaponsInHandLabel = new JLabel("In Hand:");
		JTextField weaponsInHandText = new JTextField("Crystal Ball");
		JLabel weaponsSeenLabel = new JLabel("Seen:");
		JTextField weaponsSeenText = new JTextField("Dowsing Rod");

		// Build weapons panel
		weaponsPanel.add(weaponsInHandLabel);
		weaponsPanel.add(weaponsInHandText);
		weaponsPanel.add(weaponsSeenLabel);
		weaponsPanel.add(weaponsSeenText);
		add(weaponsPanel);
	}
	
	private class ButtonListener implements ActionListener {
        @Override 
        public void actionPerformed(ActionEvent e) {
            String message = "Hello, " + nameTF.getText();
            JOptionPane.showMessageDialog(gui, message);

            String ageStr = JOptionPane.showInputDialog("Enter your age: ");
            int num = Integer.parseInt(ageStr);
            JOptionPane.showMessageDialog(gui, "You are " + num + " years old.");
            int ready = JOptionPane.showConfirmDialog(gui, "Are you ready?");

            if (ready == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(gui, "Here we go!");
            } else {
                JOptionPane.showMessageDialog(gui, "Ok, I'll wait!");
            }
        }
	}
	

	
	public static void main(String[] args) {
		GameControlPanel3 panel = new GameControlPanel3();  // create the panel
		JFrame frame = new JFrame("Clue Controls");  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(100, 1000);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
	/*	panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?"); */
	}

}