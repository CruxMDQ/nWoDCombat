package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;

import com.emi.nwodcombat.FragmentView;
import com.emi.nwodcombat.characterviewer.interfaces.MainMVP;

import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerView extends FragmentView implements MainMVP.RequiredViewOps {

    private MainMVP.PresenterOps mPresenter;
    private CharacterViewerPresenter callback;

    public CharacterViewerView(Fragment fragment) {
        super(fragment);
        ButterKnife.bind(this, fragment.getView());
    }

    @Override
    public void setUpView(com.emi.nwodcombat.model.realm.Character queriedCharacter) {

    }

    public void setCallback(CharacterViewerPresenter callback) {
        this.callback = callback;
    }

    public CharacterViewerPresenter getCallback() {
        return callback;
    }
}
