package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.wrappers.DemeanorTrait;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.wrappers.NatureTrait;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.model.realm.wrappers.ViceTrait;
import com.emi.nwodcombat.model.realm.wrappers.VirtueTrait;
import com.emi.nwodcombat.persistence.RealmHelper;
import com.emi.nwodcombat.utils.Constants;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerModel {

    private Context mContext;
    private RealmHelper helper;
    private SharedPreferences preferences;

    public CharacterViewerModel(Activity activity) {
        this.mContext = activity;
        helper = RealmHelper.getInstance(mContext);
    }

    public Character getQueriedCharacter(long id) {
        return helper.get(Character.class, id);
    }

    public RealmResults<Virtue> getVirtues() {
        return helper.getList(Virtue.class);
    }

    public RealmResults<Vice> getVices() {
        return helper.getList(Vice.class);
    }

    public RealmResults<Nature> getNatures() {
        return helper.getList(Nature.class);
    }

    public RealmResults<Demeanor> getDemeanors() {
        return helper.getList(Demeanor.class);
    }

    public void updateCharacter(Character updatedCharacter) {
        helper.updateCharacter(updatedCharacter);
    }

    public void deleteCharacter(Character characterToDelete) {
        helper.delete(characterToDelete);
    }

    /**
     * Method for returning singleton instance of SharedPreferences
     * @return Preferences object containing app settings
     */
    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = mContext.getSharedPreferences(Constants.TAG_SHARED_PREFS,
                Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public void updateDemeanorTrait(Long characterId, DemeanorTrait demeanorTrait) {
        helper.updateDemeanorTrait(characterId, demeanorTrait);
    }

    public void updateNatureTrait(Long characterId, NatureTrait natureTrait) {
        helper.updateNatureTrait(characterId, natureTrait);
    }

    public void updateViceTrait(Long characterId, ViceTrait viceTrait) {
        helper.updateViceTrait(characterId, viceTrait);
    }


    public void updateVirtueTrait(Long characterId, VirtueTrait virtueTrait) {
        helper.updateVirtueTrait(characterId, virtueTrait);
    }
}
