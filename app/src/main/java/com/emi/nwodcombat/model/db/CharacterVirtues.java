package com.emi.nwodcombat.model.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "CHARACTER_VIRTUES".
 */
public class CharacterVirtues {

    private long id;
    private long idCharacter;
    private long idVirtue;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public CharacterVirtues() {
    }

    public CharacterVirtues(long id) {
        this.id = id;
    }

    public CharacterVirtues(long id, long idCharacter, long idVirtue) {
        this.id = id;
        this.idCharacter = idCharacter;
        this.idVirtue = idVirtue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(long idCharacter) {
        this.idCharacter = idCharacter;
    }

    public long getIdVirtue() {
        return idVirtue;
    }

    public void setIdVirtue(long idVirtue) {
        this.idVirtue = idVirtue;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}