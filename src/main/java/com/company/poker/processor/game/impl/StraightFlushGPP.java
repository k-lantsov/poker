package com.company.poker.processor.game.impl;

import com.company.poker.domain.Card;
import com.company.poker.domain.PokerHand;
import com.company.poker.processor.game.GamePostProcessor;
import com.company.poker.util.HandUtil;

import java.util.List;

public class StraightFlushGPP implements GamePostProcessor {
    @Override
    public boolean postProcess(PokerHand pokerHand1, PokerHand pokerHand2) {
        List<Card> hand1CardsByRank = HandUtil.sortedByCardsRank(pokerHand1);
        List<Card> hand2CardsByRank = HandUtil.sortedByCardsRank(pokerHand2);
        return hand1CardsByRank.get(0).getRank().getStrength() > hand2CardsByRank.get(0).getRank().getStrength();
    }
}
