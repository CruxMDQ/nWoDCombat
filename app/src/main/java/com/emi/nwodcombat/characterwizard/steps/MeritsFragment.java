package com.emi.nwodcombat.characterwizard.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardModel;
import com.emi.nwodcombat.characterwizard.mvp.MeritsPresenter;
import com.emi.nwodcombat.characterwizard.mvp.MeritsView;
import com.emi.nwodcombat.tools.BusProvider;

/**
 * Created by emiliano.desantis on 02/06/2016.
 * TODO UNTESTED - SPECIALTIES HAVE TO BE COMPLETED FIRST
 */
public class MeritsFragment extends PagerFragment {
    private MeritsPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createPresenter();
    }

    private void createPresenter() {
        presenter = new MeritsPresenter(CharacterWizardModel.getInstance(),
            new MeritsView(this, BusProvider.getInstance()));
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.register(presenter);
        presenter.checkSettings();
    }

    @Override
    public void onPause() {
        BusProvider.unregister(presenter);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_pick_merits;
    }
}
