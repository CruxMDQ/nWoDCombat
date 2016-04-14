package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;

import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.FragmentView;
import com.emi.nwodcombat.characterviewer.interfaces.MainMVP;

import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerView extends FragmentView implements MainMVP.RequiredViewOps {

    private Character character;

    private MainMVP.PresenterOps callback;

    public CharacterViewerView(Fragment fragment) {
        super(fragment);
        ButterKnife.bind(this, fragment.getView());
    }

    @Override
    public void setUpView(Character queriedCharacter) {
        this.character = queriedCharacter;
    }

    public void setCallback(MainMVP.PresenterOps callback) {
        this.callback = callback;
    }

    public MainMVP.PresenterOps getCallback() {
        return callback;
    }
}
