package com.company.poker.processor;

import com.company.poker.domain.Card;
import com.company.poker.domain.Combo;
import com.company.poker.domain.PokerHand;
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

    public boolean process(PokerHand pokerHand1, PokerHand pokerHand2) {
        Combo combo1 = comboProcessor.process(pokerHand1);
        Combo combo2 = comboProcessor.process(pokerHand2);
        int strength1 = combo1.getStrength();
        int strength2 = combo2.getStrength();
        if (strength1 == strength2) {
            return postProcess(combo1, pokerHand1, pokerHand2);
        }
        return strength1 < strength2;
    }

    private boolean postProcess(Combo combo, PokerHand pokerHand1, PokerHand pokerHand2) {
        if (Combo.ROYAL_FLUSH.equals(combo))
            return false;
        if (Combo.STRAIGHT_FLUSH.equals(combo))
            return postProcessStraightFlush(pokerHand1, pokerHand2);
        if (Combo.FOUR_OF_A_KIND.equals(combo))
            return postProcessFourOfKindAndFullHouse(pokerHand1, pokerHand2);
        if (Combo.FULL_HOUSE.equals(combo))
            return postProcessFourOfKindAndFullHouse(pokerHand1, pokerHand2);
        if (Combo.FLUSH.equals(combo) || Combo.NO_COMBO.equals(combo))
            return postProcessFlushAndNoCombo(pokerHand1, pokerHand2);
        if (Combo.STRAIGHT.equals(combo))
            return postProcessStraight(pokerHand1, pokerHand2);
        if (Combo.THREE_OF_A_KIND.equals(combo) || Combo.TWO_PAIRS.equals(combo) || Combo.ONE_PAIR.equals(combo))
            return postProcessLowerCombos(pokerHand1, pokerHand2);
        return false;
    }


    private boolean postProcessStraightFlush(PokerHand pokerHand1, PokerHand pokerHand2) {
        List<Card> hand1CardsByRank = HandUtil.sortedByCardsRank(pokerHand1);
        List<Card> hand2CardsByRank = HandUtil.sortedByCardsRank(pokerHand2);
        return hand1CardsByRank.get(0).getRank().getStrength() > hand2CardsByRank.get(0).getRank().getStrength();
    }

    private boolean postProcessFourOfKindAndFullHouse(PokerHand pokerHand1, PokerHand pokerHand2) {
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

    private boolean postProcessFlushAndNoCombo(PokerHand pokerHand1, PokerHand pokerHand2) {
        List<Card> reversedHand1 = HandUtil.reverseSortedByCardsRank(pokerHand1);
        List<Card> reversedHand2 = HandUtil.reverseSortedByCardsRank(pokerHand2);
        for (int i = 0; i < reversedHand1.size(); i++) {
            Rank hand1CardRank = reversedHand1.get(i).getRank();
            Rank hand2CardRank = reversedHand2.get(i).getRank();
            if (hand1CardRank.equals(hand2CardRank))
                continue;
            return hand1CardRank.getStrength() > hand2CardRank.getStrength();
        }
        return false;
    }

    private boolean postProcessStraight(PokerHand pokerHand1, PokerHand pokerHand2) {
        List<Card> reversedHand1 = HandUtil.reverseSortedByCardsRank(pokerHand1);
        List<Card> reversedHand2 = HandUtil.reverseSortedByCardsRank(pokerHand2);
        int i = 0;
        while (true) {
            Rank hand1CardRank = reversedHand1.get(i).getRank();
            Rank hand2CardRank = reversedHand2.get(i).getRank();
            if (hand1CardRank.equals(hand2CardRank)
                    || Rank.ACE.equals(hand1CardRank)
                    || Rank.ACE.equals(hand2CardRank)) {
                i++;
                continue;
            }
            return hand1CardRank.getStrength() > hand2CardRank.getStrength();
        }
    }

    private boolean postProcessLowerCombos(PokerHand pokerHand1, PokerHand pokerHand2) {
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
