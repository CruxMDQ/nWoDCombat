package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.model.realm.wrappers.DemeanorTrait;
import com.emi.nwodcombat.model.realm.wrappers.NatureTrait;
import com.emi.nwodcombat.model.realm.wrappers.ViceTrait;
import com.emi.nwodcombat.model.realm.wrappers.VirtueTrait;
import com.emi.nwodcombat.persistence.RealmHelper;
import com.emi.nwodcombat.tools.ArrayHelper;
import com.emi.nwodcombat.utils.Constants;

import java.util.NoSuchElementException;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/05/2016.
 */
public class CharacterWizardModel {
    private Activity activity;
    private RealmHelper helper;
    private SharedPreferences preferences;

    public static Character character = new Character();

    public CharacterWizardModel(Activity activity) {
        this.activity = activity;
        helper = RealmHelper.getInstance(this.activity);
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

    public boolean isCheating() {
        return getPreferences().getBoolean(Constants.SETTING_CHEAT, false);
    }

    /**
     * Method for returning singleton instance of SharedPreferences
     * @return Preferences object containing app settings
     */
    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = activity.getSharedPreferences(Constants.TAG_SHARED_PREFS,
                Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public void setupNewCharacter() {
        character.setId(helper.getLastId(Character.class));
    }

    public void addOrUpdateDemeanorTrait(Demeanor demeanor) {

        // This object will be posted for handling by the model
        DemeanorTrait demeanorTrait = new DemeanorTrait();

        demeanorTrait.setType(Constants.CHARACTER_DEMEANOR);
        demeanorTrait.setDemeanor(demeanor);

        // This should change depending on which demeanor we're
        // editing: first, second, third, or whatever
        demeanorTrait.setOrdinal(0L);

        for (DemeanorTrait trait : character.getDemeanorTraits()) {
            if (trait.getOrdinal().equals(demeanorTrait.getOrdinal())) {
                character.getDemeanorTraits().remove(trait);
            }
        }

        character.getDemeanorTraits().add(demeanorTrait);
    }

    public void addOrUpdateNatureTrait(Nature nature) {
        NatureTrait natureTrait = new NatureTrait();
        natureTrait.setType(Constants.CHARACTER_NATURE);
        natureTrait.setNature(nature);
        natureTrait.setOrdinal(0L);

        for (NatureTrait trait : character.getNatureTraits()) {
            if (trait.getOrdinal().equals(natureTrait.getOrdinal())) {
                character.getNatureTraits().remove(trait);
            }
        }

        character.getNatureTraits().add(natureTrait);
    }

    public void addOrUpdateViceTrait(Vice vice) {
        ViceTrait viceTrait = new ViceTrait();
        viceTrait.setType(Constants.CHARACTER_VICE);
        viceTrait.setVice(vice);
        viceTrait.setOrdinal(0L);

        for (ViceTrait trait : character.getViceTraits()) {
            if (trait.getOrdinal().equals(viceTrait.getOrdinal())) {
                character.getViceTraits().remove(trait);
            }
        }

        character.getViceTraits().add(viceTrait);
    }

    public void addOrUpdateVirtueTrait(Virtue virtue) {
        VirtueTrait virtueTrait = new VirtueTrait();
        virtueTrait.setType(Constants.CHARACTER_VIRTUE);
        virtueTrait.setVirtue(virtue);
        virtueTrait.setOrdinal(0L);

        for (VirtueTrait trait : character.getVirtueTraits()) {
            if (trait.getOrdinal().equals(virtueTrait.getOrdinal())) {
                character.getVirtueTraits().remove(trait);
            }
        }

        character.getVirtueTraits().add(virtueTrait);
    }

    public Entry addOrUpdateEntry(Entry entry) {
        entry.setId(character.getEntries().size());

        for (Entry t : character.getEntries()) {
            if (t.getKey().equals(entry.getKey())) {
                entry.setId(t.getId());
                character.getEntries().set(character.getEntries().indexOf(t), entry);
//                character.getEntries().remove(t);
                return entry;
            }
        }

        character.getEntries().add(entry);

        return entry;
    }

    public int getPointsSpentOnMental() {
        int intelligence = findEntryValue(Constants.ATTR_INT, 1);
        int wits = findEntryValue(Constants.ATTR_WIT, 1);
        int resolve = findEntryValue(Constants.ATTR_RES, 1);

        int result = intelligence + wits + resolve - Constants.ATTR_PTS_TERTIARY;

        return result;
    }

    public int getPointsSpentOnPhysical() {
        int strength = findEntryValue(Constants.ATTR_STR, 1);
        int dexterity = findEntryValue(Constants.ATTR_DEX, 1);
        int stamina = findEntryValue(Constants.ATTR_STA, 1);

        int result = strength + dexterity + stamina - Constants.ATTR_PTS_TERTIARY;

        return result;
    }

    public int getPointsSpentOnSocial() {
        int presence = findEntryValue(Constants.ATTR_PRE, 1);
        int manipulation = findEntryValue(Constants.ATTR_MAN, 1);
        int composure = findEntryValue(Constants.ATTR_COM, 1);

        int result = presence + manipulation + composure - Constants.ATTR_PTS_TERTIARY;

        return result;
    }

    public int findEntryValue(String constant, int defaultValue) {
        try {
            Entry entry = ArrayHelper.findEntry(character.getEntries(), constant);

            if (entry.getType().equals(Constants.FIELD_TYPE_INTEGER)) {
                int result = Integer.valueOf(entry.getValue());

                return result;
            }
        } catch (NoSuchElementException e) {
            return defaultValue;
        }
        return defaultValue;
    }
}
