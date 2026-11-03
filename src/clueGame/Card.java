package clueGame;

public class Card {
    private String cardName;
    private CardType ct;

    // Constructor
    public Card(String cardName) {
        this.cardName = cardName;
    }

    // Get cardName
    public String getCardName() {
        return cardName;
    }

    // Set cardName
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    // Return true if the target equals this card
    public boolean equals(Card target) {
        if (target.getCardName() == cardName) {
            return true;
        } else {
            return false;
        }
    }
    
    //Set cardType
    public void setCardType(CardType ct) {
    	this.ct = ct;
    }
    
    //get cardType
    public CardType getCardType() {
    	return ct;
    }

}
