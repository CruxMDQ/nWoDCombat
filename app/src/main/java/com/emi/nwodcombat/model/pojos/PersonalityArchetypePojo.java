package com.emi.nwodcombat.model.pojos;

/**
 * Created by emiliano.desantis on 07/04/2016.
 */
public class PersonalityArchetypePojo {
    private Long id;

    private String name;
    private String description;
    private String regainOne;
    private String regainAll;

    public PersonalityArchetypePojo() {}

    public PersonalityArchetypePojo(String name) {
        this.name = name;
    }

    public PersonalityArchetypePojo(String name, String description, String regainAll, String regainOne) {
        this.name = name;
        this.description = description;
        this.regainAll = regainAll;
        this.regainOne = regainOne;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
