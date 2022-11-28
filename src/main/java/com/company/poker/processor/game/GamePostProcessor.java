package com.company.poker.processor.game;

import com.company.poker.domain.PokerHand;

public interface GamePostProcessor {

    /**
     * The realizations of the method process poker hands in case when both player have got same combinations of cards.
     * It make additional checking who is a winner of the round.
     * @param pokerHand1
     * @param pokerHand2
     * @return true - when player #1 is a winner, false - player #2 or draw was fixed
     */
    boolean postProcess(PokerHand pokerHand1, PokerHand pokerHand2);
}
