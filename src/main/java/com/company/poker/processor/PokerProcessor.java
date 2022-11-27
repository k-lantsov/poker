package com.company.poker.processor;

import com.company.poker.domain.Combo;
import com.company.poker.domain.Hand;
import com.company.poker.domain.Rank;
import com.company.poker.util.HandUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PokerProcessor {

    private final ComboProcessor comboProcessor;

    public PokerProcessor(ComboProcessor comboProcessor) {
        this.comboProcessor = comboProcessor;
    }

    public boolean process(Hand hand1, Hand hand2) {
        Combo combo1 = comboProcessor.process(hand1);
        Combo combo2 = comboProcessor.process(hand2);
        int strength1 = combo1.getStrength();
        int strength2 = combo2.getStrength();
        if (strength1 < strength2)
            return true;
        if (strength1 > strength2)
            return false;
        return postProcess(combo1, hand1, hand2);
    }

    private boolean postProcess(Combo combo, Hand hand1, Hand hand2) {
        if (Combo.ROYAL_FLUSH.equals(combo))
            return false;
        if (Combo.STRAIGHT_FLUSH.equals(combo))
            return postProcessStraightFlush(hand1, hand2);
        if (Combo.FOUR_OF_A_KIND.equals(combo))
            return postProcessFourOfKind(hand1, hand2);
        if (Combo.FULL_HOUSE.equals(combo))
            return postProcessFullHouse(hand1, hand2);
        if (Combo.FLUSH.equals(combo))
            return postProcessFlush(hand1, hand2);
        if (Combo.STRAIGHT.equals(combo))
            return postProcessStraight(hand1, hand2);
        if (Combo.THREE_OF_A_KIND.equals(combo) || Combo.TWO_PAIRS.equals(combo) || Combo.ONE_PAIR.equals(combo))
            return postProcessLowerCombos(hand1, hand2);
        if (Combo.NO_COMBO.equals(combo))
            return postProcessNoCombo(hand1, hand2);
        return false;
    }


    private boolean postProcessStraightFlush(Hand hand1, Hand hand2) {
        List<Integer> hand1CardsStrengthList = HandUtil.reverseSortedHandByCardStrength(hand1);
        List<Integer> hand2CardsStrengthList = HandUtil.reverseSortedHandByCardStrength(hand2);
        return hand1CardsStrengthList.get(0) > hand2CardsStrengthList.get(0);
    }

    private boolean postProcessFourOfKind(Hand hand1, Hand hand2) {
        Map<Rank, Long> hand1Collect = HandUtil.groupHandByCardRank(hand1);
        Map<Rank, Long> hand2Collect = HandUtil.groupHandByCardRank(hand2);
        List<Rank> hand1Range = hand1Collect.entrySet().stream()
                .filter(entry -> entry.getValue() == 4)
                .map(Map.Entry::getKey)
                .toList();
        List<Rank> hand2Range = hand2Collect.entrySet().stream()
                .filter(entry -> entry.getValue() == 4)
                .map(Map.Entry::getKey)
                .toList();
        return hand1Range.get(0).getStrength() > hand2Range.get(0).getStrength();
    }

    private boolean postProcessFullHouse(Hand hand1, Hand hand2) {
        Map<Rank, Long> hand1Collect = HandUtil.groupHandByCardRank(hand1);
        Map<Rank, Long> hand2Collect = HandUtil.groupHandByCardRank(hand2);
        List<Rank> hand1Range = hand1Collect.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .toList();
        List<Rank> hand2Range = hand2Collect.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .toList();
        return hand1Range.get(0).getStrength() > hand2Range.get(0).getStrength() ||
                hand1Range.get(0).getStrength() == hand2Range.get(0).getStrength() && hand1Range.get(1).getStrength() > hand2Range.get(1).getStrength();
    }

    private boolean postProcessFlush(Hand hand1, Hand hand2) {
        List<Integer> hand1CardsStrengthList = HandUtil.reverseSortedHandByCardStrength(hand1);
        List<Integer> hand2CardsStrengthList = HandUtil.reverseSortedHandByCardStrength(hand2);
        for (int i = 0; i < hand1CardsStrengthList.size(); i++) {
            int hand1CardStrength = hand1CardsStrengthList.get(i);
            int hand2CardStrength = hand2CardsStrengthList.get(i);
            if (hand1CardStrength == hand2CardStrength)
                continue;
            return hand1CardStrength > hand2CardStrength;
        }
        return false;
    }

    private boolean postProcessStraight(Hand hand1, Hand hand2) {
        List<Integer> hand1CardsStrengthList = HandUtil.reverseSortedHandByCardStrength(hand1);
        List<Integer> hand2CardsStrengthList = HandUtil.reverseSortedHandByCardStrength(hand2);
        return hand1CardsStrengthList.get(0) > hand2CardsStrengthList.get(0) &&
                !hand1CardsStrengthList.contains(Rank.ACE.getStrength());
    }

    private boolean postProcessLowerCombos(Hand hand1, Hand hand2) {
        Map<Rank, Long> hand1Collect = HandUtil.groupHandByCardRank(hand1);
        Map<Rank, Long> hand2Collect = HandUtil.groupHandByCardRank(hand2);
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

    private boolean postProcessNoCombo(Hand hand1, Hand hand2) {
        return postProcessFlush(hand1, hand2);
    }
}
