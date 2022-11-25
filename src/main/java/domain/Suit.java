package domain;

public enum Suit {
    DIAMONDS("D"),
    HEARTS("H"),
    CLUBS("C"),
    SPADES("S");

    private final String value;

    Suit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
