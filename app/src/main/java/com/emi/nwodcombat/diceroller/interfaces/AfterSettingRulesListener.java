package com.emi.nwodcombat.diceroller.interfaces;

import android.support.annotation.Nullable;

import com.emi.nwodcombat.diceroller.pojos.Rule;

/**
 * Created by Emi on 2/19/16.
 */
public interface AfterSettingRulesListener {
    void afterSettingRules(String tag, @Nullable Rule rule);
}
