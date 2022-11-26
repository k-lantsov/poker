package processor;

import domain.Card;
import domain.Hand;
import domain.Rang;
import domain.Suit;

import java.util.ArrayList;
import java.util.List;

public class HandsGenerator {

    public List<Hand> generate(String line) {
        Hand hand1 = new Hand(new ArrayList<>());
        Hand hand2 = new Hand(new ArrayList<>());
        String[] split = line.split(" ");
        for (int i = 0; i < split.length; i++) {
            char[] chars = split[i].toCharArray();
            Card card = new Card(Rang.valueOf(String.valueOf(chars[0])), Suit.valueOf(String.valueOf(chars[1])));
            if (i < 5) {
                hand1.getCards().add(card);
                continue;
            }
            hand2.getCards().add(card);
        }
        return List.of(hand1, hand2);
    }
}
