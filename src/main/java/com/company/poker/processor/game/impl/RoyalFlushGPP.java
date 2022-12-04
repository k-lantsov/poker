package com.company.poker.processor.game.impl;

import com.company.poker.domain.PokerHand;
import com.company.poker.processor.game.GamePostProcessor;

import java.util.List;

public class RoyalFlushGPP implements GamePostProcessor {

    @Override
    public boolean postProcess(List<PokerHand> pokerHands) {
        return false;
    }
}
