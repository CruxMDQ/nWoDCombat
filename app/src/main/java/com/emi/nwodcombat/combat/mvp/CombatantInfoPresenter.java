package com.emi.nwodcombat.combat.mvp;

import android.app.FragmentManager;
import android.support.annotation.Nullable;

import com.emi.nwodcombat.combat.dialogs.SpecialRollRulesDialog;
import com.emi.nwodcombat.interfaces.AfterSettingRulesListener;
import com.emi.nwodcombat.model.pojos.Rule;

/**
 * Created by Emi on 3/1/16.
 */
public class CombatantInfoPresenter implements CombatantInfoContract.InputListener, AfterSettingRulesListener {
    private final CombatantInfoContract.View view;
    private final FragmentManager fragmentManager;

    public CombatantInfoPresenter(CombatantInfoContract.View view, FragmentManager fragmentManager) {
        this.view = view;
        this.fragmentManager = fragmentManager;
        view.setInputListener(this);
    }

    @Override
    public void setSpecialRules(String tag) {
        SpecialRollRulesDialog dialog = SpecialRollRulesDialog.newInstance("Select rules", tag, this);
        dialog.show(fragmentManager, "someFragment");
    }

    @Override
    public void afterSettingRules(@Nullable Rule rule) {
        view.afterSettingRules(rule);
    }
}
