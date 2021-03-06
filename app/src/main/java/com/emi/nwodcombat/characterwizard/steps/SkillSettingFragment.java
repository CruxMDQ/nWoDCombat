package com.emi.nwodcombat.characterwizard.steps;

import android.os.Bundle;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardModel;
import com.emi.nwodcombat.characterwizard.mvp.SkillSettingPresenter;
import com.emi.nwodcombat.characterwizard.mvp.SkillSettingView;
import com.emi.nwodcombat.tools.BusProvider;

/**
 * Created by emiliano.desantis on 23/05/2016.
 */
public class SkillSettingFragment extends PagerFragment {
    private SkillSettingPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createPresenter();
    }

    private void createPresenter() {
        presenter = new SkillSettingPresenter(new CharacterWizardModel(getActivity()),
                new SkillSettingView(this, BusProvider.getInstance()));
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
    public String getToolbarTitle() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.step_skill_set;
    }
}
