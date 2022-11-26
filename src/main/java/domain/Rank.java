package domain;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Rank {
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("T", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
    ACE("A", 14);

    private final String value;
    private final int strength;

    Rank(String value, int strength) {
        this.value = value;
        this.strength = strength;
    }

    public String getValue() {
        return value;
    }

    public int getStrength() {
        return strength;
    }
}
