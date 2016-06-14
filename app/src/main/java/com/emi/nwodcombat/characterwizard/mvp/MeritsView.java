package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;

import com.emi.nwodcombat.adapters.MeritsAdapter;
import com.emi.nwodcombat.fragments.FragmentView;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 02/06/2016.
 * TODO UNTESTED - SPECIALTIES HAVE TO BE COMPLETED FIRST
 */
public class MeritsView extends FragmentView {
    private final Bus bus;

    public MeritsView(Fragment fragment, Bus instance) {
        super(fragment);
        this.bus = instance;
        ButterKnife.bind(this, fragment.getView());
    }

    public void setupUI() {

    }

    public void setupRV(MeritsAdapter upRV) {
    }
}
