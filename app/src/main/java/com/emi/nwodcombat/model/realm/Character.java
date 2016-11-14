package com.emi.nwodcombat.model.realm;

import android.support.annotation.NonNull;

import com.emi.nwodcombat.model.realm.wrappers.DemeanorTrait;
import com.emi.nwodcombat.model.realm.wrappers.NatureTrait;
import com.emi.nwodcombat.model.realm.wrappers.ViceTrait;
import com.emi.nwodcombat.model.realm.wrappers.VirtueTrait;
import com.emi.nwodcombat.tools.ArrayHelper;
import com.emi.nwodcombat.tools.Constants;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by emiliano.desantis on 05/04/2016.
 * Corrections to apply:
 * - One method per value to retrieve from Entry list
 */
public class Character extends RealmObject {
    @PrimaryKey
    @Required
    private Long id;

    private RealmList<DemeanorTrait> demeanorTraits = new RealmList<>();
    private RealmList<NatureTrait> natureTraits = new RealmList<>();
    private RealmList<ViceTrait> viceTraits = new RealmList<>();
    private RealmList<VirtueTrait> virtueTraits = new RealmList<>();
    private RealmList<Entry> entries = new RealmList<>();

    private boolean isComplete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RealmList<Entry> getEntries() {
        return entries;
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

    private Entry getEntry(@NonNull String code) {
        return ArrayHelper.findEntry(entries, code);
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
        return getEntry(Constants.CHARACTER_EXPERIENCE);
    }

    public Entry getIntelligence() {
        return getEntry(Constants.ATTR_INT);
    }

    public Entry getWits() {
        return getEntry(Constants.ATTR_WIT);
    }

    public Entry getResolve() {
        return getEntry(Constants.ATTR_RES);
    }

    public Entry getStrength() {
        return getEntry(Constants.ATTR_STR);
    }

    public Entry getDexterity() {
        return getEntry(Constants.ATTR_DEX);
    }

    public Entry getStamina() {
        return getEntry(Constants.ATTR_STA);
    }

    public Entry getPresence() {
        return getEntry(Constants.ATTR_PRE);
    }

    public Entry getManipulation() {
        return getEntry(Constants.ATTR_MAN);
    }

    public Entry getComposure() {
        return getEntry(Constants.ATTR_COM);
    }

    public Entry getAcademics() {
        return getEntry(Constants.SKILL_ACADEMICS);
    }

    public Entry getComputer() {
        return getEntry(Constants.SKILL_COMPUTER);
    }

    public Entry getCrafts() {
        return getEntry(Constants.SKILL_CRAFTS);
    }

    public Entry getInvestigation() {
        return getEntry(Constants.SKILL_INVESTIGATION);
    }

    public Entry getMedicine() {
        return getEntry(Constants.SKILL_MEDICINE);
    }

    public Entry getOccult() {
        return getEntry(Constants.SKILL_OCCULT);
    }

    public Entry getPolitics() {
        return getEntry(Constants.SKILL_POLITICS);
    }

    public Entry getScience() {
        return getEntry(Constants.SKILL_SCIENCE);
    }

    public Entry getAthletics() {
        return getEntry(Constants.SKILL_ATHLETICS);
    }

    public Entry getBrawl() {
        return getEntry(Constants.SKILL_BRAWL);
    }

    public Entry getDrive() {
        return getEntry(Constants.SKILL_DRIVE);
    }

    public Entry getFirearms() {
        return getEntry(Constants.SKILL_FIREARMS);
    }

    public Entry getLarceny() {
        return getEntry(Constants.SKILL_LARCENY);
    }

    public Entry getStealth() {
        return getEntry(Constants.SKILL_STEALTH);
    }

    public Entry getSurvival() {
        return getEntry(Constants.SKILL_SURVIVAL);
    }

    public Entry getWeaponry() {
        return getEntry(Constants.SKILL_WEAPONRY);
    }

    public Entry getAnimalKen() {
        return getEntry(Constants.SKILL_ANIMAL_KEN);
    }

    public Entry getEmpathy() {
        return getEntry(Constants.SKILL_EMPATHY);
    }

    public Entry getExpression() {
        return getEntry(Constants.SKILL_EXPRESSION);
    }

    public Entry getIntimidation() {
        return getEntry(Constants.SKILL_INTIMIDATION);
    }

    public Entry getPersuasion() {
        return getEntry(Constants.SKILL_PERSUASION);
    }

    public Entry getSocialize() {
        return getEntry(Constants.SKILL_SOCIALIZE);
    }

    public Entry getStreetwise() {
        return getEntry(Constants.SKILL_STREETWISE);
    }

    public Entry getSubterfuge() {
        return getEntry(Constants.SKILL_SUBTERFUGE);
    }

    public Entry getDefense() {
        return getEntry(Constants.TRAIT_DERIVED_DEFENSE);
    }

    public Entry getHealth() {
        return getEntry(Constants.TRAIT_DERIVED_HEALTH);
    }

    public Entry getInitiative() {
        return getEntry(Constants.TRAIT_DERIVED_INITIATIVE);
    }

    public Entry getMorality() {
        return getEntry(Constants.TRAIT_MORALITY);
    }

    public Entry getSpeed() {
        return getEntry(Constants.TRAIT_DERIVED_SPEED);
    }

    public Entry getWillpower() {
        return getEntry(Constants.TRAIT_DERIVED_WILLPOWER);
    }

    public RealmList<DemeanorTrait> getDemeanorTraits() {
        return demeanorTraits;
    }

    public RealmList<NatureTrait> getNatureTraits() {
        return natureTraits;
    }

    public RealmList<ViceTrait> getViceTraits() {
        return viceTraits;
    }

    public RealmList<VirtueTrait> getVirtueTraits() {
        return virtueTraits;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void cascadeDelete() {
        entries.deleteAllFromRealm(); // The cascade part
        deleteFromRealm(); // delete this object
    }

}
