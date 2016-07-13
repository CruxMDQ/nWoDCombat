package com.emi.nwodcombat.characterwizard.steps;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Emi on 3/7/16.
 */
public abstract class PagerFragment extends Fragment {
    public PagerFragment() { }

    public abstract String getToolbarTitle();

    protected abstract int getLayout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }
}


