package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.emi.nwodcombat.R;
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

import java.util.ArrayList;
import java.util.Iterator;
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

    public Entry addOrUpdateEntry(String key, String type, String value) {
        Entry entry = new Entry()
            .setKey(key)
            .setType(type)
            .setValue(value);

        entry.setId(character.getEntries().size());

        for (Entry t : character.getEntries()) {
            if (t.getKey().equals(entry.getKey())) {
                entry.setId(t.getId());
                character.getEntries().set(character.getEntries().indexOf(t), entry);
                return entry;
            }
        }

        character.getEntries().add(entry);

        return entry;
    }

    public Entry addOrUpdateEntry(Entry entry) {
        entry.setId(character.getEntries().size());

        for (Entry t : character.getEntries()) {
            if (t.getKey().equals(entry.getKey())) {
                entry.setId(t.getId());
                character.getEntries().set(character.getEntries().indexOf(t), entry);
                return entry;
            }
        }

        character.getEntries().add(entry);

        return entry;
    }

    private int getPointsSpentOnAttributes(int idArray) {
        return getPointsSpent(idArray, Constants.ATTR_PTS_TERTIARY, 1);
    }

    public int getPointsSpentOnAttrMental() {
        return getPointsSpentOnAttributes(R.array.attributes_mental);
    }

    public int getPointsSpentOnAttrPhysical() {
        return getPointsSpentOnAttributes(R.array.attributes_physical);
    }

    public int getPointsSpentOnAttrSocial() {
        return getPointsSpentOnAttributes(R.array.attributes_social);
    }

    private int getPointsSpentOnSkills(int idArray) {
        return getPointsSpent(idArray, 0, 0);
    }

    public int getPointsSpentOnMentalSkills() {
        return getPointsSpentOnSkills(R.array.skills_mental);
    }

    public int getPointsSpentOnPhysicalSkills() {
        return getPointsSpentOnSkills(R.array.skills_physical);
    }

    public int getPointsSpentOnSocialSkills() {
        return getPointsSpentOnSkills(R.array.skills_social);
    }

    private int getPointsSpent(int idArray, int takeawayValue, int defaultValue) {
        int result = 0 - takeawayValue;

        for (String skill : activity.getResources().getStringArray(idArray)) {
            result += findEntryValue(skill, defaultValue);
        }

        return result;
    }

    public int findEntryValue(String constant, int defaultValue) {
        try {
            Entry entry = ArrayHelper.findEntry(character.getEntries(), constant);

            if (entry != null && entry.getType().equals(Constants.FIELD_TYPE_INTEGER)) {
                int result = Integer.valueOf(entry.getValue());

                return result;
            }
        } catch (NoSuchElementException e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public String getMentalAttrSummary() {
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(character.getIntelligence());
        entries.add(character.getWits());
        entries.add(character.getResolve());

        return getStatBlock(entries);
    }

    public String getPhysicalAttrSummary() {
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(character.getStrength());
        entries.add(character.getDexterity());
        entries.add(character.getStamina());

        return getStatBlock(entries);
    }

    public String getSocialAttrSummary() {
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(character.getIntelligence());
        entries.add(character.getWits());
        entries.add(character.getResolve());

        return getStatBlock(entries);
    }

    public String getMentalSkillsSummary() {
        ArrayList<Entry> entries = new ArrayList<>();

        if (character.getAcademics() != null) {
            entries.add(character.getAcademics());
        }
        if (character.getComputer() != null) {
            entries.add(character.getComputer());
        }
        if (character.getCrafts() != null) {
            entries.add(character.getCrafts());
        }
        if (character.getInvestigation() != null) {
            entries.add(character.getInvestigation());
        }
        if (character.getMedicine() != null) {
            entries.add(character.getMedicine());
        }
        if (character.getOccult() != null) {
            entries.add(character.getOccult());
        }
        if (character.getPolitics() != null) {
            entries.add(character.getPolitics());
        }
        if (character.getScience() != null) {
            entries.add(character.getScience());
        }

        return getStatBlock(entries);
    }

    public String getPhysicalSkillsSummary() {
        ArrayList<Entry> entries = new ArrayList<>();

        if (character.getAthletics() != null) {
            entries.add(character.getAthletics());
        }
        if (character.getBrawl() != null) {
            entries.add(character.getBrawl());
        }
        if (character.getDrive() != null) {
            entries.add(character.getDrive());
        }
        if (character.getFirearms() != null) {
            entries.add(character.getFirearms());
        }
        if (character.getLarceny() != null) {
            entries.add(character.getLarceny());
        }
        if (character.getStealth() != null) {
            entries.add(character.getStealth());
        }
        if (character.getSurvival() != null) {
            entries.add(character.getSurvival());
        }
        if (character.getWeaponry() != null) {
            entries.add(character.getWeaponry());
        }

        return getStatBlock(entries);
    }

    public String getSocialSkillsSummary() {
        ArrayList<Entry> entries = new ArrayList<>();

        if (character.getAnimalKen() != null) {
            entries.add(character.getAnimalKen());
        }
        if (character.getEmpathy() != null) {
            entries.add(character.getEmpathy());
        }
        if (character.getExpression() != null) {
            entries.add(character.getExpression());
        }
        if (character.getIntimidation() != null) {
            entries.add(character.getIntimidation());
        }
        if (character.getPersuasion() != null) {
            entries.add(character.getPersuasion());
        }
        if (character.getSocialize() != null) {
            entries.add(character.getSocialize());
        }
        if (character.getStreetwise() != null) {
            entries.add(character.getStreetwise());
        }
        if (character.getWeaponry() != null) {
            entries.add(character.getWeaponry());
        }

        return getStatBlock(entries);
    }

    private String getStatBlock(ArrayList<Entry> stats) {
        StringBuilder builder = new StringBuilder();

        Iterator<Entry> iterator = stats.iterator();

        while (iterator.hasNext()) {
            Entry entry = iterator.next();

            if (entry.getType().equalsIgnoreCase(Constants.FIELD_TYPE_INTEGER) &&
                Integer.valueOf(entry.getValue()) > 0) {
                builder.append(entry.getKey());
                builder.append(" ");
                builder.append(String.valueOf(entry.getValue()));
                if (iterator.hasNext()) {
                    builder.append(", ");
                }
            }
        }

        return builder.toString();
    }

    public void save() {
        character.setId(helper.getLastId(Character.class));

        helper.save(character);

        character = null;
    }
}
