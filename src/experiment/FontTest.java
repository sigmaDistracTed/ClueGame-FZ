package experiment;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FontTest extends JPanel {
	
	public static void main( String[] args )
    {
        var fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (var font: fonts) {
            var label = new JLabel(font.getName());
            var derivedFont = font.deriveFont(15.0f);    // yes, a float!!!
            label.setFont(derivedFont);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.add(label);
        }
        var sp = new JScrollPane(panel);
        sp.setPreferredSize(new Dimension(400, 500));
        var f = new JFrame("Fonts");
        f.setContentPane(sp);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
