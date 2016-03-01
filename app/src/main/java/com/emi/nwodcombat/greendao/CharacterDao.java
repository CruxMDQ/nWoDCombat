package com.emi.nwodcombat.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.emi.nwodcombat.model.db.Character;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHARACTER".
*/
public class CharacterDao extends AbstractDao<Character, Long> {

    public static final String TABLENAME = "CHARACTER";

    /**
     * Properties of entity Character.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "Name", false, "NAME");
        public final static Property Player = new Property(2, String.class, "Player", false, "PLAYER");
        public final static Property Concept = new Property(3, String.class, "Concept", false, "CONCEPT");
        public final static Property Intelligence = new Property(4, int.class, "Intelligence", false, "INTELLIGENCE");
        public final static Property Wits = new Property(5, int.class, "Wits", false, "WITS");
        public final static Property Resolve = new Property(6, int.class, "Resolve", false, "RESOLVE");
        public final static Property Strength = new Property(7, int.class, "Strength", false, "STRENGTH");
        public final static Property Dexterity = new Property(8, int.class, "Dexterity", false, "DEXTERITY");
        public final static Property Stamina = new Property(9, int.class, "Stamina", false, "STAMINA");
        public final static Property Presence = new Property(10, int.class, "Presence", false, "PRESENCE");
        public final static Property Manipulation = new Property(11, int.class, "Manipulation", false, "MANIPULATION");
        public final static Property Composure = new Property(12, int.class, "Composure", false, "COMPOSURE");
        public final static Property Academics = new Property(13, Integer.class, "Academics", false, "ACADEMICS");
        public final static Property Computer = new Property(14, Integer.class, "Computer", false, "COMPUTER");
        public final static Property Crafts = new Property(15, Integer.class, "Crafts", false, "CRAFTS");
        public final static Property Investigation = new Property(16, Integer.class, "Investigation", false, "INVESTIGATION");
        public final static Property Medicine = new Property(17, Integer.class, "Medicine", false, "MEDICINE");
        public final static Property Occult = new Property(18, Integer.class, "Occult", false, "OCCULT");
        public final static Property Politics = new Property(19, Integer.class, "Politics", false, "POLITICS");
        public final static Property Science = new Property(20, Integer.class, "Science", false, "SCIENCE");
        public final static Property Athletics = new Property(21, Integer.class, "Athletics", false, "ATHLETICS");
        public final static Property Brawl = new Property(22, Integer.class, "Brawl", false, "BRAWL");
        public final static Property Drive = new Property(23, Integer.class, "Drive", false, "DRIVE");
        public final static Property Firearms = new Property(24, Integer.class, "Firearms", false, "FIREARMS");
        public final static Property Larceny = new Property(25, Integer.class, "Larceny", false, "LARCENY");
        public final static Property Stealth = new Property(26, Integer.class, "Stealth", false, "STEALTH");
        public final static Property Survival = new Property(27, Integer.class, "Survival", false, "SURVIVAL");
        public final static Property Weaponry = new Property(28, Integer.class, "Weaponry", false, "WEAPONRY");
        public final static Property AnimalKen = new Property(29, Integer.class, "AnimalKen", false, "ANIMAL_KEN");
        public final static Property Empathy = new Property(30, Integer.class, "Empathy", false, "EMPATHY");
        public final static Property Expression = new Property(31, Integer.class, "Expression", false, "EXPRESSION");
        public final static Property Intimidation = new Property(32, Integer.class, "Intimidation", false, "INTIMIDATION");
        public final static Property Persuasion = new Property(33, Integer.class, "Persuasion", false, "PERSUASION");
        public final static Property Socialize = new Property(34, Integer.class, "Socialize", false, "SOCIALIZE");
        public final static Property Streetwise = new Property(35, Integer.class, "Streetwise", false, "STREETWISE");
        public final static Property Subterfuge = new Property(36, Integer.class, "Subterfuge", false, "SUBTERFUGE");
        public final static Property Health = new Property(37, Integer.class, "Health", false, "HEALTH");
        public final static Property Morality = new Property(38, Integer.class, "Morality", false, "MORALITY");
        public final static Property Potency = new Property(39, Integer.class, "Potency", false, "POTENCY");
        public final static Property Willpower = new Property(40, Integer.class, "Willpower", false, "WILLPOWER");
        public final static Property WillpowerReserve = new Property(41, Integer.class, "WillpowerReserve", false, "WILLPOWER_RESERVE");
    };


    public CharacterDao(DaoConfig config) {
        super(config);
    }
    
    public CharacterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHARACTER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT NOT NULL ," + // 1: Name
                "\"PLAYER\" TEXT NOT NULL ," + // 2: Player
                "\"CONCEPT\" TEXT NOT NULL ," + // 3: Concept
                "\"INTELLIGENCE\" INTEGER NOT NULL ," + // 4: Intelligence
                "\"WITS\" INTEGER NOT NULL ," + // 5: Wits
                "\"RESOLVE\" INTEGER NOT NULL ," + // 6: Resolve
                "\"STRENGTH\" INTEGER NOT NULL ," + // 7: Strength
                "\"DEXTERITY\" INTEGER NOT NULL ," + // 8: Dexterity
                "\"STAMINA\" INTEGER NOT NULL ," + // 9: Stamina
                "\"PRESENCE\" INTEGER NOT NULL ," + // 10: Presence
                "\"MANIPULATION\" INTEGER NOT NULL ," + // 11: Manipulation
                "\"COMPOSURE\" INTEGER NOT NULL ," + // 12: Composure
                "\"ACADEMICS\" INTEGER," + // 13: Academics
                "\"COMPUTER\" INTEGER," + // 14: Computer
                "\"CRAFTS\" INTEGER," + // 15: Crafts
                "\"INVESTIGATION\" INTEGER," + // 16: Investigation
                "\"MEDICINE\" INTEGER," + // 17: Medicine
                "\"OCCULT\" INTEGER," + // 18: Occult
                "\"POLITICS\" INTEGER," + // 19: Politics
                "\"SCIENCE\" INTEGER," + // 20: Science
                "\"ATHLETICS\" INTEGER," + // 21: Athletics
                "\"BRAWL\" INTEGER," + // 22: Brawl
                "\"DRIVE\" INTEGER," + // 23: Drive
                "\"FIREARMS\" INTEGER," + // 24: Firearms
                "\"LARCENY\" INTEGER," + // 25: Larceny
                "\"STEALTH\" INTEGER," + // 26: Stealth
                "\"SURVIVAL\" INTEGER," + // 27: Survival
                "\"WEAPONRY\" INTEGER," + // 28: Weaponry
                "\"ANIMAL_KEN\" INTEGER," + // 29: AnimalKen
                "\"EMPATHY\" INTEGER," + // 30: Empathy
                "\"EXPRESSION\" INTEGER," + // 31: Expression
                "\"INTIMIDATION\" INTEGER," + // 32: Intimidation
                "\"PERSUASION\" INTEGER," + // 33: Persuasion
                "\"SOCIALIZE\" INTEGER," + // 34: Socialize
                "\"STREETWISE\" INTEGER," + // 35: Streetwise
                "\"SUBTERFUGE\" INTEGER," + // 36: Subterfuge
                "\"HEALTH\" INTEGER," + // 37: Health
                "\"MORALITY\" INTEGER," + // 38: Morality
                "\"POTENCY\" INTEGER," + // 39: Potency
                "\"WILLPOWER\" INTEGER," + // 40: Willpower
                "\"WILLPOWER_RESERVE\" INTEGER);"); // 41: WillpowerReserve
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHARACTER\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Character entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindString(3, entity.getPlayer());
        stmt.bindString(4, entity.getConcept());
        stmt.bindLong(5, entity.getIntelligence());
        stmt.bindLong(6, entity.getWits());
        stmt.bindLong(7, entity.getResolve());
        stmt.bindLong(8, entity.getStrength());
        stmt.bindLong(9, entity.getDexterity());
        stmt.bindLong(10, entity.getStamina());
        stmt.bindLong(11, entity.getPresence());
        stmt.bindLong(12, entity.getManipulation());
        stmt.bindLong(13, entity.getComposure());
 
        Integer Academics = entity.getAcademics();
        if (Academics != null) {
            stmt.bindLong(14, Academics);
        }
 
        Integer Computer = entity.getComputer();
        if (Computer != null) {
            stmt.bindLong(15, Computer);
        }
 
        Integer Crafts = entity.getCrafts();
        if (Crafts != null) {
            stmt.bindLong(16, Crafts);
        }
 
        Integer Investigation = entity.getInvestigation();
        if (Investigation != null) {
            stmt.bindLong(17, Investigation);
        }
 
        Integer Medicine = entity.getMedicine();
        if (Medicine != null) {
            stmt.bindLong(18, Medicine);
        }
 
        Integer Occult = entity.getOccult();
        if (Occult != null) {
            stmt.bindLong(19, Occult);
        }
 
        Integer Politics = entity.getPolitics();
        if (Politics != null) {
            stmt.bindLong(20, Politics);
        }
 
        Integer Science = entity.getScience();
        if (Science != null) {
            stmt.bindLong(21, Science);
        }
 
        Integer Athletics = entity.getAthletics();
        if (Athletics != null) {
            stmt.bindLong(22, Athletics);
        }
 
        Integer Brawl = entity.getBrawl();
        if (Brawl != null) {
            stmt.bindLong(23, Brawl);
        }
 
        Integer Drive = entity.getDrive();
        if (Drive != null) {
            stmt.bindLong(24, Drive);
        }
 
        Integer Firearms = entity.getFirearms();
        if (Firearms != null) {
            stmt.bindLong(25, Firearms);
        }
 
        Integer Larceny = entity.getLarceny();
        if (Larceny != null) {
            stmt.bindLong(26, Larceny);
        }
 
        Integer Stealth = entity.getStealth();
        if (Stealth != null) {
            stmt.bindLong(27, Stealth);
        }
 
        Integer Survival = entity.getSurvival();
        if (Survival != null) {
            stmt.bindLong(28, Survival);
        }
 
        Integer Weaponry = entity.getWeaponry();
        if (Weaponry != null) {
            stmt.bindLong(29, Weaponry);
        }
 
        Integer AnimalKen = entity.getAnimalKen();
        if (AnimalKen != null) {
            stmt.bindLong(30, AnimalKen);
        }
 
        Integer Empathy = entity.getEmpathy();
        if (Empathy != null) {
            stmt.bindLong(31, Empathy);
        }
 
        Integer Expression = entity.getExpression();
        if (Expression != null) {
            stmt.bindLong(32, Expression);
        }
 
        Integer Intimidation = entity.getIntimidation();
        if (Intimidation != null) {
            stmt.bindLong(33, Intimidation);
        }
 
        Integer Persuasion = entity.getPersuasion();
        if (Persuasion != null) {
            stmt.bindLong(34, Persuasion);
        }
 
        Integer Socialize = entity.getSocialize();
        if (Socialize != null) {
            stmt.bindLong(35, Socialize);
        }
 
        Integer Streetwise = entity.getStreetwise();
        if (Streetwise != null) {
            stmt.bindLong(36, Streetwise);
        }
 
        Integer Subterfuge = entity.getSubterfuge();
        if (Subterfuge != null) {
            stmt.bindLong(37, Subterfuge);
        }
 
        Integer Health = entity.getHealth();
        if (Health != null) {
            stmt.bindLong(38, Health);
        }
 
        Integer Morality = entity.getMorality();
        if (Morality != null) {
            stmt.bindLong(39, Morality);
        }
 
        Integer Potency = entity.getPotency();
        if (Potency != null) {
            stmt.bindLong(40, Potency);
        }
 
        Integer Willpower = entity.getWillpower();
        if (Willpower != null) {
            stmt.bindLong(41, Willpower);
        }
 
        Integer WillpowerReserve = entity.getWillpowerReserve();
        if (WillpowerReserve != null) {
            stmt.bindLong(42, WillpowerReserve);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Character readEntity(Cursor cursor, int offset) {
        Character entity = new Character( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // Name
            cursor.getString(offset + 2), // Player
            cursor.getString(offset + 3), // Concept
            cursor.getInt(offset + 4), // Intelligence
            cursor.getInt(offset + 5), // Wits
            cursor.getInt(offset + 6), // Resolve
            cursor.getInt(offset + 7), // Strength
            cursor.getInt(offset + 8), // Dexterity
            cursor.getInt(offset + 9), // Stamina
            cursor.getInt(offset + 10), // Presence
            cursor.getInt(offset + 11), // Manipulation
            cursor.getInt(offset + 12), // Composure
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // Academics
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // Computer
            cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15), // Crafts
            cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16), // Investigation
            cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17), // Medicine
            cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18), // Occult
            cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19), // Politics
            cursor.isNull(offset + 20) ? null : cursor.getInt(offset + 20), // Science
            cursor.isNull(offset + 21) ? null : cursor.getInt(offset + 21), // Athletics
            cursor.isNull(offset + 22) ? null : cursor.getInt(offset + 22), // Brawl
            cursor.isNull(offset + 23) ? null : cursor.getInt(offset + 23), // Drive
            cursor.isNull(offset + 24) ? null : cursor.getInt(offset + 24), // Firearms
            cursor.isNull(offset + 25) ? null : cursor.getInt(offset + 25), // Larceny
            cursor.isNull(offset + 26) ? null : cursor.getInt(offset + 26), // Stealth
            cursor.isNull(offset + 27) ? null : cursor.getInt(offset + 27), // Survival
            cursor.isNull(offset + 28) ? null : cursor.getInt(offset + 28), // Weaponry
            cursor.isNull(offset + 29) ? null : cursor.getInt(offset + 29), // AnimalKen
            cursor.isNull(offset + 30) ? null : cursor.getInt(offset + 30), // Empathy
            cursor.isNull(offset + 31) ? null : cursor.getInt(offset + 31), // Expression
            cursor.isNull(offset + 32) ? null : cursor.getInt(offset + 32), // Intimidation
            cursor.isNull(offset + 33) ? null : cursor.getInt(offset + 33), // Persuasion
            cursor.isNull(offset + 34) ? null : cursor.getInt(offset + 34), // Socialize
            cursor.isNull(offset + 35) ? null : cursor.getInt(offset + 35), // Streetwise
            cursor.isNull(offset + 36) ? null : cursor.getInt(offset + 36), // Subterfuge
            cursor.isNull(offset + 37) ? null : cursor.getInt(offset + 37), // Health
            cursor.isNull(offset + 38) ? null : cursor.getInt(offset + 38), // Morality
            cursor.isNull(offset + 39) ? null : cursor.getInt(offset + 39), // Potency
            cursor.isNull(offset + 40) ? null : cursor.getInt(offset + 40), // Willpower
            cursor.isNull(offset + 41) ? null : cursor.getInt(offset + 41) // WillpowerReserve
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Character entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setPlayer(cursor.getString(offset + 2));
        entity.setConcept(cursor.getString(offset + 3));
        entity.setIntelligence(cursor.getInt(offset + 4));
        entity.setWits(cursor.getInt(offset + 5));
        entity.setResolve(cursor.getInt(offset + 6));
        entity.setStrength(cursor.getInt(offset + 7));
        entity.setDexterity(cursor.getInt(offset + 8));
        entity.setStamina(cursor.getInt(offset + 9));
        entity.setPresence(cursor.getInt(offset + 10));
        entity.setManipulation(cursor.getInt(offset + 11));
        entity.setComposure(cursor.getInt(offset + 12));
        entity.setAcademics(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setComputer(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setCrafts(cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15));
        entity.setInvestigation(cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16));
        entity.setMedicine(cursor.isNull(offset + 17) ? null : cursor.getInt(offset + 17));
        entity.setOccult(cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18));
        entity.setPolitics(cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19));
        entity.setScience(cursor.isNull(offset + 20) ? null : cursor.getInt(offset + 20));
        entity.setAthletics(cursor.isNull(offset + 21) ? null : cursor.getInt(offset + 21));
        entity.setBrawl(cursor.isNull(offset + 22) ? null : cursor.getInt(offset + 22));
        entity.setDrive(cursor.isNull(offset + 23) ? null : cursor.getInt(offset + 23));
        entity.setFirearms(cursor.isNull(offset + 24) ? null : cursor.getInt(offset + 24));
        entity.setLarceny(cursor.isNull(offset + 25) ? null : cursor.getInt(offset + 25));
        entity.setStealth(cursor.isNull(offset + 26) ? null : cursor.getInt(offset + 26));
        entity.setSurvival(cursor.isNull(offset + 27) ? null : cursor.getInt(offset + 27));
        entity.setWeaponry(cursor.isNull(offset + 28) ? null : cursor.getInt(offset + 28));
        entity.setAnimalKen(cursor.isNull(offset + 29) ? null : cursor.getInt(offset + 29));
        entity.setEmpathy(cursor.isNull(offset + 30) ? null : cursor.getInt(offset + 30));
        entity.setExpression(cursor.isNull(offset + 31) ? null : cursor.getInt(offset + 31));
        entity.setIntimidation(cursor.isNull(offset + 32) ? null : cursor.getInt(offset + 32));
        entity.setPersuasion(cursor.isNull(offset + 33) ? null : cursor.getInt(offset + 33));
        entity.setSocialize(cursor.isNull(offset + 34) ? null : cursor.getInt(offset + 34));
        entity.setStreetwise(cursor.isNull(offset + 35) ? null : cursor.getInt(offset + 35));
        entity.setSubterfuge(cursor.isNull(offset + 36) ? null : cursor.getInt(offset + 36));
        entity.setHealth(cursor.isNull(offset + 37) ? null : cursor.getInt(offset + 37));
        entity.setMorality(cursor.isNull(offset + 38) ? null : cursor.getInt(offset + 38));
        entity.setPotency(cursor.isNull(offset + 39) ? null : cursor.getInt(offset + 39));
        entity.setWillpower(cursor.isNull(offset + 40) ? null : cursor.getInt(offset + 40));
        entity.setWillpowerReserve(cursor.isNull(offset + 41) ? null : cursor.getInt(offset + 41));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Character entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Character entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
