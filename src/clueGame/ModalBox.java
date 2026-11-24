package clueGame;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ModalBox extends JDialog {
	/* if suggestion: 
	 * room = get room 
	 * person and weapon are whatever
	 * 
	 * if accusation: 
	 * everything is whatever
	 */
	
	private JDialog box; 
	private static Board board = Board.getInstance();
	
	public ModalBox(boolean isSuggestion, String roomName) { 
		box = new JDialog();
		
		int size = board.getCards().size(); 
		
		String[] rooms = new String[size];
		String[] weapons = new String[size];
		String[] people = new String[size];
		int ridx = 0;
		int widx = 0;
		int pidx = 0;
		
		for (Card c : board.getCards()) {
			switch(c.getCardType()) {
				case WEAPON:
					weapons[widx] = c.getCardName();
					widx++;
					break;
				case PERSON:
					people[pidx] = c.getCardName();
					pidx++;
					break;
				case ROOM:
					rooms[ridx] = c.getCardName();
					widx++;
					break;
				default:
					break;
			}
			
			
			
		}
		
		if(isSuggestion) {
			String message = JOptionPane.showInputDialog("Make your suggestion!");
			JTextField room = new JTextField(roomName);
			room.setEditable(false);
			JOptionPane person = new JOptionPane(people);
			JOptionPane weapon = new JOptionPane(weapons);
			
			add(room);
			add(person);
			add(weapon);
	
		}
		
		else { //accusation 
			String message = JOptionPane.showInputDialog("Make your accusation!");
			JComboBox<String> room = new JComboBox<>(rooms);
			JComboBox<String> person = new JComboBox<>(people);
			JComboBox<String> weapon = new JComboBox<>(weapons);
			
			add(room);
			add(person);
			add(weapon);
			
		}
		
		
		
	}
	
	
	public static void main(String[] args) {
			
			ModalBox md = new ModalBox(false, "Alchemy Lab");  // create the panel
		
			JFrame frame = new JFrame("modal box"); 
			frame.add(md, BorderLayout.WEST);// create the frame 
			
			//dont set content pane
			frame.setSize(950, 180);  // size the frame
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
			frame.setVisible(true); 
	}
	
	
}


