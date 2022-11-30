package com.company.poker.processor.game;

import com.company.poker.domain.Combo;
import com.company.poker.domain.PokerHand;
import com.company.poker.processor.ComboProcessor;

public class GameProcessor {
    /**
     * The method processes poker hands of both players and determines strength of the hands combinations.
     * If the combinations are not the same the method give boolean result.
     * If both players have the same combination the method call corresponding method
     * for additional checking of the winner in corresponding game post processor
     * @param comboProcessor
     * @param pokerHand1
     * @param pokerHand2
     * @return true - when player #1 is a winner, false - player #2
     */
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
