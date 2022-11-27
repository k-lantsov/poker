package com.company.poker.domain;

import java.util.List;

public class PokerHand {

    private List<Card> cards;

    public PokerHand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "PokerHand{" +
                "cards=" + cards +
                '}';
    }
}
