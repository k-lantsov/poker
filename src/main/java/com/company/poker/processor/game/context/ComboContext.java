package com.company.poker.processor.game.context;

import com.company.poker.domain.Combo;

public class ComboContext {

    private Combo combo;

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }

    public void clearComboContext() {
        combo = null;
    }
}
