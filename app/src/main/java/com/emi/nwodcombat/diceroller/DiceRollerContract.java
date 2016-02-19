package com.emi.nwodcombat.diceroller;

import android.app.FragmentManager;

/**
 * Created by Emi on 2/18/16.
 */
public interface DiceRollerContract {

    interface View {
        void afterChoosingDice(String tag, int number);

        void setInputListener(InputListener listener);
    }

    interface InputListener {
        int setAttackerDice(FragmentManager manager);

        int setDefenderDice(FragmentManager manager);
    }
}
