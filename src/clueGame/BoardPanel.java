package clueGame;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class BoardPanel extends JPanel {
    private static Board board;

    public BoardPanel() {
		board = Board.getInstance();
        
        for(int r = 0; r < board.getGrid().length; r++) {
        	for(int c = 0; c < board.getGrid().length; c++) {
        		BoardCell toPaint = board.getCell(r, c); 
        		//we'd also want to set coords to r and c plus a certain value? 
        		//draw rectangle of whatever size we want 
        		if(toPaint.isRoom) {
        			//set color -> do we want different colors for different rooms?
        		}
        		else if(toPaint.isSecretPassage()) {
        			//paint and add label
        		}
        		
        		else if(toPaint.isDoorway()) { 
        			//paint and add label
        		}
        		
        		else if(toPaint.isLabel()) { 
        			//paint and add label
        			//in font test we have a list of fonts so we can choose a cool one
        			//however i refuse to use comic sans 
        		}
        		
        		else { //walkway
        			//paint and create outline
        		}
        	}
        	
        	
        }
    }

    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    public static void main(String args[]) {
    	JFrame frame = new JFrame(); 
    	BoardPanel bp = new BoardPanel();
    	frame.add(bp);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); 
    	
    }
}
