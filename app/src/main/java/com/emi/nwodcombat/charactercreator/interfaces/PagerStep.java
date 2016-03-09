package com.emi.nwodcombat.charactercreator.interfaces;

import java.util.HashMap;

/**
 * Created by Emi on 3/3/16.
 */
public interface PagerStep {
    int getLayout();

    String getToolbarTitle();

    void checkCompletionConditions();

    HashMap<String, Object> saveChoices();

    interface ParentStep {
        void clearChoices();
    }

    interface ChildStep {
        void retrieveChoices();

        boolean hasLeftoverPoints();
    }
}
