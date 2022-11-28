package com.company.poker.processor.game;

import com.company.poker.domain.Combo;
import com.company.poker.domain.PokerHand;
import com.company.poker.processor.ComboProcessor;
import com.company.poker.util.PokerContext;

public class GameProcessor {

    public boolean process(ComboProcessor comboProcessor, PokerHand pokerHand1, PokerHand pokerHand2) {
        Combo combo1 = comboProcessor.process(pokerHand1);
        Combo combo2 = comboProcessor.process(pokerHand2);
        int strength1 = combo1.getStrength();
        int strength2 = combo2.getStrength();
        if (strength1 == strength2) {
            GamePostProcessor gamePostProcessor = PokerContext.getPokerContextMap().get(combo1);
            return gamePostProcessor.postProcess(pokerHand1, pokerHand2);
        }
        return strength1 < strength2;
    }
}
