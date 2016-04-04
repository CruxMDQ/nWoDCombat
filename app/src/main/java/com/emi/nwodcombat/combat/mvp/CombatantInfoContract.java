package com.emi.nwodcombat.combat.mvp;

import com.emi.nwodcombat.model.pojos.Rule;

/**
 * Created by Emi on 3/1/16.
 */
public interface CombatantInfoContract {
    interface View {
        void afterSettingRules(Rule rule);

        void setInputListener(InputListener listener);
    }

    interface InputListener {
        void setSpecialRules(String tag);
    }
}
