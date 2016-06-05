package com.emi.nwodcombat.characterwizard.steps;

import android.os.Bundle;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardModel;
import com.emi.nwodcombat.characterwizard.mvp.SummaryPresenter;
import com.emi.nwodcombat.characterwizard.mvp.SummaryView;
import com.emi.nwodcombat.utils.BusProvider;

public class SummaryFragment extends PagerFragment {
    private SummaryPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createPresenter();
    }

    private void createPresenter() {
        presenter = new SummaryPresenter(
                new CharacterWizardModel(getActivity()),
                new SummaryView(this)
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.register(presenter);
    }

    @Override
    public void onPause() {
        BusProvider.unregister(presenter);
        super.onPause();
    }

    @Override
    public int getLayout() {
        return R.layout.step_summary;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_summary);
    }
}
