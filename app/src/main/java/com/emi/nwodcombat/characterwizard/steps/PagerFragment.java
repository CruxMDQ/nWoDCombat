package com.emi.nwodcombat.characterwizard.steps;

import android.app.Fragment;

import com.squareup.otto.Bus;

/**
 * Created by Emi on 3/7/16.
 */
public abstract class PagerFragment extends Fragment {
    protected Bus bus;

    public PagerFragment() { }

//    protected abstract void setUpUI();

//    public abstract boolean checkCompletionConditions();

    public abstract String getToolbarTitle();

    public abstract int getLayout();
}
