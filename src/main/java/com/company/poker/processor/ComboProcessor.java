package com.company.poker.processor;

import com.company.poker.domain.Card;
import com.company.poker.domain.Combo;
import com.company.poker.domain.PokerHand;
import com.company.poker.domain.Rank;
import com.company.poker.util.HandUtil;

import java.util.List;
import java.util.Map;

public class ComboProcessor {

    public Combo process(PokerHand pokerHand) {
        if (checkRoyalFlush(pokerHand))
            return Combo.ROYAL_FLUSH;
        if (checkStraightFlush(pokerHand))
            return Combo.STRAIGHT_FLUSH;
        if (checkFourOfKind(pokerHand))
            return Combo.FOUR_OF_A_KIND;
        if (checkFullHouse(pokerHand))
            return Combo.FULL_HOUSE;
        if (checkFlush(pokerHand))
            return Combo.FLUSH;
        if (checkStraight(pokerHand))
            return Combo.STRAIGHT;
        if (checkThreeOfKind(pokerHand))
            return Combo.THREE_OF_A_KIND;
        if (checkTwoPair(pokerHand))
            return Combo.TWO_PAIRS;
        if (checkPair(pokerHand))
            return Combo.ONE_PAIR;
        return Combo.NO_COMBO;
    }

    private boolean checkRoyalFlush(PokerHand pokerHand) {
        return checkFlush(pokerHand) &&
                checkStraight(pokerHand) &&
                Rank.TEN.equals(HandUtil.sortedByCardsRank(pokerHand).get(0).getRank());
    }

    private boolean checkStraightFlush(PokerHand pokerHand) {
        return checkFlush(pokerHand) &&
                checkStraight(pokerHand) &&
                !Rank.TEN.equals(HandUtil.reverseSortedByCardsRank(pokerHand).get(0).getRank());
    }

    private boolean checkFourOfKind(PokerHand pokerHand) {
        long count = pokerHand.getCards().stream().map(Card::getRank).distinct().count();
        return !checkThreeOfKind(pokerHand) && count == 2;
    }

    private boolean checkFullHouse(PokerHand pokerHand) {
        long count = pokerHand.getCards().stream().map(Card::getRank).distinct().count();
        return checkThreeOfKind(pokerHand) && count == 2;
    }

    private boolean checkFlush(PokerHand pokerHand) {
        long count = pokerHand.getCards().stream().map(Card::getSuit).distinct().count();
        return count == 1;
    }

    private boolean checkStraight(PokerHand pokerHand) {
        List<Card> cardsByRank = HandUtil.sortedByCardsRank(pokerHand);
        if (isCycleStraight(cardsByRank)) {
            return true;
        }
        for (int i = 1; i < cardsByRank.size(); i++) {
            if (cardsByRank.get(i).getRank().getStrength() - cardsByRank.get(i - 1).getRank().getStrength() == 1) {
                continue;
            }
            return false;
        }
        return true;
    }

    private boolean checkThreeOfKind(PokerHand pokerHand) {
        Map<Rank, Long> groupingByCardsRang = HandUtil.groupHandByCardRank(pokerHand);
        for (Map.Entry<Rank, Long> entry: groupingByCardsRang.entrySet()) {
            if (entry.getValue() == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTwoPair(PokerHand pokerHand) {
        long count = pokerHand.getCards().stream().map(Card::getRank).distinct().count();
        return !checkThreeOfKind(pokerHand) && count == 3;
    }

    private boolean checkPair(PokerHand pokerHand) {
        long count = pokerHand.getCards().stream().map(Card::getRank).distinct().count();
        return count == 4;
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
