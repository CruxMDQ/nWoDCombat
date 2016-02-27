package com.emi.nwodcombat.diceroller.mvp;

import android.app.FragmentManager;
import android.support.annotation.NonNull;

import com.emi.nwodcombat.diceroller.dialogs.DiceCalcDialog;
import com.emi.nwodcombat.diceroller.dialogs.SpecialRollRulesDialog;
import com.emi.nwodcombat.diceroller.interfaces.AfterChoosingNumberListener;
import com.emi.nwodcombat.diceroller.interfaces.AfterSettingRulesListener;
import com.emi.nwodcombat.model.pojos.Rule;
import com.emi.nwodcombat.tools.Roller;

import java.util.ArrayList;

/**
 * Created by Emi on 2/19/16.
 */
public class CompositeDiceRollerPresenter implements CompositeDiceRollerContract.InputListener, AfterChoosingNumberListener, AfterSettingRulesListener {

    private CompositeDiceRollerContract.View view;
    private FragmentManager fragmentManager;

    private static int MAXIMUM_DIE_VALUE = 10;

    public CompositeDiceRollerPresenter(@NonNull CompositeDiceRollerContract.View view, @NonNull FragmentManager fragmentManager) {
        this.view = view;
        this.fragmentManager = fragmentManager;
        view.setInputListener(this);
    }

    @Override
    public void afterChoosingNumber(String tag, int number) {
        view.afterChoosingDice(tag, number);
    }

    @Override
    public int setDice(String tag) {
        DiceCalcDialog dialog = DiceCalcDialog.newInstance("Pick dice", tag, this);
        dialog.show(fragmentManager, "MainFragment");
        return 0;
    }

    @Override
    public ArrayList<Integer> rollDice(int number, int threshold) {
        return Roller.rollNWoDDice(number, MAXIMUM_DIE_VALUE, threshold);
    }

    @Override
    public void setSpecialRules(String tag) {
        SpecialRollRulesDialog dialog = SpecialRollRulesDialog.newInstance("Select rules", tag, this);
        dialog.show(fragmentManager, "MainFragment");
    }

    @Override
    public void afterSettingRules(String tag, Rule rule) {
        view.afterSettingRules(tag, rule);
    }
}
