package clueGame;

public class Card {
    private String cardName;

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

}
