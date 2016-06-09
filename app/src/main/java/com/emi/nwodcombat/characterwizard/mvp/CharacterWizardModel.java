package com.emi.nwodcombat.characterwizard.mvp;

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

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/05/2016.
 */
public class CharacterWizardModel {
    private Context context;
    private RealmHelper helper;
    private SharedPreferences preferences;

    public static Character character = new Character();

    public CharacterWizardModel(Context context) {
        this.context = context;
        helper = RealmHelper.getInstance(this.context);
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
            preferences = context.getSharedPreferences(Constants.TAG_SHARED_PREFS,
                Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public void setupNewCharacter() {
        character = new Character();
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
                demeanorTrait.setId(demeanor.getId());
                character.getDemeanorTraits().set(character.getDemeanorTraits().indexOf(trait), demeanorTrait);
                return;
            }
        }

        demeanorTrait.setId(helper.getLastId(DemeanorTrait.class), character.getDemeanorTraits().size());

        character.getDemeanorTraits().add(demeanorTrait);
    }

    public void addOrUpdateNatureTrait(Nature nature) {
        NatureTrait natureTrait = new NatureTrait();
        natureTrait.setType(Constants.CHARACTER_NATURE);
        natureTrait.setNature(nature);
        natureTrait.setOrdinal(0L);

        for (NatureTrait trait : character.getNatureTraits()) {
            if (trait.getOrdinal().equals(natureTrait.getOrdinal())) {
                natureTrait.setId(nature.getId());
                character.getNatureTraits().set(character.getNatureTraits().indexOf(trait), natureTrait);
                return;
            }
        }

        natureTrait.setId(helper.getLastId(NatureTrait.class), character.getNatureTraits().size());

        character.getNatureTraits().add(natureTrait);
    }

    public void addOrUpdateViceTrait(Vice vice) {
        ViceTrait viceTrait = new ViceTrait();
        viceTrait.setType(Constants.CHARACTER_VICE);
        viceTrait.setVice(vice);
        viceTrait.setOrdinal(0L);

        for (ViceTrait trait : character.getViceTraits()) {
            if (trait.getOrdinal().equals(viceTrait.getOrdinal())) {
                viceTrait.setId(vice.getId());
                character.getViceTraits().set(character.getViceTraits().indexOf(trait), viceTrait);
                return;
            }
        }

        viceTrait.setId(helper.getLastId(ViceTrait.class), character.getViceTraits().size());

        character.getViceTraits().add(viceTrait);
    }

    public void addOrUpdateVirtueTrait(Virtue virtue) {
        VirtueTrait virtueTrait = new VirtueTrait();
        
        virtueTrait.setType(Constants.CHARACTER_VIRTUE);
        virtueTrait.setVirtue(virtue);
        virtueTrait.setOrdinal(0L);

        for (VirtueTrait trait : character.getVirtueTraits()) {
            if (trait.getOrdinal().equals(virtueTrait.getOrdinal())) {
                virtueTrait.setId(trait.getId());
                character.getVirtueTraits().set(character.getVirtueTraits().indexOf(trait), virtueTrait);
                return;
            }
        }

        virtueTrait.setId(helper.getLastId(VirtueTrait.class), character.getVirtueTraits().size());

        character.getVirtueTraits().add(virtueTrait);
    }

