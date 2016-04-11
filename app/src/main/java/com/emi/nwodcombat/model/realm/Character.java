package com.emi.nwodcombat.model.realm;

import com.emi.nwodcombat.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emiliano.desantis on 05/04/2016.
 */
public class Character extends RealmObject {
    @PrimaryKey
    private Long id;

    private String name;
    private String player;
    private String concept;

    private int intelligence;
    private int wits;
    private int resolve;
    private int strength;
    private int dexterity;
    private int stamina;
    private int presence;
    private int manipulation;
    private int composure;
    
    private int academics;
    private int computer;
    private int crafts;
    private int investigation;
    private int medicine;
    private int occult;
    private int politics;
    private int science;
    private int athletics;
    private int brawl;
    private int drive;
    private int firearms;
    private int larceny;
    private int stealth;
    private int survival;
    private int weaponry;
    private int animalKen;
    private int empathy;
    private int expression;
    private int intimidation;
    private int persuasion;
    private int socialize;
    private int streetwise;
    private int subterfuge;

    private int health;
    private int morality;
    private int potency;
    private int willpower;
    private int willpowerReserve;

    private RealmList<PersonalityArchetype> personalityTraits;
    private RealmList<Vice> vices;
    private RealmList<Virtue> virtues;

    public RealmList<PersonalityArchetype> getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(RealmList<PersonalityArchetype> personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public int getAcademics() {
        return academics;
    }

    public void setAcademics(int academics) {
        this.academics = academics;
    }

    public int getAnimalKen() {
        return animalKen;
    }

    public void setAnimalKen(int animalKen) {
        this.animalKen = animalKen;
    }

    public int getAthletics() {
        return athletics;
    }

    public void setAthletics(int athletics) {
        this.athletics = athletics;
    }

    public int getBrawl() {
        return brawl;
    }

    public void setBrawl(int brawl) {
        this.brawl = brawl;
    }

    public int getComposure() {
        return composure;
    }

    public void setComposure(int composure) {
        this.composure = composure;
    }

    public int getComputer() {
        return computer;
    }

    public void setComputer(int computer) {
        this.computer = computer;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public int getCrafts() {
        return crafts;
    }

    public void setCrafts(int crafts) {
        this.crafts = crafts;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getDrive() {
        return drive;
    }

    public void setDrive(int drive) {
        this.drive = drive;
    }

    public int getEmpathy() {
        return empathy;
    }

    public void setEmpathy(int empathy) {
        this.empathy = empathy;
    }

    public int getExpression() {
        return expression;
    }

    public void setExpression(int expression) {
        this.expression = expression;
    }

    public int getFirearms() {
        return firearms;
    }

    public void setFirearms(int firearms) {
        this.firearms = firearms;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getIntimidation() {
        return intimidation;
    }

    public void setIntimidation(int intimidation) {
        this.intimidation = intimidation;
    }

    public int getInvestigation() {
        return investigation;
    }

    public void setInvestigation(int investigation) {
        this.investigation = investigation;
    }

    public int getLarceny() {
        return larceny;
    }

    public void setLarceny(int larceny) {
        this.larceny = larceny;
    }

    public int getManipulation() {
        return manipulation;
    }

    public void setManipulation(int manipulation) {
        this.manipulation = manipulation;
    }

    public int getMedicine() {
        return medicine;
    }

    public void setMedicine(int medicine) {
        this.medicine = medicine;
    }

    public int getMorality() {
        return morality;
    }

    public void setMorality(int morality) {
        this.morality = morality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOccult() {
        return occult;
    }

    public void setOccult(int occult) {
        this.occult = occult;
    }

    public int getPersuasion() {
        return persuasion;
    }

    public void setPersuasion(int persuasion) {
        this.persuasion = persuasion;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPolitics() {
        return politics;
    }

    public void setPolitics(int politics) {
        this.politics = politics;
    }

    public int getPotency() {
        return potency;
    }

    public void setPotency(int potency) {
        this.potency = potency;
    }

    public int getPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }

    public int getResolve() {
        return resolve;
    }

    public void setResolve(int resolve) {
        this.resolve = resolve;
    }

    public int getScience() {
        return science;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public int getSocialize() {
        return socialize;
    }

    public void setSocialize(int socialize) {
        this.socialize = socialize;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStealth() {
        return stealth;
    }

    public void setStealth(int stealth) {
        this.stealth = stealth;
    }

    public int getStreetwise() {
        return streetwise;
    }

    public void setStreetwise(int streetwise) {
        this.streetwise = streetwise;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getSubterfuge() {
        return subterfuge;
    }

    public void setSubterfuge(int subterfuge) {
        this.subterfuge = subterfuge;
    }

    public int getSurvival() {
        return survival;
    }

    public void setSurvival(int survival) {
        this.survival = survival;
    }

    public int getWeaponry() {
        return weaponry;
    }

    public void setWeaponry(int weaponry) {
        this.weaponry = weaponry;
    }

    public int getWillpower() {
        return willpower;
    }

    public void setWillpower(int willpower) {
        this.willpower = willpower;
    }

    public int getWillpowerReserve() {
        return willpowerReserve;
    }

    public void setWillpowerReserve(int willpowerReserve) {
        this.willpowerReserve = willpowerReserve;
    }

    public int getWits() {
        return wits;
    }

    public void setWits(int wits) {
        this.wits = wits;
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

    // TODO Finish this
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        // Attributes
        jsonObject.put(Constants.ATTR_INT, getIntelligence());
        jsonObject.put(Constants.ATTR_WIT, getWits());
        jsonObject.put(Constants.ATTR_RES, getResolve());
        jsonObject.put(Constants.ATTR_STR, getStrength());
        jsonObject.put(Constants.ATTR_DEX, getDexterity());
        jsonObject.put(Constants.ATTR_STA, getStamina());
        jsonObject.put(Constants.ATTR_PRE, getPresence());
        jsonObject.put(Constants.ATTR_MAN, getManipulation());
        jsonObject.put(Constants.ATTR_COM, getComposure());

        // Skills
        jsonObject.put(Constants.SKILL_ACADEMICS, getAcademics());
        jsonObject.put(Constants.SKILL_ANIMAL_KEN, getAnimalKen());
        jsonObject.put(Constants.SKILL_ATHLETICS, getAthletics());
        jsonObject.put(Constants.SKILL_BRAWL, getBrawl());
        jsonObject.put(Constants.SKILL_COMPUTER, getComputer());
        jsonObject.put(Constants.SKILL_CRAFTS, getCrafts());
        jsonObject.put(Constants.SKILL_DRIVE, getDrive());
        jsonObject.put(Constants.SKILL_EMPATHY, getEmpathy());
        jsonObject.put(Constants.SKILL_EXPRESSION, getExpression());
        jsonObject.put(Constants.SKILL_FIREARMS, getFirearms());
        jsonObject.put(Constants.SKILL_INTIMIDATION, getIntimidation());
        jsonObject.put(Constants.SKILL_INVESTIGATION, getInvestigation());
        jsonObject.put(Constants.SKILL_LARCENY, getLarceny());
        jsonObject.put(Constants.SKILL_MEDICINE, getMedicine());
        jsonObject.put(Constants.SKILL_OCCULT, getOccult());
        jsonObject.put(Constants.SKILL_PERSUASION, getPersuasion());
        jsonObject.put(Constants.SKILL_POLITICS, getPolitics());
        jsonObject.put(Constants.SKILL_SCIENCE, getScience());
        jsonObject.put(Constants.SKILL_SOCIALIZE, getSocialize());
        jsonObject.put(Constants.SKILL_STEALTH, getStealth());
        jsonObject.put(Constants.SKILL_STREETWISE, getStreetwise());
        jsonObject.put(Constants.SKILL_SUBTERFUGE, getSubterfuge());
        jsonObject.put(Constants.SKILL_SURVIVAL, getSurvival());
        jsonObject.put(Constants.SKILL_WEAPONRY, getWeaponry());

        // Personal info
        jsonObject.put(Constants.CHARACTER_PLAYER, getPlayer());
        jsonObject.put(Constants.CHARACTER_NAME, getName());
        jsonObject.put(Constants.CHARACTER_CONCEPT, getConcept());

        // Combat traits
        jsonObject.put("Health", getHealth());
        jsonObject.put("Morality", getMorality());
        jsonObject.put("Potency", getPotency());
        jsonObject.put("Willpower", getWillpower());
        jsonObject.put("WillpowerReserve", getWillpowerReserve());

        for (PersonalityArchetype archetype : personalityTraits) {

        }

        return jsonObject;
    }
}
