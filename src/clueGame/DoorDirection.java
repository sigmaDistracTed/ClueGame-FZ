package clueGame;

public enum DoorDirection {
<<<<<<< HEAD
    UP ("^"), DOWN ("v"), LEFT ("<"), RIGHT (">"), NONE ("");
=======
    UP("^"), DOWN("V"), LEFT("<"), RIGHT(">"), NONE("");

>>>>>>> 1520fe363f872a7daf2aca4b642e1ea8460161a7
    private String value;

    // Constructor
    DoorDirection(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