    public Entry addOrUpdateEntry(String key, String type, String value) {
        Entry entry = new Entry()
            .setKey(key)
            .setType(type)
            .setValue(value);

        entry.setId(helper.getLastId(Entry.class), character.getEntries().size());

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

    public Entry addOrUpdateEntry(String key, Integer change) {
        Entry entry = new Entry().setKey(key).setType(Constants.FIELD_TYPE_INTEGER).setValue(change);

        entry.setId(helper.getLastId(Entry.class), character.getEntries().size());

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

        for (String skill : context.getResources().getStringArray(idArray)) {
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
        if (character.getSubterfuge() != null) {
            entries.add(character.getSubterfuge());
        }

        return getStatBlock(entries);
    }

    private String getStatBlock(ArrayList<Entry> stats) {
        StringBuilder builder = new StringBuilder();

        Iterator<Entry> iterator = stats.iterator();

        while (iterator.hasNext()) {
            Entry entry = iterator.next();

            if (entry != null && entry.getType().equalsIgnoreCase(Constants.FIELD_TYPE_INTEGER)
                    && Integer.valueOf(entry.getValue()) > 0) {
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
        // FIXME Added to prevent a crash on the character viewer. May, no, WILL be axed later.
        addOrUpdateEntry(Constants.CHARACTER_EXPERIENCE, Constants.FIELD_TYPE_INTEGER, String.valueOf(0));

        character.setId(helper.getLastId(Character.class));

        helper.save(character);

        setupNewCharacter();
    }

    public int calculateDefense() {
        int defense = Math.min(Integer.valueOf(character.getDexterity().getValue()),
                Integer.valueOf(character.getWits().getValue()));

        addOrUpdateEntry(Constants.TRAIT_DERIVED_DEFENSE, Constants.FIELD_TYPE_INTEGER,
                String.valueOf(defense));

        return defense;
    }

    public int calculateMorality() {
        addOrUpdateEntry(Constants.TRAIT_MORALITY, Constants.FIELD_TYPE_INTEGER,
                String.valueOf(Constants.TRAIT_MORALITY_DEFAULT));

        return Constants.TRAIT_MORALITY_DEFAULT;
    }

    public int calculateHealth() {
        int health = Integer.valueOf(character.getStamina().getValue()) +
                Constants.TRAIT_SIZE_DEFAULT;

        addOrUpdateEntry(Constants.TRAIT_DERIVED_HEALTH, Constants.FIELD_TYPE_INTEGER,
                String.valueOf(health));

        return health;
    }

    public int calculateInitiative() {
        int initiative = Integer.valueOf(character.getComposure().getValue()) +
                Integer.valueOf(character.getDexterity().getValue());

        addOrUpdateEntry(Constants.TRAIT_DERIVED_INITIATIVE, Constants.FIELD_TYPE_INTEGER,
                String.valueOf(initiative));

        return initiative;
    }

    public int calculateSpeed() {
        int speed = Integer.valueOf(character.getStrength().getValue()) +
                Integer.valueOf(character.getDexterity().getValue());

        addOrUpdateEntry(Constants.TRAIT_DERIVED_SPEED, Constants.FIELD_TYPE_INTEGER,
                String.valueOf(speed));

        return speed;
    }

    public int calculateWillpower() {
        int willpower = Integer.valueOf(character.getResolve().getValue()) +
                Integer.valueOf(character.getComposure().getValue());

        addOrUpdateEntry(Constants.TRAIT_DERIVED_WILLPOWER, Constants.FIELD_TYPE_INTEGER,
                String.valueOf(willpower));

        return willpower;
    }

    public int countSpecialties() {
        int result = 0;

        for (Entry entry : character.getEntries()) {
            if (entry.getSecondaryData() != null &&
                entry.getSecondaryData().getKey().equalsIgnoreCase(Constants.SKILL_SPECIALTY)) {
                result++;
            }
        }

        return result;
    }

    public Entry addSpecialty(String key, String specialtyName) {
        for (Entry entry : character.getEntries()) {
            if (entry.getKey() != null &&
                entry.getKey().equalsIgnoreCase(key)) {

                Entry specialty = new Entry();
                specialty.setId(helper.getLastId(Entry.class), character.getEntries().size());
                specialty.setKey(Constants.SKILL_SPECIALTY);
                specialty.setType(Constants.FIELD_TYPE_STRING);
                specialty.setValue(specialtyName);

                if (entry.getExtras() == null) {
                    entry.setExtras(new RealmList<Entry>());
                }

                entry.getExtras().add(specialty);
//                entry.setSecondaryData(specialty);

                return specialty;
            }
        }
        return null;
    }

    public void removeSpecialty(String key) {
        for (Entry entry : character.getEntries()) {
            if (entry.getKey() != null &&
                entry.getKey().equalsIgnoreCase(key)) {

                entry.setSecondaryData(null);
                break;
            }
        }
    }

    public void removeSpecialty(String key, String specialty) {
        for (Entry entry : character.getEntries()) {
            if (entry.getKey() != null &&
                entry.getKey().equalsIgnoreCase(key)) {

                Entry entryToRemove = null;

                for (Entry extra : entry.getExtras()) {
                    if (extra.getKey() != null
                        && extra.getKey().equalsIgnoreCase(specialty)) {
                        entryToRemove = extra;
                        break;
                    }
                }

                if (entryToRemove != null) {
                    entry.getExtras().remove(entryToRemove);
                }

                break;
            }
        }
    }

    public RealmList<Entry> getSpecialties(String key) {
        for (Entry entry : character.getEntries()) {
            if (entry.getKey() != null &&
                entry.getKey().equalsIgnoreCase(key)) {

                if (entry.getExtras() == null) {
                    entry.setExtras(new RealmList<Entry>());
                }

                return entry.getExtras();
            }
        }
        return null;
    }
}
