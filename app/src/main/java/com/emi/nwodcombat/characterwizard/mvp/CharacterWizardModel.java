package com.emi.nwodcombat.characterwizard.mvp;

import android.content.Context;
import android.content.SharedPreferences;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.application.NwodCombatApplication;
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
import com.emi.nwodcombat.tools.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/05/2016.
 */
public class CharacterWizardModel implements SpecialtiesModel {

    static private CharacterWizardModel instance;

    private final RealmHelper helper;
    private SharedPreferences preferences;
    private Character character;
    private Long characterId;

    private CharacterWizardModel() {
        helper = RealmHelper.getInstance(NwodCombatApplication.getAppContext());

        createCharacter();

        setUpDefaultValues();
    }

    private void setUpDefaultValues() {
        addEntry(Constants.NAMESPACE_ATTRIBUTE, Constants.ATTR_INT, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(Constants.ABSOLUTE_MINIMUM_ATTR));
        addEntry(Constants.NAMESPACE_ATTRIBUTE, Constants.ATTR_WIT, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(Constants.ABSOLUTE_MINIMUM_ATTR));
        addEntry(Constants.NAMESPACE_ATTRIBUTE, Constants.ATTR_RES, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(Constants.ABSOLUTE_MINIMUM_ATTR));
        addEntry(Constants.NAMESPACE_ATTRIBUTE, Constants.ATTR_STR, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(Constants.ABSOLUTE_MINIMUM_ATTR));
        addEntry(Constants.NAMESPACE_ATTRIBUTE, Constants.ATTR_DEX, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(Constants.ABSOLUTE_MINIMUM_ATTR));
        addEntry(Constants.NAMESPACE_ATTRIBUTE, Constants.ATTR_STA, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(Constants.ABSOLUTE_MINIMUM_ATTR));
        addEntry(Constants.NAMESPACE_ATTRIBUTE, Constants.ATTR_PRE, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(Constants.ABSOLUTE_MINIMUM_ATTR));
        addEntry(Constants.NAMESPACE_ATTRIBUTE, Constants.ATTR_MAN, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(Constants.ABSOLUTE_MINIMUM_ATTR));
        addEntry(Constants.NAMESPACE_ATTRIBUTE, Constants.ATTR_COM, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(Constants.ABSOLUTE_MINIMUM_ATTR));

        addEntry(Constants.CHARACTER_EXPERIENCE, Constants.CHARACTER_EXPERIENCE, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(0));
    }

    static public CharacterWizardModel getInstance() {
        if (instance == null) {
            instance = new CharacterWizardModel();
        }
        return instance;
    }

    RealmResults<Virtue> getVirtues() {
        return helper.getList(Virtue.class);
    }

    RealmResults<Vice> getVices() {
        return helper.getList(Vice.class);
    }

    RealmResults<Nature> getNatures() {
        return helper.getList(Nature.class);
    }

    RealmResults<Demeanor> getDemeanors() {
        return helper.getList(Demeanor.class);
    }

    boolean isCheating() {
        return getPreferences().getBoolean(Constants.SETTING_CHEAT, false);
    }

