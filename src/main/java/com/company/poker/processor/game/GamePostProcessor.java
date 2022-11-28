package com.company.poker.processor.game;

import com.company.poker.domain.PokerHand;

public interface GamePostProcessor {

    boolean postProcess(PokerHand pokerHand1, PokerHand pokerHand2);
}
