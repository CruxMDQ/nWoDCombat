package com.emi.daogenerator;

/**
 * Created by Emi on 2/26/16.
 */
public class Constants {
    public static final String PACKAGE_MODELS = "com.emi.nwodcombat.model.db";
    public static final String PACKAGE_DAO = "com.emi.nwodcombat.greendao";

    public static final String CLASS_RECORD = "Record";

    // Root directory is assumed to be the main folder containing the whole project.
    public static final String OUTPUT_DIRECTORY = "app/src/main/java";

    // Entities
    public static final String ENTITY_PERSONALITY_ARCHETYPE = "Archetype";
    public static final String ENTITY_CHARACTER = "Character";
    public static final String ENTITY_DEMEANOR = "Demeanor";
    public static final String ENTITY_NATURE = "Nature";
    public static final String ENTITY_VICE = "Vice";
    public static final String ENTITY_VIRTUE = "Virtue";
    public static final String ENTITY_CHARACTER_PERSONAL_TRAITS = "CharacterPersonalityTraits";
    public static final String ENTITY_CHARACTER_DEMEANOR = "CharacterDemeanor";
    public static final String ENTITY_CHARACTER_NATURE = "CharacterNature";
    public static final String ENTITY_CHARACTER_VICES = "CharacterVices";
    public static final String ENTITY_CHARACTER_VIRTUES = "CharacterVirtues";

    // ------ Fields ------
    public static final String FIELD_ID_ARCHETYPE = "idArchetype";
    public static final String FIELD_ID_CHARACTER = "idCharacter";
    public static final String FIELD_ID_DEMEANOR = "idDemeanor";
    public static final String FIELD_ID_NATURE = "idNature";
    public static final String FIELD_ID_VICE = "idVice";
    public static final String FIELD_ID_VIRTUE = "idVirtue";

    public static final String FIELD_DESCRIPTION = "Description";
    public static final String FIELD_NAME = "Name";
    public static final String FIELD_PLAYER = "Player";
    public static final String FIELD_CHRONICLE = "Chronicle";
    public static final String FIELD_CONCEPT = "Concept";

    public static final String FIELD_CONDITION_REGAIN_ALL = "RegainAll";
    public static final String FIELD_CONDITION_REGAIN_ONE = "RegainOne";

    // --- Attributes ---
    // Mental
    public static final String FIELD_ATTR_INTELLIGENCE = "Intelligence";
    public static final String FIELD_ATTR_WITS = "Wits";
    public static final String FIELD_ATTR_RESOLVE = "Resolve";
    // Physical
    public static final String FIELD_ATTR_STRENGTH = "Strength";
    public static final String FIELD_ATTR_DEXTERITY = "Dexterity";
    public static final String FIELD_ATTR_STAMINA = "Stamina";
    // Social
    public static final String FIELD_ATTR_PRESENCE = "Presence";
    public static final String FIELD_ATTR_MANIPULATION = "Manipulation";
    public static final String FIELD_ATTR_COMPOSURE = "Composure";

    // --- Skills ---
    // Mental
    public static final String FIELD_SKILL_ACADEMICS = "Academics";
    public static final String FIELD_SKILL_COMPUTER = "Computer";
    public static final String FIELD_SKILL_CRAFTS = "Crafts";
    public static final String FIELD_SKILL_INVESTIGATION = "Investigation";
    public static final String FIELD_SKILL_MEDICINE = "Medicine";
    public static final String FIELD_SKILL_OCCULT = "Occult";
    public static final String FIELD_SKILL_POLITICS = "Politics";
    public static final String FIELD_SKILL_SCIENCE = "Science";
    // Physical
    public static final String FIELD_SKILL_ATHLETICS = "Athletics";
    public static final String FIELD_SKILL_BRAWL = "Brawl";
    public static final String FIELD_SKILL_DRIVE = "Drive";
    public static final String FIELD_SKILL_FIREARMS = "Firearms";
    public static final String FIELD_SKILL_LARCENY = "Larceny";
    public static final String FIELD_SKILL_STEALTH = "Stealth";
    public static final String FIELD_SKILL_SURVIVAL = "Survival";
    public static final String FIELD_SKILL_WEAPONRY = "Weaponry";
    // Social
    public static final String FIELD_SKILL_ANIMAL_KEN = "AnimalKen";
    public static final String FIELD_SKILL_EMPATHY = "Empathy";
    public static final String FIELD_SKILL_EXPRESSION = "Expression";
    public static final String FIELD_SKILL_INTIMIDATION = "Intimidation";
    public static final String FIELD_SKILL_PERSUASION = "Persuasion";
    public static final String FIELD_SKILL_SOCIALIZE = "Socialize";
    public static final String FIELD_SKILL_STREETWISE = "Streetwise";
    public static final String FIELD_SKILL_SUBTERFUGE = "Subterfuge";

    // --- Other traits ---
    public static final String FIELD_TRAIT_HEALTH = "Health";
    public static final String FIELD_TRAIT_WILLPOWER_PERMANENT = "Willpower";
    public static final String FIELD_TRAIT_WILLPOWER_TEMPORARY = "WillpowerReserve";
    public static final String FIELD_TRAIT_POTENCY = "Potency";
    public static final String FIELD_TRAIT_MORALITY = "Morality";
    public static final String FIELD_TRAIT_TEMPLATE = "Template";
}
