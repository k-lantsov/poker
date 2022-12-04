package com.company.poker.processor.game;

import com.company.poker.domain.Combo;
import com.company.poker.domain.PokerHand;
import com.company.poker.processor.ComboProcessor;
import com.company.poker.processor.game.context.PokerContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
     * @return true - when player #1 is a winner, false - player #2
     */
    public boolean process(List<PokerHand> pokerHands) throws InvocationTargetException, IllegalAccessException {
        ComboProcessor comboProcessor = getComboProcessor();
        List<Combo> combos = new ArrayList<>();
        for (PokerHand pokerHand: pokerHands) {
            Combo combo = comboProcessor.process(pokerHand);
            combos.add(combo);
            comboProcessor.getComboContext().clearComboContext();
        }
        int strength1 = combos.get(0).getStrength();
        int strength2 = combos.get(1).getStrength();
        if (strength1 == strength2) {
            GamePostProcessor gamePostProcessor = PokerContext.getPokerContextMap().get(combos.get(0));
            return gamePostProcessor.postProcess(pokerHands);
        }
        return strength1 < strength2;
    }
}
