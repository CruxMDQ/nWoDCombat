package com.emi.nwodcombat.characterviewer.mvp;

import com.emi.nwodcombat.characterviewer.interfaces.MainMVP;
import com.emi.nwodcombat.model.realm.Character;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerPresenter implements MainMVP.PresenterOps, MainMVP.RequiredPresenterOps {

    private MainMVP.RequiredViewOps mView;

    private MainMVP.ModelOps mModel;

    public CharacterViewerPresenter(MainMVP.ModelOps mModel, CharacterViewerView mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public RealmResults<Character> queryCharacters() {
        return null;
    }

    public void setUpView(long idCharacter) {
        mView.setUpView(mModel.getQueriedCharacter(idCharacter));
    }
}