    /**
     * Method for returning singleton instance of SharedPreferences
     * @return Preferences object containing app settings
     */
    private SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = NwodCombatApplication.getAppContext()
                .getSharedPreferences(Constants.TAG_SHARED_PREFS,
                    Context.MODE_PRIVATE);
        }
        return preferences;
    }

    void setupNewCharacter() {
        character = new Character();
    }

    void addOrUpdateDemeanorTrait(Demeanor demeanor) {

        // This object will be posted for handling by the model
        DemeanorTrait demeanorTrait = new DemeanorTrait();

        demeanorTrait.setType(Constants.CHARACTER_DEMEANOR);
        demeanorTrait.setDemeanor(demeanor);

        // This should change depending on which demeanor we're
        // editing: first, second, third, or whatever
        demeanorTrait.setOrdinal(0L);

        helper.updateDemeanorTrait(characterId, demeanorTrait);
    }

    void addOrUpdateNatureTrait(Nature nature) {
        NatureTrait natureTrait = new NatureTrait();
        natureTrait.setType(Constants.CHARACTER_NATURE);
        natureTrait.setNature(nature);
        natureTrait.setOrdinal(0L);

        helper.updateNatureTrait(characterId, natureTrait);
    }

    void addOrUpdateViceTrait(Vice vice) {
        ViceTrait viceTrait = new ViceTrait();
        viceTrait.setType(Constants.CHARACTER_VICE);
        viceTrait.setVice(vice);
        viceTrait.setOrdinal(0L);

        helper.updateViceTrait(characterId, viceTrait);
    }

    void addOrUpdateVirtueTrait(Virtue virtue) {
        VirtueTrait virtueTrait = new VirtueTrait();
        
        virtueTrait.setType(Constants.CHARACTER_VIRTUE);
        virtueTrait.setVirtue(virtue);
        virtueTrait.setOrdinal(0L);

        helper.updateVirtueTrait(characterId, virtueTrait);
    }

    private int getPointsSpentOnAttributes(int idArray) {
        return getPointsSpent(idArray, Constants.ATTR_PTS_TERTIARY, 1);
    }

    int getPointsSpentOnAttrMental() {
        return getPointsSpentOnAttributes(R.array.attributes_mental);
    }

    int getPointsSpentOnAttrPhysical() {
        return getPointsSpentOnAttributes(R.array.attributes_physical);
    }

    int getPointsSpentOnAttrSocial() {
        return getPointsSpentOnAttributes(R.array.attributes_social);
    }

    private int getPointsSpentOnSkills(int idArray) {
        return getPointsSpent(idArray, 0, 0);
    }

    int getPointsSpentOnMentalSkills() {
        return getPointsSpentOnSkills(R.array.skills_mental);
    }

    int getPointsSpentOnPhysicalSkills() {
        return getPointsSpentOnSkills(R.array.skills_physical);
    }

    int getPointsSpentOnSocialSkills() {
        return getPointsSpentOnSkills(R.array.skills_social);
    }

    private int getPointsSpent(int idArray, int takeawayValue, int defaultValue) {
        int result = 0 - takeawayValue;

        for (String skill : NwodCombatApplication.getAppContext().
            getResources().getStringArray(idArray)) {
            result += findEntryValue(skill, defaultValue);
        }

        return result;
    }

    Integer findEntryValue(String constant, int defaultValue) {
        try {
            String value = helper.getEntryValue(character.getId(), constant);

            return Integer.valueOf(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    String getMentalAttrSummary() {
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(character.getIntelligence());
        entries.add(character.getWits());
        entries.add(character.getResolve());

        return getStatBlock(entries);
    }

    String getPhysicalAttrSummary() {
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(character.getStrength());
        entries.add(character.getDexterity());
        entries.add(character.getStamina());

        return getStatBlock(entries);
    }

    String getSocialAttrSummary() {
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(character.getIntelligence());
        entries.add(character.getWits());
        entries.add(character.getResolve());

        return getStatBlock(entries);
    }

    String getMentalSkillsSummary() {
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

    String getPhysicalSkillsSummary() {
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

    String getSocialSkillsSummary() {
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

    String getSpecialtiesSummary() {
        StringBuilder builder = new StringBuilder();
        /**
         * Pseudocode:
         * - Iterate through skills
         * - If skill has a specialty:
         * ---> If there was an entry already on the string builder, add a comma
         * ---> Add skill name
         * ---> Add skill specialty between parenthesis
         */

        for (Entry entry : character.getEntries()) {
            if (entry.getExtras() != null) {
                for (Entry extra : entry.getExtras()) {
                    // If skill has specialties
                    if (extra.getKey().equalsIgnoreCase(Constants.SKILL_SPECIALTY)) {

                        // If there was an entry already on the string builder, add a comma
                        if (builder.length() != 0) {
                            builder.append(", ");
                        }

                        // Add skill name and specialty between parentheses
                        builder.append(String.format("%s (%s)", entry.getKey(), extra.getValue()));
                    }
                }
            }
        }

        return builder.toString();
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

    void save() {
        helper.saveCharacter(character);

        setupNewCharacter();
    }

    int calculateDefense() {
        int defense = Math.min(Integer.valueOf(character.getDexterity().getValue()),
                Integer.valueOf(character.getWits().getValue()));

        addEntry(Constants.NAMESPACE_ADVANTAGE, Constants.TRAIT_DERIVED_DEFENSE, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(defense));

        return defense;
    }

    int calculateMorality() {
        addEntry(Constants.NAMESPACE_ADVANTAGE, Constants.TRAIT_MORALITY, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(Constants.TRAIT_MORALITY_DEFAULT));

        return Constants.TRAIT_MORALITY_DEFAULT;
    }

    int calculateHealth() {
        int health = Integer.valueOf(character.getStamina().getValue()) +
                Constants.TRAIT_SIZE_DEFAULT;

        addEntry(Constants.NAMESPACE_ADVANTAGE, Constants.TRAIT_DERIVED_HEALTH, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(health));

        return health;
    }

    int calculateInitiative() {
        int initiative = Integer.valueOf(character.getComposure().getValue()) +
                Integer.valueOf(character.getDexterity().getValue());

        addEntry(Constants.NAMESPACE_ADVANTAGE, Constants.TRAIT_DERIVED_INITIATIVE, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(initiative));

        return initiative;
    }

    int calculateSpeed() {
        int speed = Integer.valueOf(character.getStrength().getValue()) +
                Integer.valueOf(character.getDexterity().getValue());

        addEntry(Constants.NAMESPACE_ADVANTAGE, Constants.TRAIT_DERIVED_SPEED, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(speed));

        return speed;
    }

    int calculateWillpower() {
        int willpower = Integer.valueOf(character.getResolve().getValue()) +
                Integer.valueOf(character.getComposure().getValue());

        addEntry(Constants.NAMESPACE_ADVANTAGE, Constants.TRAIT_DERIVED_WILLPOWER, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(willpower));

        return willpower;
    }

    @Override
    public Entry addSpecialty(String key, String specialtyName) {
        Entry specialty = Entry.newInstance(helper.getLastId(Entry.class));

        specialty.setKey(Constants.SKILL_SPECIALTY);
        specialty.setType(Constants.FIELD_TYPE_STRING);
        specialty.setValue(specialtyName);

        return helper.addSpecialty(character.getId(), key, specialty);
    }

    @Override
    public void removeSpecialty(String key, String specialty) {
        helper.removeSpecialty(character.getId(), key, specialty);
    }

//    private RealmList<Entry> getAllSpecialties() {
//        RealmList<Entry> specialties = new RealmList<>();
//
//        for (Entry entry : character.getEntries()) {
//            if (entry.getExtras() != null) {
//                for (Entry extra : entry.getExtras()) {
//                    // If skill has specialties
//                    if (extra.getKey().equalsIgnoreCase(Constants.SKILL_SPECIALTY)) {
//                        specialties.add(extra);
//                    }
//                }
//            }
//        }
//
//        return specialties;
//    }

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

    @Override
    public int countSpecialties() {
        int result = 0;

        for (Entry entry : character.getEntries()) {
            if (entry.getKey() != null) {
                if (entry.getExtras() != null) {
                    for (Entry extra : entry.getExtras()) {
                        if (extra.getKey().equalsIgnoreCase(Constants.SKILL_SPECIALTY)) {
                            result++;
                        }
                    }
                }
            }
        }

        return result;
    }

    public Character getCharacter() {
        return character;
    }

    void refreshCharacter() {
        character = helper.get(Character.class, characterId);
    }

    RealmList<Entry> getEntries() {
        return character.getEntries();
    }

    Entry getEntry(String name) {
        return helper.get(Entry.class, name);
    }

    void deleteEntry(String name) {
        helper.delete(Entry.class, name);
    }

    void deleteCharacter() {
        helper.deleteCharacter(characterId);
        createCharacter();
    }

    private void createCharacter() {
        characterId = helper.getLastId(Character.class);
        character = (Character) helper.createObject(Character.class, characterId);
    }

    boolean hasEntry(String name) {
        Entry entry = helper.get(Entry.class, name);

        return entry != null;
    }

    Entry addOrUpdateEntry(String namespace, String key, Integer change) {
        if (!hasEntry(key)) {
            return addEntry(namespace, key, Constants.FIELD_TYPE_INTEGER, String.valueOf(change));
        } else {
            return updateEntry(key, String.valueOf(change));
        }
    }

    Entry addOrUpdateEntry(String namespace, String key, String value) {
        if (!hasEntry(key)) {
            return addEntry(namespace, key, Constants.FIELD_TYPE_STRING, value);
        } else {
            return updateEntry(key, value);
        }
    }

    Entry addEntry(String namespace, String key, String type, String value) {
        Entry entry = new Entry.Builder(helper.getLastId(Entry.class), namespace, key, value, type)
            .build();

        helper.addEntry(characterId, entry);

        return entry;
    }

    Entry updateEntry(String key, String value) {
        return helper.updateEntry(characterId, key, value);
    }

    List<Entry> getMeritsSummary() {
        return helper.getAllCharacterMerits(characterId);
    }
}
