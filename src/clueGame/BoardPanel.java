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
    private Board board;
    
    public BoardPanel(Board board) {
        this.board = board;
    }

    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
