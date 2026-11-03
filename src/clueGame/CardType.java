package clueGame;

public enum CardType {

    ROOM ("ROOM"), PERSON ("PERSON"), WEAPON ("WEAPON");

    private String value;

    // Constructor
    CardType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
