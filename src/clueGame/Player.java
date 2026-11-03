package clueGame;

import java.awt.Color;

public abstract class Player {
    private String name;
    private String color;
    
    private int row;
    private int col;

    // Constructor
    public Player(String name, String color, int row, int col) {
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
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
    public String getColor() {
        return color;
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

    }

}
