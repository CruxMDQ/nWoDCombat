package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.interfaces.SpecialtiesModel;
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
import com.emi.nwodcombat.tools.Constants;

import java.util.NoSuchElementException;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerModel implements SpecialtiesModel {

    private Context mContext;
    private long id;
    private RealmHelper helper;
    private SharedPreferences preferences;
    private static Character character;

    public CharacterViewerModel(Activity activity, long id) {
        this.mContext = activity;
        this.id = id;
        helper = RealmHelper.getInstance(mContext);
        character = getQueriedCharacter();
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
        helper.updateEntry(characterId, entryId, String.valueOf(value));
    }

    public int getExperience() {
        return Integer.valueOf(getQueriedCharacter().getExperience().getValue());
    }

    public boolean isCheating() {
        return getPreferences().getBoolean(Constants.SETTING_CHEAT, false);
    }

    public int findEntryValue(String constant, String kind) {
        try {
            Entry entry = ArrayHelper.findEntry(character.getEntries(), constant);

            if (entry != null && entry.getType().equals(Constants.FIELD_TYPE_INTEGER)) {
                int result = Integer.valueOf(entry.getValue());

                return result;
            }
        } catch (NoSuchElementException e) {
            return getDefaultScore(kind);
        }
        return getDefaultScore(kind);
    }

    private int getDefaultScore(String kind) {
        switch (kind) {
            case Constants.ATTRIBUTE:
                return 1;
            case Constants.SKILL:
                return 0;
            default:
                return 0;
        }
    }

    public Integer getExperienceCost(String kind) {
        switch (kind) {
            case Constants.ATTRIBUTE:
                return mContext.getResources().getInteger(R.integer.cost_attributes);
            case Constants.SKILL:
                return mContext.getResources().getInteger(R.integer.cost_skills);
            default:
                return 0;
        }
    }

    public Entry addOrUpdateEntry(String key, String type, String value) {
        Entry entry = Entry.newInstance()
            .setKey(key)
            .setType(type)
            .setValue(value);

        for (Entry t : character.getEntries()) {
            if (t.getKey().equals(entry.getKey())) {
                entry.setId(t.getId());
            }
        }

        if (!helper.updateEntry(character.getId(), entry.getId(), value)) {
            helper.addEntry(character.getId(), key, type, value);
        }

        return entry;
    }

    public boolean checkIfCharacterHasEnoughXP(String kind) {
        Integer experienceCost = getExperienceCost(kind);

        Integer experiencePool = getExperience();

        return experiencePool >= experienceCost;
    }

    public RealmList<Entry> getAllSpecialties() {
        RealmList<Entry> skillsWithSpecialties = new RealmList<>();

        for (Entry entry : character.getEntries()) {
            if (entry.getExtras() != null && entry.getExtras().size() > 0) {
                skillsWithSpecialties.add(entry);
            }
        }

        return skillsWithSpecialties;
    }

    @Override
    public Entry addSpecialty(String key, String specialtyName) {
        for (Entry entry : character.getEntries()) {
            if (entry.getKey() != null &&
                entry.getKey().equalsIgnoreCase(key)) {

                Entry specialty = Entry.newInstance();
                specialty.setKey(Constants.SKILL_SPECIALTY);
                specialty.setType(Constants.FIELD_TYPE_STRING);
                specialty.setValue(specialtyName);

                if (entry.getExtras() == null) {
                    entry.setExtras(new RealmList<Entry>());
                }

                entry.getExtras().add(specialty);

                // DONE Code step similar to skill saving here - remember that this has to be encapsulated within Realm transactions
                helper.updateEntry(character.getId(), entry);

                return specialty;
            }
        }
        return null;
    }

    @Override
    public void removeSpecialty(String key, String specialty) {
        for (Entry entry : character.getEntries()) {
            if (entry.getKey() != null &&
                entry.getKey().equalsIgnoreCase(key)) {

                Entry entryToRemove = null;

                for (Entry extra : entry.getExtras()) {
                    if (extra.getValue() != null
                        && extra.getValue().equalsIgnoreCase(specialty)) {
                        entryToRemove = extra;
                        break;
                    }
                }

                if (entryToRemove != null) {
                    entry.getExtras().remove(entryToRemove);
                }

                helper.updateEntry(character.getId(), entry);

                break;
            }
        }
    }

    @Override
    public int countSpecialties() {
        return getAllSpecialties().size();
    }
}
