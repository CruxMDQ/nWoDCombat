package com.emi.nwodcombat.interfaces;

import android.support.annotation.Nullable;

import com.emi.nwodcombat.model.pojos.Rule;

/**
 * Created by Emi on 2/19/16.
 */
public interface AfterSettingRulesListener {
    void afterSettingRules(@Nullable Rule rule);
}
