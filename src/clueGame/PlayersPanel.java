package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;

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
import javax.swing.border.TitledBorder;

public class PlayersPanel extends JPanel {
	private static Board board;
    private final JTextField nameTF = new JTextField();
    private JLabel sprites;
	private ArrayList<Player> playerList = new ArrayList<Player>(); 
	private int idx;
	private String path;
	
	public PlayersPanel(String playerName) {
		board = Board.getInstance(); 
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize(); //so we have the players
		
		
		nameTF.setText(playerName);
		
		//sprites
		sprites = new JLabel();
		path = "../ClueGame/Sprites/";
		sprites.setIcon(new ImageIcon(new ImageIcon(path+playerName+".png").getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH)));
		add(sprites);
		//adding to panel
	}
	
	public void UpdatePanel(String playerName) {
		sprites.setIcon(new ImageIcon(new ImageIcon(path+playerName+".png").getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH)));
		add(sprites);
	}
	
}
