package com.emi.nwodcombat.diceroller;

import android.app.FragmentManager;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by Emi on 2/19/16.
 */
public class CompositeDiceRollerPresenter implements CompositeDiceRollerContract.InputListener, AfterChoosingNumberListener, AfterSettingRulesListener {

    private CompositeDiceRollerContract.View view;
    private FragmentManager fragmentManager;

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
    public void setSpecialRules(String tag) {
        SpecialRollRulesDialog dialog = SpecialRollRulesDialog.newInstance("Select rules", tag, this);
        dialog.show(fragmentManager, "MainFragment");
    }

    @Override
    public void afterSettingRules(String tag, ArrayList<android.support.v4.util.Pair<String, Boolean>> rules) {
        view.afterSettingRules(tag, rules);
    }
}
