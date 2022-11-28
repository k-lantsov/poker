package com.company.poker.processor;

import com.company.poker.domain.Card;
import com.company.poker.domain.Combo;
import com.company.poker.domain.PokerHand;
import com.company.poker.domain.Rank;
import com.company.poker.util.HandUtil;

import java.util.List;
import java.util.Map;

public class ComboProcessor {

    private Combo combo;

    public Combo process(PokerHand pokerHand) {
        checkRoyalFlush(pokerHand);
        return combo;
    }

    private void checkRoyalFlush(PokerHand pokerHand) {
        if (isFlush(pokerHand) &&
                isStraight(pokerHand) &&
                Rank.TEN.equals(HandUtil.sortedByCardsRank(pokerHand).get(0).getRank())) {
            combo = Combo.ROYAL_FLUSH;
            return;
        }
        checkStraightFlush(pokerHand);
    }

    private void checkStraightFlush(PokerHand pokerHand) {
        if (isFlush(pokerHand) &&
                isStraight(pokerHand) &&
                !Rank.TEN.equals(HandUtil.reverseSortedByCardsRank(pokerHand).get(0).getRank())) {
            combo = Combo.STRAIGHT_FLUSH;
            return;
        }
        checkFourOfKind(pokerHand);
    }

    private void checkFourOfKind(PokerHand pokerHand) {
        Map<Rank, Long> groupingByCardRank = HandUtil.groupHandByCardRank(pokerHand);
        if (groupingByCardRank.containsValue(4L)) {
            combo = Combo.FOUR_OF_A_KIND;
            return;
        }
        checkFullHouse(pokerHand);
    }

    private void checkFullHouse(PokerHand pokerHand) {
        Map<Rank, Long> groupingByCardRank = HandUtil.groupHandByCardRank(pokerHand);
        if (groupingByCardRank.containsValue(3L) && groupingByCardRank.size() == 2) {
            combo = Combo.FULL_HOUSE;
            return;
        }
        checkFlush(pokerHand);
    }

    private void checkFlush(PokerHand pokerHand) {
        if (isFlush(pokerHand)) {
            combo = Combo.FLUSH;
            return;
        }
        checkStraight(pokerHand);
    }

    private void checkStraight(PokerHand pokerHand) {
        if (isStraight(pokerHand)) {
            combo = Combo.STRAIGHT;
            return;
        }
        checkThreeOfKind(pokerHand);
    }

    private void checkThreeOfKind(PokerHand pokerHand) {
        if (isThreeOfKind(pokerHand)) {
            combo = Combo.THREE_OF_A_KIND;
            return;
        }
        checkTwoPair(pokerHand);
    }

    private void checkTwoPair(PokerHand pokerHand) {
        long count = pokerHand.getCards().stream().map(Card::getRank).distinct().count();
        if (!isThreeOfKind(pokerHand) && count == 3) {
            combo = Combo.TWO_PAIRS;
            return;
        }
        checkPair(pokerHand);
    }

    private void checkPair(PokerHand pokerHand) {
        long count = pokerHand.getCards().stream().map(Card::getRank).distinct().count();
        if (count == 4) {
            combo = Combo.ONE_PAIR;
            return;
        }
        combo = Combo.NO_COMBO;
    }

    private boolean isFlush(PokerHand pokerHand) {
        long count = pokerHand.getCards().stream().map(Card::getSuit).distinct().count();
        return count == 1;
    }

    private boolean isStraight(PokerHand pokerHand) {
        List<Card> cardsByRank = HandUtil.sortedByCardsRank(pokerHand);
        int size = cardsByRank.size();
        int lastCardStrength = cardsByRank.get(size - 1).getRank().getStrength();
        int firstCardStrength = cardsByRank.get(0).getRank().getStrength();
        return isCycleStraight(cardsByRank) || lastCardStrength - firstCardStrength == 4;
    }

    private boolean isThreeOfKind(PokerHand pokerHand) {
        Map<Rank, Long> groupingByCardsRank = HandUtil.groupHandByCardRank(pokerHand);
        return groupingByCardsRank.containsValue(3L) && groupingByCardsRank.size() > pokerHand.getCards().size() - 3;
    }

    private boolean isCycleStraight(List<Card> cardsByRank) {
        List<Rank> ranks = cardsByRank.stream().map(Card::getRank).toList();
        return ranks.contains(Rank.TWO) &&
                ranks.contains(Rank.THREE) &&
                ranks.contains(Rank.FOUR) &&
                ranks.contains(Rank.FIVE) &&
                ranks.contains(Rank.ACE);
    }
}
