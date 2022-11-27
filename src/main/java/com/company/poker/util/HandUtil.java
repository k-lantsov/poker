package com.company.poker.util;

import com.company.poker.domain.Card;
import com.company.poker.domain.Hand;
import com.company.poker.domain.Rank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandUtil {

    private HandUtil() {
    }

    public static List<Integer> reverseSortedHandByCardStrength(Hand hand) {
        return hand.getCards().stream().map(card -> card.getRang().getStrength()).sorted(Comparator.reverseOrder()).toList();
    }

    public static List<Integer> sortedHandByCardStrength(Hand hand) {
        return hand.getCards().stream().map(card -> card.getRang().getStrength()).sorted().toList();
    }

    public static Map<Rank, Long> groupHandByCardRank(Hand hand) {
        return hand.getCards().stream()
                .collect(Collectors.groupingBy(Card::getRang, Collectors.counting()));
    }
}
