package com.company.poker.processor.game.impl;

import com.company.poker.domain.Card;
import com.company.poker.domain.PokerHand;
import com.company.poker.processor.game.GamePostProcessor;
import com.company.poker.util.HandUtil;

import java.util.List;

public class StraightFlushGPP implements GamePostProcessor {
    @Override
    public boolean postProcess(List<PokerHand> pokerHands) {
        List<Card> hand1CardsByRank = HandUtil.sortedByCardsRank(pokerHands.get(0));
        List<Card> hand2CardsByRank = HandUtil.sortedByCardsRank(pokerHands.get(1));
        return hand1CardsByRank.get(0).getRank().getStrength() > hand2CardsByRank.get(0).getRank().getStrength();
    }
}
