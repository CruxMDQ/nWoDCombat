package com.emi.nwodcombat.model.pojos;

/**
 * Created by Crux on 3/26/2016.
 */
class Record {
    private String name;

    public Record(String name) {
        this.name = name;
    }

    public Record() {
    }

    private String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return getName();
    }
}
