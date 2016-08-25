package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.adapters.MeritsAdapter;
import com.emi.nwodcombat.fragments.FragmentView;
import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 02/06/2016.
 * TODO UNTESTED - SPECIALTIES HAVE TO BE COMPLETED FIRST
 */
public class MeritsView extends FragmentView {
    private final Bus bus;

    @Bind(R.id.rvAvailableMerits) RecyclerView rvAvailableMerits;

    public MeritsView(Fragment fragment, Bus instance) {
        super(fragment);
        this.bus = instance;
        ButterKnife.bind(this, fragment.getView());
    }

    public void setupUI() {

    }

    public void setupRV(MeritsAdapter upRV) {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        lm.setAutoMeasureEnabled(true);
        rvAvailableMerits.setLayoutManager(lm);
        rvAvailableMerits.setAdapter(upRV);
    }
}
