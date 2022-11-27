package com.company.poker.processor;

import com.company.poker.domain.Card;
import com.company.poker.domain.PokerHand;
import com.company.poker.domain.Rank;
import com.company.poker.domain.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class HandProducer {

    public List<PokerHand> produce(String line) {
        PokerHand pokerHand1 = new PokerHand(new ArrayList<>());
        PokerHand pokerHand2 = new PokerHand(new ArrayList<>());
        String[] split = line.split(" ");
        for (int i = 0; i < split.length; i++) {
            char[] chars = split[i].toCharArray();
            String rankValue = String.valueOf(chars[0]);
            String suitValue = String.valueOf(chars[1]);
            Rank rank = Stream.of(Rank.values())
                    .filter(r -> r.getValue().equals(String.valueOf(chars[0])))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("There isn't rank value " + rankValue));
            Suit suit = Stream.of(Suit.values())
                    .filter(s -> s.getValue().equals(suitValue))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("There isn't suit value " + suitValue));
            Card card = new Card(rank, suit);
            if (i < 5) {
                pokerHand1.getCards().add(card);
                continue;
            }
            pokerHand2.getCards().add(card);
        }
        return List.of(pokerHand1, pokerHand2);
    }
}
