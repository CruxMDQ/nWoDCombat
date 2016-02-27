package com.emi.nwodcombat.model.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "CHARACTER".
 */
public class Character {

    private Long id;
    /** Not-null value. */
    private String Name;
    /** Not-null value. */
    private String Player;
    /** Not-null value. */
    private String Concept;
    private int Intelligence;
    private int Wits;
    private int Resolve;
    private int Strength;
    private int Dexterity;
    private int Stamina;
    private int Presence;
    private int Manipulation;
    private int Composure;
    private Integer Academics;
    private Integer Computer;
    private Integer Crafts;
    private Integer Investigation;
    private Integer Medicine;
    private Integer Occult;
    private Integer Politics;
    private Integer Science;
    private Integer Athletics;
    private Integer Brawl;
    private Integer Drive;
    private Integer Firearms;
    private Integer Larceny;
    private Integer Survival;
    private Integer Weaponry;
    private Integer AnimalKen;
    private Integer Empathy;
    private Integer Expression;
    private Integer Intimidation;
    private Integer Persuasion;
    private Integer Socialize;
    private Integer Streetwise;
    private Integer Subterfuge;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Character() {
    }

    public Character(Long id) {
        this.id = id;
    }

    public Character(Long id, String Name, String Player, String Concept, int Intelligence, int Wits, int Resolve, int Strength, int Dexterity, int Stamina, int Presence, int Manipulation, int Composure, Integer Academics, Integer Computer, Integer Crafts, Integer Investigation, Integer Medicine, Integer Occult, Integer Politics, Integer Science, Integer Athletics, Integer Brawl, Integer Drive, Integer Firearms, Integer Larceny, Integer Survival, Integer Weaponry, Integer AnimalKen, Integer Empathy, Integer Expression, Integer Intimidation, Integer Persuasion, Integer Socialize, Integer Streetwise, Integer Subterfuge) {
        this.id = id;
        this.Name = Name;
        this.Player = Player;
        this.Concept = Concept;
        this.Intelligence = Intelligence;
        this.Wits = Wits;
        this.Resolve = Resolve;
        this.Strength = Strength;
        this.Dexterity = Dexterity;
        this.Stamina = Stamina;
        this.Presence = Presence;
        this.Manipulation = Manipulation;
        this.Composure = Composure;
        this.Academics = Academics;
        this.Computer = Computer;
        this.Crafts = Crafts;
        this.Investigation = Investigation;
        this.Medicine = Medicine;
        this.Occult = Occult;
        this.Politics = Politics;
        this.Science = Science;
        this.Athletics = Athletics;
        this.Brawl = Brawl;
        this.Drive = Drive;
        this.Firearms = Firearms;
        this.Larceny = Larceny;
        this.Survival = Survival;
        this.Weaponry = Weaponry;
        this.AnimalKen = AnimalKen;
        this.Empathy = Empathy;
        this.Expression = Expression;
        this.Intimidation = Intimidation;
        this.Persuasion = Persuasion;
        this.Socialize = Socialize;
        this.Streetwise = Streetwise;
        this.Subterfuge = Subterfuge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return Name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String Name) {
        this.Name = Name;
    }

