package com.emi.nwodcombat.characterwizard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardModel;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardPresenter;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardView;
import com.emi.nwodcombat.tools.BusProvider;

/**
 * Created by emiliano.desantis on 13/05/2016.
 */
public class CharacterWizardFragment extends Fragment {

    private CharacterWizardPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.register(presenter);
    }

    @Override
    public void onPause() {
        BusProvider.unregister(presenter);
        presenter.destroyCharacter();
        super.onPause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    private void createPresenter() {
        presenter = new CharacterWizardPresenter(
            CharacterWizardModel.getInstance(),
            new CharacterWizardView(this, BusProvider.getInstance()));
    }
}
