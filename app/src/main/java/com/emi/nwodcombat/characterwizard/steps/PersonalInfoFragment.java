package com.emi.nwodcombat.characterwizard.steps;

import android.os.Bundle;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardModel;
import com.emi.nwodcombat.characterwizard.mvp.PersonalInfoPresenter;
import com.emi.nwodcombat.characterwizard.mvp.PersonalInfoView;
import com.emi.nwodcombat.utils.BusProvider;
import com.emi.nwodcombat.utils.Constants;

public class PersonalInfoFragment extends PagerFragment {
    private PersonalInfoPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createPresenter();
    }

    private void createPresenter() {
        presenter = new PersonalInfoPresenter(
                new CharacterWizardModel(getActivity()),
                new PersonalInfoView(this, BusProvider.getInstance())
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
    public String getToolbarTitle() {
        return Constants.TITLE_STEP_PERSONAL_INFO;
    }

    @Override
    public int getLayout() {
        return R.layout.step_personal_info;
    }
}
