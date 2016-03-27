package com.emi.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToManyWithJoinEntity;

public class Generator {
    private final static int VERSION_NUMBER = 1;

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(VERSION_NUMBER, Constants.PACKAGE_MODELS);
        schema.setDefaultJavaPackageDao(Constants.PACKAGE_DAO);
        schema.enableKeepSectionsByDefault();

        addCharacterEntity(schema);

        new DaoGenerator().generateAll(schema, Constants.OUTPUT_DIRECTORY);
    }

    private static void addCharacterEntity(Schema schema) {
        Entity character = schema.addEntity(Constants.ENTITY_CHARACTER);

        Property idCharacter = character.addLongProperty(Constants.FIELD_ID_CHARACTER).primaryKey().autoincrement().getProperty();
        character.addStringProperty(Constants.FIELD_NAME).notNull();
        character.addStringProperty(Constants.FIELD_PLAYER).notNull();
//        character.addStringProperty(Constants.FIELD_CHRONICLE).notNull();
        character.addStringProperty(Constants.FIELD_CONCEPT).notNull();

        // --- Attributes ---
        // Mental
        character.addIntProperty(Constants.FIELD_ATTR_INTELLIGENCE).notNull();
        character.addIntProperty(Constants.FIELD_ATTR_WITS).notNull();
        character.addIntProperty(Constants.FIELD_ATTR_RESOLVE).notNull();
        // Physical
        character.addIntProperty(Constants.FIELD_ATTR_STRENGTH).notNull();
        character.addIntProperty(Constants.FIELD_ATTR_DEXTERITY).notNull();
        character.addIntProperty(Constants.FIELD_ATTR_STAMINA).notNull();
        // Social
        character.addIntProperty(Constants.FIELD_ATTR_PRESENCE).notNull();
        character.addIntProperty(Constants.FIELD_ATTR_MANIPULATION).notNull();
        character.addIntProperty(Constants.FIELD_ATTR_COMPOSURE).notNull();

        // --- Skills ---
        // Mental
        character.addIntProperty(Constants.FIELD_SKILL_ACADEMICS);
        character.addIntProperty(Constants.FIELD_SKILL_COMPUTER);
        character.addIntProperty(Constants.FIELD_SKILL_CRAFTS);
        character.addIntProperty(Constants.FIELD_SKILL_INVESTIGATION);
        character.addIntProperty(Constants.FIELD_SKILL_MEDICINE);
        character.addIntProperty(Constants.FIELD_SKILL_OCCULT);
        character.addIntProperty(Constants.FIELD_SKILL_POLITICS);
        character.addIntProperty(Constants.FIELD_SKILL_SCIENCE);
        // Physical
        character.addIntProperty(Constants.FIELD_SKILL_ATHLETICS);
        character.addIntProperty(Constants.FIELD_SKILL_BRAWL);
        character.addIntProperty(Constants.FIELD_SKILL_DRIVE);
        character.addIntProperty(Constants.FIELD_SKILL_FIREARMS);
        character.addIntProperty(Constants.FIELD_SKILL_LARCENY);
        character.addIntProperty(Constants.FIELD_SKILL_STEALTH);
        character.addIntProperty(Constants.FIELD_SKILL_SURVIVAL);
        character.addIntProperty(Constants.FIELD_SKILL_WEAPONRY);
        // Mental
        character.addIntProperty(Constants.FIELD_SKILL_ANIMAL_KEN);
        character.addIntProperty(Constants.FIELD_SKILL_EMPATHY);
        character.addIntProperty(Constants.FIELD_SKILL_EXPRESSION);
        character.addIntProperty(Constants.FIELD_SKILL_INTIMIDATION);
        character.addIntProperty(Constants.FIELD_SKILL_PERSUASION);
        character.addIntProperty(Constants.FIELD_SKILL_SOCIALIZE);
        character.addIntProperty(Constants.FIELD_SKILL_STREETWISE);
        character.addIntProperty(Constants.FIELD_SKILL_SUBTERFUGE);

        // --- Other traits ---
        character.addIntProperty(Constants.FIELD_TRAIT_HEALTH);
        character.addIntProperty(Constants.FIELD_TRAIT_MORALITY);
        character.addIntProperty(Constants.FIELD_TRAIT_POTENCY);
        character.addIntProperty(Constants.FIELD_TRAIT_WILLPOWER_PERMANENT);
        character.addIntProperty(Constants.FIELD_TRAIT_WILLPOWER_TEMPORARY);

        Entity demeanor = schema.addEntity(Constants.ENTITY_DEMEANOR);
        Entity nature = schema.addEntity(Constants.ENTITY_NATURE);
        Entity vice = schema.addEntity(Constants.ENTITY_VICE);
        Entity virtue = schema.addEntity(Constants.ENTITY_VIRTUE);

        Property idVice = vice.addLongProperty(Constants.FIELD_ID_VICE).primaryKey().autoincrement().getProperty();
        vice.addStringProperty(Constants.FIELD_NAME).notNull();
        vice.addStringProperty(Constants.FIELD_CONDITION_REGAIN_ONE).notNull();
        vice.addStringProperty(Constants.FIELD_DESCRIPTION);
        vice.setSuperclass(Constants.CLASS_RECORD);

        Property idVirtue = virtue.addLongProperty(Constants.FIELD_ID_VIRTUE).primaryKey().autoincrement().getProperty();
        virtue.addStringProperty(Constants.FIELD_NAME).notNull();
        virtue.addStringProperty(Constants.FIELD_CONDITION_REGAIN_ALL).notNull();
        virtue.addStringProperty(Constants.FIELD_DESCRIPTION);
        virtue.setSuperclass(Constants.CLASS_RECORD);

        Property idDemeanor = demeanor.addLongProperty(Constants.FIELD_ID_DEMEANOR).primaryKey().autoincrement().getProperty();
        demeanor.addStringProperty(Constants.FIELD_NAME).notNull();
        demeanor.addStringProperty(Constants.FIELD_CONDITION_REGAIN_ONE);
        demeanor.addStringProperty(Constants.FIELD_CONDITION_REGAIN_ALL);
        demeanor.addStringProperty(Constants.FIELD_DESCRIPTION);
        demeanor.setSuperclass(Constants.CLASS_RECORD);

        Property idNature = nature.addLongProperty(Constants.FIELD_ID_NATURE).primaryKey().autoincrement().getProperty();
        nature.addStringProperty(Constants.FIELD_NAME).notNull();
        nature.addStringProperty(Constants.FIELD_CONDITION_REGAIN_ONE);
        nature.addStringProperty(Constants.FIELD_CONDITION_REGAIN_ALL);
        nature.addStringProperty(Constants.FIELD_DESCRIPTION);
        nature.setSuperclass(Constants.CLASS_RECORD);

        Entity characterPersonalityTraits = schema.addEntity(Constants.ENTITY_CHARACTER_PERSONAL_TRAITS);
        characterPersonalityTraits.addIdProperty().primaryKey().notNull().autoincrement();
        characterPersonalityTraits.addLongProperty(Constants.FIELD_ID_CHARACTER).notNull();
        characterPersonalityTraits.addLongProperty(Constants.FIELD_ID_DEMEANOR).notNull();
        characterPersonalityTraits.addLongProperty(Constants.FIELD_ID_NATURE).notNull();
        characterPersonalityTraits.addLongProperty(Constants.FIELD_ID_VICE).notNull();
        characterPersonalityTraits.addLongProperty(Constants.FIELD_ID_VIRTUE).notNull();

        ToManyWithJoinEntity toManyCharPersDemeanor = character.addToMany(demeanor, characterPersonalityTraits, idCharacter, idDemeanor);
        ToManyWithJoinEntity toManyCharPersNature = character.addToMany(nature, characterPersonalityTraits, idCharacter, idNature);
        ToManyWithJoinEntity toManyCharPersVice = character.addToMany(vice, characterPersonalityTraits, idCharacter, idVice);
        ToManyWithJoinEntity toManyCharPersVirtue = character.addToMany(virtue, characterPersonalityTraits, idCharacter, idVirtue);

//         Preserving old structure: may be necessary in case present one cannot handle multiple virtues or vices
//        Entity characterVices = schema.addEntity(Constants.ENTITY_CHARACTER_VICES);
//        characterVices.addIdProperty().primaryKey().notNull().autoincrement();
//        characterVices.addLongProperty(Constants.FIELD_ID_CHARACTER).notNull();
//        characterVices.addLongProperty(Constants.FIELD_ID_VICE).notNull();
//
//        Entity characterVirtues = schema.addEntity(Constants.ENTITY_CHARACTER_VIRTUES);
//        characterVirtues.addIdProperty().primaryKey().notNull().autoincrement();
//        characterVirtues.addLongProperty(Constants.FIELD_ID_CHARACTER).notNull();
//        characterVirtues.addLongProperty(Constants.FIELD_ID_VIRTUE).notNull();
//
//        ToManyWithJoinEntity toManyCharVices = character.addToMany(vice, characterVices, idCharacter, idVice);
//
//        ToManyWithJoinEntity toManyCharVirtues = character.addToMany(virtue, characterVirtues, idCharacter, idVirtue);
//
//        Entity characterDemeanor = schema.addEntity(Constants.ENTITY_CHARACTER_DEMEANOR);
//        characterDemeanor.addIdProperty().primaryKey().notNull().autoincrement();
//        characterDemeanor.addLongProperty(Constants.FIELD_ID_CHARACTER).notNull();
//        characterDemeanor.addLongProperty(Constants.FIELD_ID_ARCHETYPE).notNull();
//
//        Entity characterNature = schema.addEntity(Constants.ENTITY_CHARACTER_NATURE);
//        characterNature.addIdProperty().primaryKey().notNull().autoincrement();
//        characterNature.addLongProperty(Constants.FIELD_ID_CHARACTER).notNull();
//        characterNature.addLongProperty(Constants.FIELD_ID_ARCHETYPE).notNull();
//
//        Entity personalityArchetype = schema.addEntity(Constants.ENTITY_PERSONALITY_ARCHETYPE);
//        Property idArchetype = personalityArchetype.addLongProperty(Constants.FIELD_ID_ARCHETYPE).primaryKey().autoincrement().getProperty();
//        personalityArchetype.addStringProperty(Constants.FIELD_NAME).notNull();
//        personalityArchetype.addStringProperty(Constants.FIELD_CONDITION_REGAIN_ONE);
//        personalityArchetype.addStringProperty(Constants.FIELD_CONDITION_REGAIN_ALL);
//        personalityArchetype.addStringProperty(Constants.FIELD_DESCRIPTION);
//        personalityArchetype.setSuperclass(Constants.CLASS_RECORD);
//
//        ToManyWithJoinEntity toManyCharDemeanors = character.addToMany(personalityArchetype, characterDemeanor, idCharacter, idArchetype);
//
//        ToManyWithJoinEntity toManyCharNatures = character.addToMany(personalityArchetype, characterNature, idCharacter, idArchetype);
    }
}
