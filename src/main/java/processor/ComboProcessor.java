package processor;

import domain.Card;
import domain.Combo;
import domain.Hand;
import domain.Rang;
import util.HandUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                HandUtil.sortHandByCardStrength(hand).get(0) == Rang.TEN.getStrength();
    }

    private boolean checkStraightFlush(Hand hand) {
        return checkFlush(hand) &&
                checkStraight(hand) &&
                HandUtil.sortHandByCardStrength(hand).get(0) != Rang.TEN.getStrength();
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
        return count == 5;
    }

    private boolean checkStraight(Hand hand) {
        List<Integer> sortedCardsByStrength = HandUtil.sortHandByCardStrength(hand);
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
        Map<String, List<Card>> groupingByCardsRang = hand.getCards().stream()
                .collect(Collectors.groupingBy(card -> card.getRang().getValue()));
        for (Map.Entry<String, List<Card>> entry: groupingByCardsRang.entrySet()) {
            if (entry.getValue().size() == 3) {
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
        return sortedCardsByStrength.contains(Rang.TWO.getStrength()) &&
                sortedCardsByStrength.contains(Rang.THREE.getStrength()) &&
                sortedCardsByStrength.contains(Rang.FOUR.getStrength()) &&
                sortedCardsByStrength.contains(Rang.FIVE.getStrength()) &&
                sortedCardsByStrength.contains(Rang.ACE.getStrength());
    }
}
