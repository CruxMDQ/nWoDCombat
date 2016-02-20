package com.emi.nwodcombat.diceroller;

import android.support.v4.util.Pair;

import java.util.ArrayList;

/**
 * Created by Emi on 2/19/16.
 */
public interface AfterSettingRulesListener {
    void afterSettingRules(String tag, ArrayList<Pair<String, Boolean>> rules);
}
