package domain;

public enum Combo {
    ROYAL_FLUSH(1),
    STRAIGHT_FLUSH(2),
    FOUR_OF_A_KIND(3),
    FULL_HOUSE(4),
    FLUSH(5),
    STRAIGHT(6),
    THREE_OF_A_KIND(7),
    TWO_PAIRS(8),
    ONE_PAIR(9),
    NO_COMBO(10);

    private final int strength;

    Combo(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }
}
