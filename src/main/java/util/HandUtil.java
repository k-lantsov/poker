package util;

import domain.Hand;

import java.util.List;

public class HandUtil {

    private HandUtil() {
    }

    public static List<Integer> sortHandByCardStrength(Hand hand) {
        return hand.getCards().stream().map(card -> card.getRang().getStrength()).sorted().toList();
    }
}
