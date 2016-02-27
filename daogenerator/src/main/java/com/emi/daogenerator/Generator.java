package com.emi.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

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

        character.addIdProperty().primaryKey().autoincrement().getProperty();
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
    }
}
