package com.company.poker.processor;

import com.company.poker.domain.Card;
import com.company.poker.domain.Combo;
import com.company.poker.domain.Hand;
import com.company.poker.domain.Rank;
import com.company.poker.util.HandUtil;

import java.util.List;
import java.util.Map;

public class ComboProcessor {

    public Combo process(Hand hand) {
        if (checkRoyalFlush(hand))
            return Combo.ROYAL_FLUSH;
        if (checkStraightFlush(hand))
            return Combo.STRAIGHT_FLUSH;
        if (checkFourOfKind(hand))
            return Combo.FOUR_OF_A_KIND;
        if (checkFullHouse(hand))
            return Combo.FULL_HOUSE;
        if (checkFlush(hand))
            return Combo.FLUSH;
        if (checkStraight(hand))
            return Combo.STRAIGHT;
        if (checkThreeOfKind(hand))
            return Combo.THREE_OF_A_KIND;
        if (checkTwoPair(hand))
            return Combo.TWO_PAIRS;
        if (checkPair(hand))
            return Combo.ONE_PAIR;
        return Combo.NO_COMBO;
    }

    private boolean checkRoyalFlush(Hand hand) {
        return checkFlush(hand) &&
                checkStraight(hand) &&
                HandUtil.reverseSortedHandByCardStrength(hand).get(0) == Rank.TEN.getStrength();
    }

    private boolean checkStraightFlush(Hand hand) {
        return checkFlush(hand) &&
                checkStraight(hand) &&
                HandUtil.reverseSortedHandByCardStrength(hand).get(0) != Rank.TEN.getStrength();
    }

    private boolean checkFourOfKind(Hand hand) {
        long count = hand.getCards().stream().map(Card::getRang).distinct().count();
        return !checkThreeOfKind(hand) && count == 2;
    }

    private boolean checkFullHouse(Hand hand) {
        long count = hand.getCards().stream().map(Card::getRang).distinct().count();
        return checkThreeOfKind(hand) && count == 2;
    }

    private boolean checkFlush(Hand hand) {
        long count = hand.getCards().stream().map(Card::getSuit).distinct().count();
        return count == 1;
    }

    private boolean checkStraight(Hand hand) {
        List<Integer> sortedCardsByStrength = HandUtil.sortedHandByCardStrength(hand);
        if (isCheckCycleStraight(sortedCardsByStrength)) {
            return true;
        }
        for (int i = 1; i < sortedCardsByStrength.size(); i++) {
            if (sortedCardsByStrength.get(i) - sortedCardsByStrength.get(i - 1) == 1) {
                continue;
            }
            return false;
        }
        return true;
    }

    private boolean checkThreeOfKind(Hand hand) {
        Map<Rank, Long> groupingByCardsRang = HandUtil.groupHandByCardRank(hand);
        for (Map.Entry<Rank, Long> entry: groupingByCardsRang.entrySet()) {
            if (entry.getValue() == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTwoPair(Hand hand) {
        long count = hand.getCards().stream().map(Card::getRang).distinct().count();
        return !checkThreeOfKind(hand) && count == 3;
    }

    private boolean checkPair(Hand hand) {
        long count = hand.getCards().stream().map(Card::getRang).distinct().count();
        return count == 4;
    }

    private boolean isCheckCycleStraight(List<Integer> sortedCardsByStrength) {
        return sortedCardsByStrength.contains(Rank.TWO.getStrength()) &&
                sortedCardsByStrength.contains(Rank.THREE.getStrength()) &&
                sortedCardsByStrength.contains(Rank.FOUR.getStrength()) &&
                sortedCardsByStrength.contains(Rank.FIVE.getStrength()) &&
                sortedCardsByStrength.contains(Rank.ACE.getStrength());
    }
}
