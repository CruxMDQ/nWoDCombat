package com.emi.nwodcombat.model;

/**
 * Created by Crux on 3/26/2016.
 */
public class Record {
    private String name;

    public static Record newInstance() {
        return new Record();
    }

    public Record(String name) {
        this.name = name;
    }

    public Record() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return getName();
    }
}
