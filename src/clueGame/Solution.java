package clueGame;

public class Solution {
    private Card room;
    private Card person;
    private Card weapon;
    private static int solSize = 0;
    
    {solSize += 3;} //increases sol size every time its called by the three cards
    
    public Solution(Card room, Card person, Card weapon) { 
        this.room = room;
        this.person = person;
        this.weapon = weapon;
    }

    public Card getRoom() {
        return room;
    }

    public Card getPerson() {
        return person;
    }

    public Card getWeapon() {
        return weapon;
    }
    
    public int getSolSize() {
    	return solSize;
    }
        
    @Override 
    public String toString() {
        return(this.room + " " + this.person + " " + this.weapon);
    }
}
