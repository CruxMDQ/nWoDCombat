package com.emi.nwodcombat.interfaces;

/**
 * Created by Emi on 3/1/16.
 */
public interface OnTraitChangedListener {
    void onTraitChanged(int value, String constant, String kind, String category);
    void onSpecialtyTapped(boolean isChecked, String constant, String category);
}
