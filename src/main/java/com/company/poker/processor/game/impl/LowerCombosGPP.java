package com.company.poker.processor.game.impl;

import com.company.poker.domain.PokerHand;
import com.company.poker.domain.Rank;
import com.company.poker.processor.game.GamePostProcessor;
import com.company.poker.util.HandUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class LowerCombosGPP implements GamePostProcessor {
    @Override
    public boolean postProcess(PokerHand pokerHand1, PokerHand pokerHand2) {
        Map<Rank, Long> hand1Collect = HandUtil.groupHandByCardRank(pokerHand1);
        Map<Rank, Long> hand2Collect = HandUtil.groupHandByCardRank(pokerHand2);
        Comparator<Map.Entry<Rank, Long>> byCardCountComparator = (e1, e2) -> e2.getValue().compareTo(e1.getValue());
        Comparator<Map.Entry<Rank, Long>> byCardRankComparator = (e1, e2) -> e2.getKey().compareTo(e1.getKey());
        List<Rank> hand1Range = hand1Collect.entrySet().stream()
                .sorted(byCardCountComparator.thenComparing(byCardRankComparator))
                .map(Map.Entry::getKey)
                .toList();
        List<Rank> hand2Range = hand2Collect.entrySet().stream()
                .sorted(byCardCountComparator.thenComparing(byCardRankComparator))
                .map(Map.Entry::getKey)
                .toList();
        for (int i = 0; i < hand1Range.size(); i++) {
            int hand1CardStrength = hand1Range.get(i).getStrength();
            int hand2CardStrength = hand2Range.get(i).getStrength();
            if (hand1CardStrength == hand2CardStrength)
                continue;
            return hand1CardStrength > hand2CardStrength;
        }
        return false;
    }
}