    /** Not-null value. */
    public String getPlayer() {
        return Player;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPlayer(String Player) {
        this.Player = Player;
    }

    /** Not-null value. */
    public String getConcept() {
        return Concept;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setConcept(String Concept) {
        this.Concept = Concept;
    }

    public int getIntelligence() {
        return Intelligence;
    }

    public void setIntelligence(int Intelligence) {
        this.Intelligence = Intelligence;
    }

    public int getWits() {
        return Wits;
    }

    public void setWits(int Wits) {
        this.Wits = Wits;
    }

    public int getResolve() {
        return Resolve;
    }

    public void setResolve(int Resolve) {
        this.Resolve = Resolve;
    }

    public int getStrength() {
        return Strength;
    }

    public void setStrength(int Strength) {
        this.Strength = Strength;
    }

    public int getDexterity() {
        return Dexterity;
    }

    public void setDexterity(int Dexterity) {
        this.Dexterity = Dexterity;
    }

    public int getStamina() {
        return Stamina;
    }

    public void setStamina(int Stamina) {
        this.Stamina = Stamina;
    }

    public int getPresence() {
        return Presence;
    }

    public void setPresence(int Presence) {
        this.Presence = Presence;
    }

    public int getManipulation() {
        return Manipulation;
    }

    public void setManipulation(int Manipulation) {
        this.Manipulation = Manipulation;
    }

    public int getComposure() {
        return Composure;
    }

    public void setComposure(int Composure) {
        this.Composure = Composure;
    }

    public Integer getAcademics() {
        return Academics;
    }

    public void setAcademics(Integer Academics) {
        this.Academics = Academics;
    }

    public Integer getComputer() {
        return Computer;
    }

    public void setComputer(Integer Computer) {
        this.Computer = Computer;
    }

    public Integer getCrafts() {
        return Crafts;
    }

    public void setCrafts(Integer Crafts) {
        this.Crafts = Crafts;
    }

    public Integer getInvestigation() {
        return Investigation;
    }

    public void setInvestigation(Integer Investigation) {
        this.Investigation = Investigation;
    }

    public Integer getMedicine() {
        return Medicine;
    }

    public void setMedicine(Integer Medicine) {
        this.Medicine = Medicine;
    }

    public Integer getOccult() {
        return Occult;
    }

    public void setOccult(Integer Occult) {
        this.Occult = Occult;
    }

    public Integer getPolitics() {
        return Politics;
    }

    public void setPolitics(Integer Politics) {
        this.Politics = Politics;
    }

    public Integer getScience() {
        return Science;
    }

    public void setScience(Integer Science) {
        this.Science = Science;
    }

    public Integer getAthletics() {
        return Athletics;
    }

    public void setAthletics(Integer Athletics) {
        this.Athletics = Athletics;
    }

    public Integer getBrawl() {
        return Brawl;
    }

    public void setBrawl(Integer Brawl) {
        this.Brawl = Brawl;
    }

    public Integer getDrive() {
        return Drive;
    }

    public void setDrive(Integer Drive) {
        this.Drive = Drive;
    }

    public Integer getFirearms() {
        return Firearms;
    }

    public void setFirearms(Integer Firearms) {
        this.Firearms = Firearms;
    }

    public Integer getLarceny() {
        return Larceny;
    }

    public void setLarceny(Integer Larceny) {
        this.Larceny = Larceny;
    }

    public Integer getSurvival() {
        return Survival;
    }

    public void setSurvival(Integer Survival) {
        this.Survival = Survival;
    }

    public Integer getWeaponry() {
        return Weaponry;
    }

    public void setWeaponry(Integer Weaponry) {
        this.Weaponry = Weaponry;
    }

    public Integer getAnimalKen() {
        return AnimalKen;
    }

    public void setAnimalKen(Integer AnimalKen) {
        this.AnimalKen = AnimalKen;
    }

    public Integer getEmpathy() {
        return Empathy;
    }

    public void setEmpathy(Integer Empathy) {
        this.Empathy = Empathy;
    }

    public Integer getExpression() {
        return Expression;
    }

    public void setExpression(Integer Expression) {
        this.Expression = Expression;
    }

    public Integer getIntimidation() {
        return Intimidation;
    }

    public void setIntimidation(Integer Intimidation) {
        this.Intimidation = Intimidation;
    }

    public Integer getPersuasion() {
        return Persuasion;
    }

    public void setPersuasion(Integer Persuasion) {
        this.Persuasion = Persuasion;
    }

    public Integer getSocialize() {
        return Socialize;
    }

    public void setSocialize(Integer Socialize) {
        this.Socialize = Socialize;
    }

    public Integer getStreetwise() {
        return Streetwise;
    }

    public void setStreetwise(Integer Streetwise) {
        this.Streetwise = Streetwise;
    }

    public Integer getSubterfuge() {
        return Subterfuge;
    }

    public void setSubterfuge(Integer Subterfuge) {
        this.Subterfuge = Subterfuge;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
