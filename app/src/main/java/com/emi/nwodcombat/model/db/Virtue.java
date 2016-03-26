package com.emi.nwodcombat.model.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import com.emi.nwodcombat.model.Record;
// KEEP INCLUDES END
/**
 * Entity mapped to table "VIRTUE".
 */
public class Virtue extends Record  {

    private Long idVirtue;
    /** Not-null value. */
    private String Name;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Virtue() {
    }

    public Virtue(Long idVirtue) {
        this.idVirtue = idVirtue;
    }

    public Virtue(Long idVirtue, String Name) {
        this.idVirtue = idVirtue;
        this.Name = Name;
    }

    public Long getIdVirtue() {
        return idVirtue;
    }

    public void setIdVirtue(Long idVirtue) {
        this.idVirtue = idVirtue;
    }

    /** Not-null value. */
    public String getName() {
        return Name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String Name) {
        this.Name = Name;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
