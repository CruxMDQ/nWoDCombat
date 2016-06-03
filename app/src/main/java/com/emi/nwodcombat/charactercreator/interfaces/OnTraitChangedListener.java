package com.emi.nwodcombat.charactercreator.interfaces;

/**
 * Created by Emi on 3/1/16.
 */
public interface OnTraitChangedListener {
    void onTraitChanged(int value, String constant, String category);
    void onSpecialtyChecked(boolean isChecked, String constant, String category);
}
