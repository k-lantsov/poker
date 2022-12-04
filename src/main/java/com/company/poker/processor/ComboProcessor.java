package com.company.poker.processor;

import com.company.poker.domain.Card;
import com.company.poker.domain.Combo;
import com.company.poker.domain.PokerHand;
import com.company.poker.domain.Rank;
import com.company.poker.processor.game.context.ComboContext;
import com.company.poker.util.HandUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ComboProcessor {

    private ComboContext comboContext;

    public ComboProcessor(ComboContext comboContext) {
        this.comboContext = comboContext;
    }

    public ComboContext getComboContext() {
        return comboContext;
    }

    public void setComboContext(ComboContext comboContext) {
        this.comboContext = comboContext;
    }

    /**
     * The method accepts poker hand consisted of 5 cards and determines combination of the cards
     */
    public Combo process(PokerHand pokerHand) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = this.getClass().getDeclaredMethods();
        List<Method> annotatedMethods = Stream.of(methods)
                .filter(m -> m.isAnnotationPresent(CheckCombo.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(CheckCombo.class).order()))
                .toList();
        for (Method method : annotatedMethods) {
            method.invoke(this, pokerHand);
            if (comboContext.getCombo() != null) {
                return comboContext.getCombo();
            }
        }
        return comboContext.getCombo();
    }

    @CheckCombo(order = 10)
    private void checkRoyalFlush(PokerHand pokerHand) {
        if (isFlush(pokerHand) &&
                isStraight(pokerHand) &&
                Rank.TEN.equals(HandUtil.sortedByCardsRank(pokerHand).get(0).getRank())) {
            comboContext.setCombo(Combo.ROYAL_FLUSH);
        }
    }

    @CheckCombo(order = 9)
    private void checkStraightFlush(PokerHand pokerHand) {
        if (isFlush(pokerHand) &&
                isStraight(pokerHand) &&
                !Rank.TEN.equals(HandUtil.sortedByCardsRank(pokerHand).get(0).getRank())) {
            comboContext.setCombo(Combo.STRAIGHT_FLUSH);
        }
    }

    @CheckCombo(order = 8)
    private void checkFourOfKind(PokerHand pokerHand) {
        Map<Rank, Long> groupingByCardRank = HandUtil.groupHandByCardRank(pokerHand);
        if (groupingByCardRank.containsValue(4L)) {
            comboContext.setCombo(Combo.FOUR_OF_A_KIND);
        }
    }

    @CheckCombo(order = 7)
    private void checkFullHouse(PokerHand pokerHand) {
        Map<Rank, Long> groupingByCardRank = HandUtil.groupHandByCardRank(pokerHand);
        if (groupingByCardRank.containsValue(3L) && groupingByCardRank.size() == 2) {
            comboContext.setCombo(Combo.FULL_HOUSE);
        }
    }

    @CheckCombo(order = 6)
    private void checkFlush(PokerHand pokerHand) {
        if (isFlush(pokerHand) && !isStraight(pokerHand)) {
            comboContext.setCombo(Combo.FLUSH);
        }
    }

    @CheckCombo(order = 5)
    private void checkStraight(PokerHand pokerHand) {
        if (!isFlush(pokerHand) && isStraight(pokerHand)) {
            comboContext.setCombo(Combo.STRAIGHT);
        }
    }

    @CheckCombo(order = 4)
    private void checkThreeOfKind(PokerHand pokerHand) {
        if (isThreeOfKind(pokerHand)) {
            comboContext.setCombo(Combo.THREE_OF_A_KIND);
        }
    }

    @CheckCombo(order = 3)
    private void checkTwoPair(PokerHand pokerHand) {
        long count = HandUtil.countDistinctCardsByRank(pokerHand);
        if (!isThreeOfKind(pokerHand) && count == 3) {
            comboContext.setCombo(Combo.TWO_PAIRS);
        }
    }

    @CheckCombo(order = 2)
    private void checkPair(PokerHand pokerHand) {
        long count = HandUtil.countDistinctCardsByRank(pokerHand);
        if (count == 4) {
            comboContext.setCombo(Combo.ONE_PAIR);
        }
    }

    @CheckCombo(order = 1)
    private void checkNoCombo(PokerHand pokerHand) {
        long countDistinctCardsByRank = HandUtil.countDistinctCardsByRank(pokerHand);
        long countDistinctCardsBySuit = HandUtil.countDistinctCardsBySuit(pokerHand);
        if (countDistinctCardsByRank == 5 && countDistinctCardsBySuit > 1 && !isStraight(pokerHand)) {
            comboContext.setCombo(Combo.NO_COMBO);
        }
    }

    private boolean isFlush(PokerHand pokerHand) {
        long count = HandUtil.countDistinctCardsBySuit(pokerHand);
        return count == 1;
    }

    private boolean isStraight(PokerHand pokerHand) {
        List<Card> cardsByRank = HandUtil.sortedByCardsRank(pokerHand);
        if (isCycle(cardsByRank)) {
            return true;
        }
        for (int i = 1; i < cardsByRank.size(); i++) {
            Card current = cardsByRank.get(i);
            Card previous = cardsByRank.get(i - 1);
            if (current.getRank().getStrength() - previous.getRank().getStrength() == 1) {
                continue;
            }
            return false;
        }
        return true;
    }

    private boolean isThreeOfKind(PokerHand pokerHand) {
        Map<Rank, Long> groupingByCardsRank = HandUtil.groupHandByCardRank(pokerHand);
        return groupingByCardsRank.containsValue(3L) && !groupingByCardsRank.containsValue(2L);
    }

    private boolean isCycle(List<Card> cardsByRank) {
        List<Rank> ranks = cardsByRank.stream().map(Card::getRank).toList();
        return ranks.contains(Rank.TWO) &&
                ranks.contains(Rank.THREE) &&
                ranks.contains(Rank.FOUR) &&
                ranks.contains(Rank.FIVE) &&
                ranks.contains(Rank.ACE);
    }
}
