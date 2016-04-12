package com.emi.nwodcombat.model.realm;

import io.realm.RealmObject;

public class Virtue extends RealmObject {

    private Long id;

    private String Name;
    private String RegainAll;
    private String Description;

    public Virtue() {
    }

    public Virtue(Long id) {
        this.id = id;
    }

    public Virtue(Long id, String Name, String RegainAll, String Description) {
        this.id = id;
        this.Name = Name;
        this.RegainAll = RegainAll;
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

    public String getRegainAll() {
        return RegainAll;
    }

    public void setRegainAll(String RegainAll) {
        this.RegainAll = RegainAll;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}
