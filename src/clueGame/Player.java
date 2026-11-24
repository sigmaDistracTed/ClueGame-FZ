package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.awt.Graphics;

public abstract class Player {
    private String name;
    private String color;
    private Set<Card> hand = new HashSet<Card>();
    private Set<Card> seen = new HashSet<Card>();
    
    private int row;
    private int col;
    private boolean turnFinished;

    // Constructor
    public Player(String name, String color, int row, int col) {
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
    }
    
    public Card disproveSuggestion(Card room, Card person, Card weapon) {
    	ArrayList<Card> candidates = new ArrayList<Card>();
    	
    	//Allowing for random return functionality
     	for(Card c : hand) {
     		if(c.equals(room)) {
     			candidates.add(c);
     		}
     		else if(c.equals(person)) {
     			candidates.add(c);
     		}
     		else if(c.equals(weapon)) {
     			candidates.add(c);
     		}
    	}
     	if(!candidates.isEmpty()) {
     		Collections.shuffle(candidates);
         	return candidates.get(0);
     	}
     	
    	return null;
    }
    
    // Draw player on board
    public void draw(Graphics g, int cellSize, int offsetX, int offsetY) {
        int x = offsetX + getCol() * cellSize;
        int y = offsetY + getRow() * cellSize;
        drawPiece(g, x, y, cellSize);
    }

    public void drawPiece(Graphics g, int x, int y, int cellSize) {
        g.setColor(getColor());
        g.fillOval(x, y, cellSize, cellSize);

        g.setColor(Color.BLACK);
        g.drawOval(x, y, cellSize - 1, cellSize - 1);
    }

    // Get player name
    public String getName() {
        return name;
    }

    // Set player name
    public void setName(String name) {
        this.name = name;
    }

    // Get player color
    public Color getColor() {
    	Color c = Color.decode(color);
        return c;
    }

    // Set player color
    public void setColor(String color) {
        this.color = color;
    }

    // Get player row
    public int getRow() {
        return row;
    }

    // Get player col
    public int getCol() {
        return col;
    }

    public void updateHand(Card card) {
    	this.hand.add(card);

    }
    
    public Set<Card> getHand(){
    	return this.hand;
    }
    
    public void updateSeen(Card card) {
    	this.seen.add(card);
    }
    
    public Set<Card> getSeen() { 
    	return seen;
    }
    
    @Override
    public String toString() { 
    	return(this.name + " " + this.color + " " + this.row + " " +this.col);
    			 
    }

	public void setRow(int row) { 
		this.row = row;
	}
	
	public void setCol(int col) { 
		this.col = col;
	}
	
	 public boolean getTurnFinished() {
	    	return turnFinished;
	    }
	    
	    public void setTurnFinished(boolean turnFinished) {
	    	this.turnFinished = turnFinished;
	    }
	

}
