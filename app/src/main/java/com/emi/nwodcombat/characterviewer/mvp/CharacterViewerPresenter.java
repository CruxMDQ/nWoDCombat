package com.emi.nwodcombat.characterviewer.mvp;

import com.emi.nwodcombat.model.realm.Character;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerPresenter  {
    private CharacterViewerView view;
    private CharacterViewerModel model;

    public CharacterViewerPresenter(CharacterViewerModel model, CharacterViewerView view) {
        this.model = model;
        this.view = view;
    }

    public void setUpView(long idCharacter) {
        view.setUpView(model.getQueriedCharacter(idCharacter));
    }
}
