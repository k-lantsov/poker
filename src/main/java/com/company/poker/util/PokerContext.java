package com.company.poker.util;

import com.company.poker.domain.Combo;
import com.company.poker.processor.game.GamePostProcessor;
import com.company.poker.processor.game.impl.*;

import java.util.HashMap;
import java.util.Map;

public class PokerContext {

    private PokerContext() {
    }

    public static Map<Combo, GamePostProcessor> getPokerContextMap() {
        Map<Combo, GamePostProcessor> pokerContextMap = new HashMap<>();
        pokerContextMap.put(Combo.ROYAL_FLUSH, new RoyalFlushGPP());
        pokerContextMap.put(Combo.STRAIGHT_FLUSH, new StraightFlushGPP());
        pokerContextMap.put(Combo.FOUR_OF_A_KIND, new FourOfKindAndFullHouseGPP());
        pokerContextMap.put(Combo.FULL_HOUSE, new FourOfKindAndFullHouseGPP());
        pokerContextMap.put(Combo.FLUSH, new FlushAndNoComboGPP());
        pokerContextMap.put(Combo.STRAIGHT, new StraightGPP());
        pokerContextMap.put(Combo.THREE_OF_A_KIND, new LowerCombosGPP());
        pokerContextMap.put(Combo.TWO_PAIRS, new LowerCombosGPP());
        pokerContextMap.put(Combo.ONE_PAIR, new LowerCombosGPP());
        pokerContextMap.put(Combo.NO_COMBO, new FlushAndNoComboGPP());
        return pokerContextMap;
    }
}
