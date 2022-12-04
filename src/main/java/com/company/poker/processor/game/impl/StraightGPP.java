package com.company.poker.processor.game.impl;

import com.company.poker.domain.Card;
import com.company.poker.domain.PokerHand;
import com.company.poker.domain.Rank;
import com.company.poker.processor.game.GamePostProcessor;
import com.company.poker.util.HandUtil;

import java.util.List;

public class StraightGPP implements GamePostProcessor {
    @Override
    public boolean postProcess(List<PokerHand> pokerHands) {
        List<Card> sorted1 = HandUtil.sortedByCardsRank(pokerHands.get(0));
        List<Card> sorted2 = HandUtil.sortedByCardsRank(pokerHands.get(1));
        for (int i = 0; i < sorted1.size(); i++) {
            Rank rank1 = sorted1.get(i).getRank();
            Rank rank2 = sorted2.get(i).getRank();
            if (rank1.getStrength() == rank2.getStrength()) {
                continue;
            }
            return rank1.getStrength() > rank2.getStrength() && !Rank.ACE.equals(rank1);
        }
        return false;
    }
}
