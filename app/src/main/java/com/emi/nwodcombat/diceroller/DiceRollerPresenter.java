package com.emi.nwodcombat.diceroller;

import android.app.FragmentManager;
import android.support.annotation.NonNull;

import com.emi.nwodcombat.Constants;

/**
 * Created by Emi on 2/18/16.
 */
public class DiceRollerPresenter implements DiceRollerContract.InputListener,
    AfterChoosingNumberListener {

    private DiceRollerContract.View view;

    public DiceRollerPresenter(@NonNull DiceRollerContract.View view) {
        this.view = view;
        view.setInputListener(this);
    }

    @Override
    public int setAttackerDice(FragmentManager manager) {
        NumberPickerDialogFragment dialog = NumberPickerDialogFragment.newInstance("Pick dice", Constants.DICE_ATTACKER, this);
        dialog.show(manager, "MainFragment");
        return 0;
    }

    @Override
    public int setDefenderDice(FragmentManager manager) {
        NumberPickerDialogFragment dialog = NumberPickerDialogFragment.newInstance("Pick dice", Constants.DICE_DEFENDER, this);
        dialog.show(manager, "MainFragment");
        return 0;
    }

    @Override
    public void afterChoosingNumber(String tag, int number) {
        view.afterChoosingDice(tag, number);
    }
}
