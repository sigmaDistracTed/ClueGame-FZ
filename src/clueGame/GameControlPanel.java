package clueGame;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel {
    private final Board board = Board.getInstance();
    private final PlayersPanel players;
    private final JTextField nameTF = new JTextField(12);
    private final JTextField rollTF = new JTextField(2);
    private final JLabel moveStatus = new JLabel();
    private final JTextField guessField = new JTextField("No Guess!");
    private final JTextField guessResultField = new JTextField("So you have nothing?");
    private final JButton next = new JButton("Next Turn");

    public GameControlPanel(PlayersPanel players) {
        this.players = players;
        setLayout(new GridLayout(2, 1));

        JPanel turns = new JPanel();
        turns.add(new JLabel("Who's turn is it?"));
        nameTF.setEditable(false);
        turns.add(nameTF);

        JPanel rollPanel = new JPanel();
        rollPanel.add(new JLabel("Roll"));
        rollTF.setEditable(false);
        rollPanel.add(rollTF);
        rollPanel.add(moveStatus);

        JButton makeAccusation = new JButton("Make Accusation");
        makeAccusation.setEnabled(false);
        next.setEnabled(false);
        next.addActionListener(e -> {
            if (!board.advanceTurn()) {
                JOptionPane.showMessageDialog(this, "Please finish your turn.");
                return;
            }
            showCurrentTurn();
        });

        JPanel topHalf = new JPanel(new GridLayout(1, 4));
        topHalf.add(turns);
        topHalf.add(rollPanel);
        topHalf.add(makeAccusation);
        topHalf.add(next);
        add(topHalf);

        JPanel guessPanel = new JPanel(new GridLayout(1, 1));
        guessPanel.setBorder(BorderFactory.createTitledBorder("Guess"));
        guessPanel.setName("Guess");
        guessPanel.add(guessField);

        JPanel guessResultPanel = new JPanel(new GridLayout(1, 1));
        guessResultPanel.setBorder(BorderFactory.createTitledBorder("Guess Result"));
        guessResultPanel.setName("Guess Result");
        guessResultPanel.add(guessResultField);

        JPanel bottomHalf = new JPanel(new GridLayout(1, 2));
        bottomHalf.add(guessPanel);
        bottomHalf.add(guessResultPanel);
        add(bottomHalf);
    }

    void startGame() {
        board.startTurns();
        next.setEnabled(true);
        showCurrentTurn();
    }

    private void showCurrentTurn() {
        Player player = board.getCurrentPlayer();
        Color color = player.getColor();
        
        nameTF.setText(player.getName());
        nameTF.setBackground(color);
        nameTF.setForeground(Color.BLACK.equals(color) ? Color.WHITE : Color.BLACK);
        rollTF.setText(String.valueOf(board.getCurrentRoll()));

        if (board.isMoveBlocked()) {
            moveStatus.setText("No legal moves");
        } else {
            moveStatus.setText("");
        }

        if (players != null) {
            players.UpdatePanel(player.getName());
        }
        if (getParent() != null) {
            // The board and controls share the game's content pane.
            getParent().repaint();
        }
    }

    public String getPlayerName() {
        return nameTF.getText();
    }

    public void setGuessField(String p, String r, String w) {
        guessField.setText("It was " + p + " in the " + r + " with the " + w + "!");
    }

    public void setGuessField() {
        guessField.setText("No guess");
    }

    public void setGuessResult(String card) {
        guessResultField.setText(card);
    }

    public static void main(String[] args) {
        ClueGame.main(args);
    }
}
