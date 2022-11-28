package com.company.poker.processor.game.impl;

import com.company.poker.domain.PokerHand;
import com.company.poker.domain.Rank;
import com.company.poker.processor.game.GamePostProcessor;
import com.company.poker.util.HandUtil;

import java.util.List;
import java.util.Map;

public class FourOfKindAndFullHouseGPP implements GamePostProcessor {
    @Override
    public boolean postProcess(PokerHand pokerHand1, PokerHand pokerHand2) {
        Map<Rank, Long> hand2Collect = HandUtil.groupHandByCardRank(pokerHand2);
        Map<Rank, Long> hand1Collect = HandUtil.groupHandByCardRank(pokerHand1);
        List<Rank> hand1Range = hand1Collect.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .toList();
        List<Rank> hand2Range = hand2Collect.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .toList();
        for (int i = 0; i < hand1Range.size(); i++) {
            if (hand1Range.get(i) == hand2Range.get(i))
                continue;
            return hand1Range.get(i).getStrength() > hand2Range.get(i).getStrength();
        }
        return false;
    }
}
