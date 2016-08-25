package com.emi.nwodcombat.model.pojos;

/**
 * Created by Emi on 2/22/16.
 */
public class CombatRule {
    private String name;
    private boolean isSelected;
    private int value;
    private String contentDescription;

    public CombatRule(String name, boolean isSelected, int value) {
        this.name = name;
        this.isSelected = isSelected;
        this.value = value;
    }

    public CombatRule(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return name;
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

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }
}
