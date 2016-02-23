package com.emi.nwodcombat.diceroller.pojos;

/**
 * Created by Emi on 2/22/16.
 */
public class Rule {
    private String name;
    private boolean isSelected;
    private int value;

    public Rule(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public Rule(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
