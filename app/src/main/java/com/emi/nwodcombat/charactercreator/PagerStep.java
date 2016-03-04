package com.emi.nwodcombat.charactercreator;

import java.util.HashMap;

/**
 * Created by Emi on 3/3/16.
 */
public interface PagerStep {
    HashMap<String, Object> saveChoices();

    interface ParentStep {
        void clearChoices();
    }

    interface ChildStep {
        void retrieveChoices();
    }
}
