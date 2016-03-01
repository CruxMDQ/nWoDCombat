package com.emi.nwodcombat.model.pojos;

/**
 * Created by Emi on 2/29/16.
 */
public class Value {
    private String name;
    private Integer value;

    public Value() {
    }

    public Value(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
