package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Activity;
import android.content.Context;

import com.emi.nwodcombat.characterviewer.interfaces.MainMVP;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.persistence.RealmHelper;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerModel implements MainMVP.ModelOps {

    // Presenter reference
    private MainMVP.RequiredPresenterOps mPresenter;
    private Context mContext;
    private RealmHelper helper;

    public CharacterViewerModel(Activity activity) {
        this.mContext = activity;
        helper = RealmHelper.getInstance(mContext);
    }

    @Override
    public Character getQueriedCharacter(long id) {
        return (Character) helper.get(Character.class, id);
    }

    @Override
    public void updateCharacter(Character character) {

    }

    public void setCallback(CharacterViewerPresenter callback) {
        this.mPresenter = callback;
    }

    public MainMVP.RequiredPresenterOps getCallback() {
        return mPresenter;
    }
}
