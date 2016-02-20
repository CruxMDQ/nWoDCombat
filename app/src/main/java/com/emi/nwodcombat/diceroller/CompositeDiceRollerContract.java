package com.emi.nwodcombat.diceroller;

import android.support.v4.util.Pair;

import java.util.ArrayList;

/**
 * Created by Emi on 2/19/16.
 */
public interface CompositeDiceRollerContract {
    interface View {
        void afterChoosingDice(String tag, int number);

        void afterSettingRules(String tag, ArrayList<Pair<String, Boolean>> rules);

        void setInputListener(InputListener listener);
    }

    interface InputListener {
        int setDice(String tag);

        void setSpecialRules(String tag);
    }
}
