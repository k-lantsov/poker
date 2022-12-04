package com.company.poker.processor.game.impl;

import com.company.poker.domain.Card;
import com.company.poker.domain.PokerHand;
import com.company.poker.domain.Rank;
import com.company.poker.processor.game.GamePostProcessor;
import com.company.poker.util.HandUtil;

import java.util.List;

public class FlushAndNoComboGPP implements GamePostProcessor {
    @Override
    public boolean postProcess(List<PokerHand> pokerHands) {
        List<Card> reversedHand1 = HandUtil.reverseSortedByCardsRank(pokerHands.get(0));
        List<Card> reversedHand2 = HandUtil.reverseSortedByCardsRank(pokerHands.get(1));
        for (int i = 0; i < reversedHand1.size(); i++) {
            Rank hand1CardRank = reversedHand1.get(i).getRank();
            Rank hand2CardRank = reversedHand2.get(i).getRank();
            if (hand1CardRank.equals(hand2CardRank))
                continue;
            return hand1CardRank.getStrength() > hand2CardRank.getStrength();
        }
        return false;
    }
}
