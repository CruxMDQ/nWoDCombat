package com.emi.nwodcombat.model.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import com.emi.nwodcombat.model.Record;
// KEEP INCLUDES END
/**
 * Entity mapped to table "VICE".
 */
public class Vice extends Record  {

    private Long idVice;
    /** Not-null value. */
    private String Name;
    /** Not-null value. */
    private String RegainOne;
    private String Description;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Vice() {
    }

    public Vice(Long idVice) {
        this.idVice = idVice;
    }

    public Vice(Long idVice, String Name, String RegainOne, String Description) {
        this.idVice = idVice;
        this.Name = Name;
        this.RegainOne = RegainOne;
        this.Description = Description;
    }

    public Long getIdVice() {
        return idVice;
    }

    public void setIdVice(Long idVice) {
        this.idVice = idVice;
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
    public String getRegainOne() {
        return RegainOne;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRegainOne(String RegainOne) {
        this.RegainOne = RegainOne;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}