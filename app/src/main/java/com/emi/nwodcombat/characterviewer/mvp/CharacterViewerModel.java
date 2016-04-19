package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Activity;
import android.content.Context;

import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.persistence.RealmHelper;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerModel {

    // Presenter reference
    private Context mContext;
    private RealmHelper helper;

    public CharacterViewerModel(Activity activity) {
        this.mContext = activity;
        helper = RealmHelper.getInstance(mContext);
    }

    public Character getQueriedCharacter(long id) {
        return helper.get(Character.class, id);
    }
}
