package com.emi.nwodcombat.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.emi.nwodcombat.model.db.CharacterPersonalityTraits;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHARACTER_PERSONALITY_TRAITS".
*/
public class CharacterPersonalityTraitsDao extends AbstractDao<CharacterPersonalityTraits, Long> {

    public static final String TABLENAME = "CHARACTER_PERSONALITY_TRAITS";

    /**
     * Properties of entity CharacterPersonalityTraits.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property IdCharacter = new Property(1, long.class, "idCharacter", false, "ID_CHARACTER");
        public final static Property IdDemeanor = new Property(2, long.class, "idDemeanor", false, "ID_DEMEANOR");
        public final static Property IdNature = new Property(3, long.class, "idNature", false, "ID_NATURE");
        public final static Property IdVice = new Property(4, long.class, "idVice", false, "ID_VICE");
        public final static Property IdVirtue = new Property(5, long.class, "idVirtue", false, "ID_VIRTUE");
    };


    public CharacterPersonalityTraitsDao(DaoConfig config) {
        super(config);
    }
    
    public CharacterPersonalityTraitsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHARACTER_PERSONALITY_TRAITS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"ID_CHARACTER\" INTEGER NOT NULL ," + // 1: idCharacter
                "\"ID_DEMEANOR\" INTEGER NOT NULL ," + // 2: idDemeanor
                "\"ID_NATURE\" INTEGER NOT NULL ," + // 3: idNature
                "\"ID_VICE\" INTEGER NOT NULL ," + // 4: idVice
                "\"ID_VIRTUE\" INTEGER NOT NULL );"); // 5: idVirtue
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHARACTER_PERSONALITY_TRAITS\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CharacterPersonalityTraits entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getIdCharacter());
        stmt.bindLong(3, entity.getIdDemeanor());
        stmt.bindLong(4, entity.getIdNature());
        stmt.bindLong(5, entity.getIdVice());
        stmt.bindLong(6, entity.getIdVirtue());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CharacterPersonalityTraits readEntity(Cursor cursor, int offset) {
        CharacterPersonalityTraits entity = new CharacterPersonalityTraits( //
            cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // idCharacter
            cursor.getLong(offset + 2), // idDemeanor
            cursor.getLong(offset + 3), // idNature
            cursor.getLong(offset + 4), // idVice
            cursor.getLong(offset + 5) // idVirtue
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CharacterPersonalityTraits entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setIdCharacter(cursor.getLong(offset + 1));
        entity.setIdDemeanor(cursor.getLong(offset + 2));
        entity.setIdNature(cursor.getLong(offset + 3));
        entity.setIdVice(cursor.getLong(offset + 4));
        entity.setIdVirtue(cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CharacterPersonalityTraits entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CharacterPersonalityTraits entity) {
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
