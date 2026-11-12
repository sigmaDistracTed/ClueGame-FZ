package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.plaf.basic.BasicButtonListener;

public class KnownCardsGUI extends JPanel {
	private KnownCardsGUI gui;

	private Player huP;

	private JPanel peoplePanel;
	private JPanel roomsPanel;
	private JPanel weaponsPanel;

	private JPanel peopleHandPanel;
	private JPanel peopleSeenPanel;
	
	private JPanel roomsHandPanel;
	private JPanel roomsSeenPanel;

	private JPanel weaponsHandPanel;
	private JPanel weaponsSeenPanel;
	
	public KnownCardsGUI() {
		gui = this;
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createTitledBorder("Known Cards"));

		// Find the human player
		for (Player player : Board.getInstance().getPlayers()) {
			if (player instanceof HumanPlayer) {
				huP = player;
				break;
			}
		}

		// Create category panels
		peoplePanel = new JPanel(new GridBagLayout());
		roomsPanel = new JPanel(new GridBagLayout());
		weaponsPanel = new JPanel(new GridBagLayout());

		peoplePanel.setBorder(BorderFactory.createTitledBorder("People"));
		roomsPanel.setBorder(BorderFactory.createTitledBorder("Rooms"));
		weaponsPanel.setBorder(BorderFactory.createTitledBorder("Weapons"));

		// Create card containers
		peopleHandPanel = new JPanel(new GridLayout(0, 1, 0, 1));
		peopleSeenPanel = new JPanel(new GridLayout(0, 1, 0, 2));
		roomsHandPanel = new JPanel(new GridLayout(0, 1, 0, 1));
		roomsSeenPanel = new JPanel(new GridLayout(0, 1, 0, 2));
		weaponsHandPanel = new JPanel(new GridLayout(0, 1, 0, 1));
		weaponsSeenPanel = new JPanel(new GridLayout(0, 1, 0, 2));

		// Fill hand containers
		for (Card card : huP.getHand()) {
			JTextField field = createCardField(card.getCardName());

			switch (card.getCardType()) {
				case PERSON: peopleHandPanel.add(field);
				case ROOM: roomsHandPanel.add(field);
				case WEAPON: weaponsHandPanel.add(field);
			}
		}

		if (peopleHandPanel.getComponentCount() == 0) {
			peopleHandPanel.add(createCardField("None"));
		}
		if (roomsHandPanel.getComponentCount() == 0) {
			roomsHandPanel.add(createCardField("None"));
		}
		if (weaponsHandPanel.getComponentCount() == 0) {
			weaponsHandPanel.add(createCardField("None"));
		}

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2, 4, 2, 4);

		// People
		c.gridy = 0;
		peoplePanel.add(new JLabel("In Hand:"), c);

		c.gridy = 1;
		peoplePanel.add(peopleHandPanel, c);

		c.gridy = 2;
		peoplePanel.add(new JLabel("Seen:"), c);

		c.gridy = 3;
		peoplePanel.add(peopleSeenPanel, c);

		// Rooms
		c.gridy = 0;
		roomsPanel.add(new JLabel("In Hand:"), c);

		c.gridy = 1;
		roomsPanel.add(roomsHandPanel, c);

		c.gridy = 2;
		roomsPanel.add(new JLabel("Seen:"), c);

		c.gridy = 3;
		roomsPanel.add(roomsSeenPanel, c);

		// Weapons
		c.gridy = 0;
		weaponsPanel.add(new JLabel("In Hand:"), c);

		c.gridy = 1;
		weaponsPanel.add(weaponsHandPanel, c);

		c.gridy = 2;
		weaponsPanel.add(new JLabel("Seen:"), c);

		c.gridy = 3;
		weaponsPanel.add(weaponsSeenPanel, c);

		add(peoplePanel);
		add(roomsPanel);
		add(weaponsPanel);

		refreshCardsGUI();
	}

	public void refreshCardsGUI() {
		peopleSeenPanel.removeAll();
		weaponsSeenPanel.removeAll();
		roomsSeenPanel.removeAll();

		for (Card card : huP.getSeen()) {
			JTextField field = createCardField(card.getCardName());
			switch(card.getCardType()) {
				case PERSON: peopleSeenPanel.add(field);
				case ROOM: roomsSeenPanel.add(field);
				case WEAPON: weaponsSeenPanel.add(field);
			}
		}

		if (peopleSeenPanel.getComponentCount() == 0) {
			peopleSeenPanel.add(createCardField("None"));
		}
		if (roomsSeenPanel.getComponentCount() == 0) {
			roomsSeenPanel.add(createCardField("None"));
		}
		if (weaponsSeenPanel.getComponentCount() == 0) {
			weaponsSeenPanel.add(createCardField("None"));
		}
	}

	private JTextField createCardField(String text) {
		JTextField field = new JTextField(text);
		field.setEditable(false);
		field.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.GRAY),
			BorderFactory.createEmptyBorder(4, 4, 4, 4)
		));
		return field;
	}
	
	private class ButtonListener implements ActionListener {
        @Override 
        public void actionPerformed(ActionEvent e) {
        }
	}
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();

		KnownCardsGUI panel = new KnownCardsGUI();  // create the panel
		JFrame frame = new JFrame("Clue Controls");  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(250, 1000);;  // size the frame
		//frame.pack()
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}

}
