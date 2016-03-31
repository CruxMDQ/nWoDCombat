package com.emi.nwodcombat.characterlist.mvp;

import com.emi.nwodcombat.characterlist.interfaces.MainMVP;

/**
 * Created by emiliano.desantis on 29/03/2016.
 */
public class CharacterListModel implements MainMVP.ModelOps {

    // Presenter reference
    private MainMVP.RequiredPresenterOps mPresenter;

    public CharacterListModel(MainMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void insertCharacter(com.emi.nwodcombat.model.db.Character character) {
        mPresenter.onCharacterAdded();
    }

    @Override
    public void removeCharacter(long character) {
        mPresenter.onCharacterRemoved();
    }

    @Override
    public void onDestroy() {
        // Should stop/kill operations that could be running and aren't needed anymoar
    }
}
