package clueGame;

import java.awt.Graphics;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
    private final Board board;

    public BoardPanel() {
        board = Board.getInstance();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int rows = board.getNumRows();
        int cols = board.getNumColumns();
        if (rows == 0 || cols == 0) {
            return;
        }

        int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
        if (cellSize == 0) {
            return;
        }

        int offsetX = (getWidth() - cols * cellSize) / 2;
        int offsetY = (getHeight() - rows * cellSize) / 2;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board.getCell(row, col).draw(g, cellSize, offsetX, offsetY);
            }
        }
    }

    public static void main(String[] args) {
        ClueGame.main(args);
    }
}
