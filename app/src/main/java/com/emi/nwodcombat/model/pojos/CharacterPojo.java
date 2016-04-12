package com.emi.nwodcombat.model.pojos;

import java.util.ArrayList;

/**
 * Created by emiliano.desantis on 05/04/2016.
 */
public class CharacterPojo {
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

    private ArrayList<Long> demeanors;
    private ArrayList<Long> natures;
    private ArrayList<Long> vices;
    private ArrayList<Long> virtues;

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

    public ArrayList<Long> getDemeanors() {
        return demeanors;
    }

    public void setDemeanors(ArrayList<Long> demeanors) {
        this.demeanors = demeanors;
    }

    public ArrayList<Long> getNatures() {
        return natures;
    }

    public void setNatures(ArrayList<Long> natures) {
        this.natures = natures;
    }

    public ArrayList<Long> getVices() {
        return vices;
    }

    public void setVices(ArrayList<Long> vices) {
        this.vices = vices;
    }

    public ArrayList<Long> getVirtues() {
        return virtues;
    }

    public void setVirtues(ArrayList<Long> virtues) {
        this.virtues = virtues;
    }
}
