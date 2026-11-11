package clueGame;

import java.awt.BorderLayout;
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
import javax.swing.ImageIcon;

public class GameControlPanel extends JFrame {
    private GameControlPanel gui;
    private JTextField nameTF;

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
        gui = this;

        JPanel panel = new JPanel();

        JLabel nameLabel = new JLabel("Name:");
        nameTF = new JTextField(20);
        panel.add(nameLabel);
        panel.add(nameTF);
        add(panel, BorderLayout.CENTER);

        JButton nameButton = new JButton("OK");
        add(nameButton, BorderLayout.SOUTH);

        nameButton.addActionListener(new ButtonListener());

        // To load a sprite
        ImageIcon original = new ImageIcon("Sprites/Astrologer Azure.png");

        setTitle("Clue Control");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

    public class ButtonListener implements ActionListener {
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
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
        GameControlPanel gui = new GameControlPanel();
        gui.setVisible(true);
	}
}