package clueGame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
    private final Board board;

    public BoardPanel() {
        board = Board.getInstance();
        addMouseListener(new CellListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int rows = board.getNumRows();
        int cols = board.getNumColumns();
        if (rows == 0 || cols == 0) {
            return;
        }

		// Determine cell size
        int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
        if (cellSize == 0) {
            return;
        }

		// Determine offset
        int offsetX = (getWidth() - cols * cellSize) / 2;
        int offsetY = (getHeight() - rows * cellSize) / 2;

		// Drawing the board
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board.getCell(row, col).draw(g, cellSize, offsetX, offsetY);
                
            }
        }

		// For writing room labels
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				BoardCell thisCell = board.getCell(row, col);
				if (thisCell.isLabel()) {
					String name = board.getRoom(thisCell).getName();
					thisCell.drawLabel(g, name, cellSize, offsetX, offsetY);
				}
			}
		}
		
		//secret passage
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				BoardCell thisCell = board.getCell(row, col);
				if (thisCell.isSecretPassage()) {
					char name = thisCell.getSecretPassage();
					thisCell.drawLabel(g, String.valueOf(name), cellSize, offsetX, offsetY);
				}
			}
		}

		
		//paint targets
		for (BoardCell t : board.getTargets()) {
	        t.drawTargets(g, cellSize,  offsetX, offsetY);
	    }
		
		// Draw players
				for (Player player : board.getPlayers()) {
					player.draw(g, cellSize, offsetX, offsetY);
				}
    }
    
    public class CellListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int rows = board.getNumRows();
	        int cols = board.getNumColumns();
	        int cellSize = Math.min(getWidth() / cols, getHeight() / rows);
			int offsetX = (getWidth() - cols * cellSize) / 2;
	        int offsetY = (getHeight() - rows * cellSize) / 2;
	        
			int x = e.getX();
			int y = e.getY();
			
			int col = (x - offsetX) / cellSize;
			int row = (y - offsetY) / cellSize;
			if (board.getTargets().contains(board.getCell(row, col))) {
		        Player player = board.getHumanPlayer();
		     
		        player.setRow(row);
		        player.setCol(col);

		        repaint();
		        player.setTurnFinished(true);
		    	}
			}
			
		

		public void mousePressed(MouseEvent e) {}

		
		public void mouseReleased(MouseEvent e) {}

		
		public void mouseEntered(MouseEvent e) {}

		
		public void mouseExited(MouseEvent e) {} 
    	
    }


    public static void main(String[] args) {
        ClueGame.main(args);
    }
}
