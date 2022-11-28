package com.company.poker.processor.game.impl;

import com.company.poker.domain.PokerHand;
import com.company.poker.processor.game.GamePostProcessor;

public class RoyalFlushGPP implements GamePostProcessor {

    @Override
    public boolean postProcess(PokerHand pokerHand1, PokerHand pokerHand2) {
        return false;
    }
}
