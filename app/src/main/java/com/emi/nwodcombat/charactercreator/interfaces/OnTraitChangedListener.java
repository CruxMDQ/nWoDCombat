package com.emi.nwodcombat.charactercreator.interfaces;

/**
 * Created by Emi on 3/1/16.
 * TODO Remove the Object parameter once the wizard refactor is complete
 */
public interface OnTraitChangedListener {
    void onTraitChanged(Object caller, int value, String constant, String category);
}
