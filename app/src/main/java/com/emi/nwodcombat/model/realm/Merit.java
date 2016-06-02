package com.emi.nwodcombat.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emiliano.desantis on 02/06/2016.
 * List of things that may be required to qualify for a merit:
 * - Template (supernatural or mortal)
 * - Attribute
 * - Skill
 * - Background fluff
 * - Skill specialty (any)
 * - Skill specialty (particular one)
 * - Skill specialty (any on a particular category)
 * - Merit
 * Requisite logic may be AND, OR or XOR
 *
 * TODO UNTESTED - SPECIALTIES HAVE TO BE COMPLETED FIRST
 */
public class Merit extends RealmObject {
    @PrimaryKey
    private Long id;

    private String description;
    private String name;
    private int cost;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
