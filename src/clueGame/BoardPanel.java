package clueGame;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
    private final Board board = Board.getInstance();

    public BoardPanel() {
        addMouseListener(new CellListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = getCellSize();
        if (cellSize == 0) {
            return;
        }
        int offsetX = getBoardOffsetX(cellSize);
        int offsetY = getBoardOffsetY(cellSize);

        for (BoardCell[] row : board.getGrid()) {
            for (BoardCell cell : row) {
                cell.draw(g, cellSize, offsetX, offsetY);
            }
        }
        if (board.isWaitingForHumanMove()) {
            for (BoardCell target : board.getTargets()) {
                target.drawTargets(g, cellSize, offsetX, offsetY);
            }
        }
        for (BoardCell[] row : board.getGrid()) {
            for (BoardCell cell : row) {
                if (cell.isLabel()) {
                    cell.drawLabel(g, board.getRoom(cell).getName(), cellSize, offsetX, offsetY);
                }
                if (cell.isSecretPassage()) {
                    cell.drawLabel(g, String.valueOf(cell.getSecretPassage()), cellSize, offsetX, offsetY);
                }
            }
        }
        drawPlayers(g, cellSize, offsetX, offsetY);
    }

    private void drawPlayers(Graphics g, int cellSize, int offsetX, int offsetY) {
        Map<BoardCell, List<Player>> playersByCell = new LinkedHashMap<>();
        for (Player player : board.getPlayers()) {
            BoardCell cell = board.getCell(player.getRow(), player.getCol());
            playersByCell.computeIfAbsent(cell, key -> new ArrayList<>()).add(player);
        }
        for (Map.Entry<BoardCell, List<Player>> entry : playersByCell.entrySet()) {
            BoardCell cell = entry.getKey();
            List<Player> players = entry.getValue();
            int tilesPerSide = (int) Math.ceil(Math.sqrt(players.size()));
            int pieceSize = Math.max(1, cellSize / tilesPerSide);
            int x = offsetX + cell.getCell() * cellSize;
            int y = offsetY + cell.getRow() * cellSize;
            for (int i = 0; i < players.size(); i++) {
                players.get(i).drawPiece(g, x + (i % tilesPerSide) * pieceSize,
                        y + (i / tilesPerSide) * pieceSize, pieceSize);
            }
        }
    }

    private int getCellSize() {
        int rows = board.getNumRows();
        int cols = board.getNumColumns();
        return rows == 0 || cols == 0 ? 0 : Math.min(getWidth() / cols, getHeight() / rows);
    }

    private int getBoardOffsetX(int cellSize) {
        return (getWidth() - board.getNumColumns() * cellSize) / 2;
    }

    private int getBoardOffsetY(int cellSize) {
        return (getHeight() - board.getNumRows() * cellSize) / 2;
    }

    private BoardCell getClickedCell(MouseEvent event) {
        int cellSize = getCellSize();
        int x = event.getX() - getBoardOffsetX(cellSize);
        int y = event.getY() - getBoardOffsetY(cellSize);
        if (x < 0 || x >= board.getNumColumns() * cellSize
                || y < 0 || y >= board.getNumRows() * cellSize) {
            return null;
        }
        return board.getCell(y / cellSize, x / cellSize);
    }

    public class CellListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (!board.isWaitingForHumanMove()) {
                return;
            }
            if (!board.moveHumanPlayer(getClickedCell(event))) {
                JOptionPane.showMessageDialog(BoardPanel.this, "Please select a highlighted target.");
                return;
            }
            repaint();
        }
    }

    public static void main(String[] args) {
        ClueGame.main(args);
    }
}
