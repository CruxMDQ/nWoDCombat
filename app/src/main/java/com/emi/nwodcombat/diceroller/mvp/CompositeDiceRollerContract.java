package com.emi.nwodcombat.diceroller.mvp;

import android.support.annotation.Nullable;

import com.emi.nwodcombat.model.pojos.Rule;

import java.util.ArrayList;

/**
 * Created by Emi on 2/19/16.
 */
public interface CompositeDiceRollerContract {
    interface View {
        void afterChoosingDice(String tag, int number);

        void afterSettingRules(String tag, @Nullable Rule rule);

        void setInputListener(InputListener listener);
    }

    interface InputListener {
        int setDice(String tag);

        ArrayList<Integer> rollDice(int number, int threshold);

        void setSpecialRules(String tag);
    }
}
