package com.company.poker.processor.game;

import com.company.poker.domain.Combo;
import com.company.poker.domain.PokerHand;
import com.company.poker.processor.ComboProcessor;
import com.company.poker.processor.game.context.PokerContext;

import java.lang.reflect.InvocationTargetException;

public class GameProcessor {

    private ComboProcessor comboProcessor;

    public GameProcessor(ComboProcessor comboProcessor) {
        this.comboProcessor = comboProcessor;
    }

    public ComboProcessor getComboProcessor() {
        return comboProcessor;
    }

    public void setComboProcessor(ComboProcessor comboProcessor) {
        this.comboProcessor = comboProcessor;
    }

    /**
     * The method processes poker hands of both players and determines strength of the hands combinations.
     * If the combinations are not the same the method give boolean result.
     * If both players have the same combination the method call corresponding method
     * for additional checking of the winner in corresponding game post processor
     * @param pokerHand1
     * @param pokerHand2
     * @return true - when player #1 is a winner, false - player #2
     */
    public boolean process(PokerHand pokerHand1, PokerHand pokerHand2) throws InvocationTargetException, IllegalAccessException {
        ComboProcessor comboProcessor = getComboProcessor();
        Combo combo1 = comboProcessor.process(pokerHand1);
        comboProcessor.getComboContext().clearComboContext();
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
