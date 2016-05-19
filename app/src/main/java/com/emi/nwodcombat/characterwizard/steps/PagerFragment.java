package com.emi.nwodcombat.characterwizard.steps;

import android.app.Fragment;

/**
 * Created by Emi on 3/7/16.
 */
public abstract class PagerFragment extends Fragment {
    public PagerFragment() { }

    public abstract String getToolbarTitle();

    public abstract int getLayout();
}
