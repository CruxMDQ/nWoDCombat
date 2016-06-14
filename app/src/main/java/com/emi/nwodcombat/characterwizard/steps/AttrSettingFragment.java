package com.emi.nwodcombat.characterwizard.steps;

import android.os.Bundle;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.mvp.AttrSettingPresenter;
import com.emi.nwodcombat.characterwizard.mvp.AttrSettingView;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardModel;
import com.emi.nwodcombat.tools.BusProvider;
import com.emi.nwodcombat.tools.Constants;
import com.squareup.otto.Bus;

public class AttrSettingFragment extends PagerFragment {
    private AttrSettingPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createPresenter();
    }

    private void createPresenter() {
        Bus bus = BusProvider.getInstance();
        presenter = new AttrSettingPresenter(new CharacterWizardModel(getActivity()),
                new AttrSettingView(this, bus), bus);
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
    public int getLayout() {
        return R.layout.step_attr_set;
    }

    @Override
    public String getToolbarTitle() {
        return Constants.TITLE_STEP_POINTS_SET;
    }
}
