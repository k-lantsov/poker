package com.company.poker.util;

import com.company.poker.domain.Card;
import com.company.poker.domain.PokerHand;
import com.company.poker.domain.Rank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandUtil {

    private HandUtil() {
    }

    public static List<Card> reverseSortedByCardsRank(PokerHand pokerHand) {
        Comparator<Card> comparator = (card1, card2) -> card2.getRank().compareTo(card1.getRank());
        return pokerHand.getCards().stream()
                .sorted(comparator)
                .toList();
    }

    public static List<Card> sortedByCardsRank(PokerHand pokerHand) {
        Comparator<Card> comparator = Comparator.comparing(Card::getRank);
        return pokerHand.getCards().stream()
                .sorted(comparator)
                .toList();
    }

    public static Map<Rank, Long> groupHandByCardRank(PokerHand pokerHand) {
        return pokerHand.getCards().stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
    }
}
