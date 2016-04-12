package com.emi.nwodcombat.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Vice extends RealmObject {

    @PrimaryKey
    private Long id;
    private String Name;
    private String RegainOne;
    private String Description;

    public Vice() {
    }

    public Vice(Long id) {
        this.id = id;
    }

    public Vice(Long id, String Name, String RegainOne, String Description) {
        this.id = id;
        this.Name = Name;
        this.RegainOne = RegainOne;
        this.Description = Description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRegainOne() {
        return RegainOne;
    }

    public void setRegainOne(String RegainOne) {
        this.RegainOne = RegainOne;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}
