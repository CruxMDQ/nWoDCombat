package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.model.realm.wrappers.DemeanorTrait;
import com.emi.nwodcombat.model.realm.wrappers.NatureTrait;
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
    private long id;
    private RealmHelper helper;
    private SharedPreferences preferences;

    public CharacterViewerModel(Activity activity, long id) {
        this.mContext = activity;
        this.id = id;
        helper = RealmHelper.getInstance(mContext);
    }

    public Character getQueriedCharacter() {
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

    public void deleteCharacter(long id) {
        helper.delete(Character.class, id);
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

    public void updateDemeanorTrait(Long characterId, Demeanor demeanor) {

        // This object will be posted for handling by the model
        DemeanorTrait demeanorTrait = new DemeanorTrait();

        demeanorTrait.setType(Constants.CHARACTER_DEMEANOR);
        demeanorTrait.setDemeanor(demeanor);

        // This should change depending on which demeanor we're
        // editing: first, second, third, or whatever
        demeanorTrait.setOrdinal(0L);

        helper.updateDemeanorTrait(characterId, demeanorTrait);
    }

    public void updateNatureTrait(Long characterId, Nature nature) {
        NatureTrait natureTrait = new NatureTrait();
        natureTrait.setType(Constants.CHARACTER_NATURE);
        natureTrait.setNature(nature);
        natureTrait.setOrdinal(0L);

        helper.updateNatureTrait(characterId, natureTrait);
    }

    public void updateViceTrait(Long characterId, Vice vice) {
        ViceTrait viceTrait = new ViceTrait();
        viceTrait.setType(Constants.CHARACTER_VICE);
        viceTrait.setVice(vice);
        viceTrait.setOrdinal(0L);

        helper.updateViceTrait(characterId, viceTrait);
    }


    public void updateVirtueTrait(Long characterId, Virtue virtue) {
        VirtueTrait virtueTrait = new VirtueTrait();
        virtueTrait.setType(Constants.CHARACTER_VIRTUE);
        virtueTrait.setVirtue(virtue);
        virtueTrait.setOrdinal(0L);

        helper.updateVirtueTrait(characterId, virtueTrait);
    }

    public void updateEntry(Long characterId, Long entryId, int value) {
        helper.updateEntry(characterId, entryId, value);
    }

    public int getExperience() {
        return Integer.valueOf(getQueriedCharacter().getExperience().getValue());
    }

    public boolean isCheating() {
        return getPreferences().getBoolean(Constants.SETTING_CHEAT, false);
    }
}
