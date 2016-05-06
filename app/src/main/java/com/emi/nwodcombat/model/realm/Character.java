package com.emi.nwodcombat.model.realm;

import android.support.annotation.NonNull;

import com.emi.nwodcombat.tools.ArrayHelper;
import com.emi.nwodcombat.utils.Constants;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emiliano.desantis on 05/04/2016.
 * Corrections to apply:
 * - One method per value to retrieve from Entry list
 */
public class Character extends RealmObject {
    @PrimaryKey
    private Long id;

    private RealmList<Entry> entries = new RealmList<>();
    private RealmList<Nature> natures = new RealmList<>();
    private RealmList<Demeanor> demeanors = new RealmList<>();
    private RealmList<Vice> vices = new RealmList<>();
    private RealmList<Virtue> virtues = new RealmList<>();

    public RealmList<Nature> getNatures() {
        return natures;
    }

    public void setNatures(RealmList<Nature> natures) {
        this.natures = natures;
    }

    public RealmList<Demeanor> getDemeanors() {
        return demeanors;
    }

    public void setDemeanors(RealmList<Demeanor> demeanors) {
        this.demeanors = demeanors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RealmList<Vice> getVices() {
        return vices;
    }

    public void setVices(RealmList<Vice> vices) {
        this.vices = vices;
    }

    public RealmList<Virtue> getVirtues() {
        return virtues;
    }

    public void setVirtues(RealmList<Virtue> virtues) {
        this.virtues = virtues;
    }

    public RealmList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(RealmList<Entry> entries) {
        this.entries = entries;
    }

    public int getValue(@NonNull String code) {
        String result = ArrayHelper.findValue(entries, code);

        return result != null ? Integer.valueOf(result) : 0;
    }

    public Object getValue(@NonNull String code, Class klass) {
        if (klass.equals(Integer.class)) {
            String result = ArrayHelper.findValue(entries, code);

            return result != null ? Integer.valueOf(result) : 0;
        } else if (klass.equals(String.class)) {
            return ArrayHelper.findValue(entries, code);
        }
        return null;
    }

    public Entry getEntry(@NonNull String code) {
        return ArrayHelper.findEntry(entries, code);
    }

    public String getFirstDemeanor() {
        return getDemeanors().first().getName();
    }

    public String getFirstNature() {
        return getNatures().first().getName();
    }

    public String getFirstVice() {
        return getVices().first().getName();
    }

    public String getFirstVirtue() {
        return getVirtues().first().getName();
    }

    public String getName() {
        return getValue(Constants.CHARACTER_NAME, String.class).toString();
    }

    public String getPlayer() {
        return getValue(Constants.CHARACTER_PLAYER, String.class).toString();
    }

    public String getConcept() {
        return getValue(Constants.CHARACTER_CONCEPT, String.class).toString();
    }

    public Entry getExperience() {
        return ArrayHelper.findEntry(entries, Constants.CHARACTER_EXPERIENCE);
    }

    public Entry getIntelligence() {
        return ArrayHelper.findEntry(entries, Constants.ATTR_INT);
    }

    public Entry getWits() {
        return ArrayHelper.findEntry(entries, Constants.ATTR_WIT);
    }

    public Entry getResolve() {
        return ArrayHelper.findEntry(entries, Constants.ATTR_RES);
    }

    public Entry getStrength() {
        return ArrayHelper.findEntry(entries, Constants.ATTR_STR);
    }

    public Entry getDexterity() {
        return ArrayHelper.findEntry(entries, Constants.ATTR_DEX);
    }

    public Entry getStamina() {
        return ArrayHelper.findEntry(entries, Constants.ATTR_STA);
    }

    public Entry getPresence() {
        return ArrayHelper.findEntry(entries, Constants.ATTR_PRE);
    }

    public Entry getManipulation() {
        return ArrayHelper.findEntry(entries, Constants.ATTR_MAN);
    }

    public Entry getComposure() {
        return ArrayHelper.findEntry(entries, Constants.ATTR_COM);
    }

    public Entry getAcademics() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_ACADEMICS);
    }

    public Entry getComputer() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_COMPUTER);
    }

    public Entry getCrafts() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_CRAFTS);
    }

    public Entry getInvestigation() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_INVESTIGATION);
    }

    public Entry getMedicine() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_MEDICINE);
    }

    public Entry getOccult() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_OCCULT);
    }

    public Entry getPolitics() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_POLITICS);
    }

    public Entry getScience() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_SCIENCE);
    }

    public Entry getAthletics() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_ATHLETICS);
    }

    public Entry getBrawl() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_BRAWL);
    }

    public Entry getDrive() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_DRIVE);
    }

    public Entry getFirearms() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_FIREARMS);
    }

    public Entry getLarceny() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_LARCENY);
    }

    public Entry getStealth() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_STEALTH);
    }

    public Entry getSurvival() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_SURVIVAL);
    }

    public Entry getWeaponry() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_WEAPONRY);
    }

    public Entry getAnimalKen() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_ANIMAL_KEN);
    }

    public Entry getEmpathy() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_EMPATHY);
    }

    public Entry getExpression() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_EXPRESSION);
    }

    public Entry getIntimidation() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_INTIMIDATION);
    }

    public Entry getPersuasion() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_PERSUASION);
    }

    public Entry getSocialize() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_SOCIALIZE);
    }

    public Entry getStreetwise() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_STREETWISE);
    }

    public Entry getSubterfuge() {
        return ArrayHelper.findEntry(entries, Constants.SKILL_SUBTERFUGE);
    }

    public Entry getDefense() {
        return ArrayHelper.findEntry(entries, Constants.TRAIT_DERIVED_DEFENSE);
    }

    public Entry getHealth() {
        return ArrayHelper.findEntry(entries, Constants.TRAIT_DERIVED_HEALTH);
    }

    public Entry getInitiative() {
        return ArrayHelper.findEntry(entries, Constants.TRAIT_DERIVED_INITIATIVE);
    }

    public Entry getMorality() {
        return ArrayHelper.findEntry(entries, Constants.TRAIT_MORALITY);
    }

    public Entry getSpeed() {
        return ArrayHelper.findEntry(entries, Constants.TRAIT_DERIVED_SPEED);
    }

    public Entry getWillpower() {
        return ArrayHelper.findEntry(entries, Constants.TRAIT_DERIVED_WILLPOWER);
    }
}
