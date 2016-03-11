package com.emi.nwodcombat.charactercreator.interfaces;

import com.emi.nwodcombat.model.db.Character;

/**
 * Created by Emi on 3/3/16.
 */
public interface PagerMaster {
    void checkStepIsComplete(boolean isComplete, PagerStep caller);

    void commitChoices(Character character);
}
