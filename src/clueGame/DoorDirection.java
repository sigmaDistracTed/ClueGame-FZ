package clueGame;

public enum DoorDirection {

    UP ("^"), DOWN ("v"), LEFT ("<"), RIGHT (">"), NONE ("");

    private String value;

    // Constructor
    DoorDirection(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
