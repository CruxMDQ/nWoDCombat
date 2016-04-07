package com.emi.nwodcombat.model.pojos;

/**
 * Created by emiliano.desantis on 07/04/2016.
 */
public class PersonalityArchetype {
    private int id;

    private String description;
    private String name;
    private String regainOne;
    private String regainAll;

    public PersonalityArchetype() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegainAll() {
        return regainAll;
    }

    public void setRegainAll(String regainAll) {
        this.regainAll = regainAll;
    }

    public String getRegainOne() {
        return regainOne;
    }

    public void setRegainOne(String regainOne) {
        this.regainOne = regainOne;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
