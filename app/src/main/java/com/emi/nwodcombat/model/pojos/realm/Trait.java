package com.emi.nwodcombat.model.pojos.realm;

import com.emi.nwodcombat.Constants;

import io.realm.RealmObject;

/**
 * Created by emiliano.desantis on 06/04/2016.
 */
public class Trait extends RealmObject {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (!type.equals(Constants.CHARACTER_VIRTUE) ||
            !type.equals(Constants.CHARACTER_VICE) ||
            !type.equals(Constants.CHARACTER_DEMEANOR) ||
            !type.equals(Constants.CHARACTER_NATURE)) {
            throw new IllegalArgumentException("Incorrect type parameter!");
        }
        this.type = type;
    }
}
