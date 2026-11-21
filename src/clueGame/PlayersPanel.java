package clueGame;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class PlayersPanel extends JPanel {
	private static Board board;
    private final JTextField nameTF = new JTextField();
    private JLabel sprites;
	private ArrayList<Player> playerList = new ArrayList<Player>(); 
	private int idx;
	private String path;
	
	public PlayersPanel(String playerName) {
		board = Board.getInstance(); 
		//board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		//board.initialize(); //so we have the players
		
		
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
		revalidate();
	    repaint();
	}
	
}
