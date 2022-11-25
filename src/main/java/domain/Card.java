package domain;

import java.util.Objects;

public class Card {
    private Rang rang;
    private Suit suit;

    public Card(Rang rang, Suit suit) {
        this.rang = rang;
        this.suit = suit;
    }

    public Rang getRang() {
        return rang;
    }

    public void setRang(Rang rang) {
        this.rang = rang;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rang.equals(card.rang) && suit.equals(card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rang, suit);
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + rang +
                ", suit=" + suit +
                '}';
    }
}
