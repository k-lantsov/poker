package com.company.poker.processor.game;

import com.company.poker.domain.PokerHand;

import java.util.List;

public interface GamePostProcessor {

    /**
     * The realizations of the method process poker hands in case when both player have got same combinations of cards.
     * It makes additional checking who is a winner of the round.
     * @return true - when player #1 is a winner, false - player #2 or draw was fixed
     */
    boolean postProcess(List<PokerHand> pokerHands);
}
