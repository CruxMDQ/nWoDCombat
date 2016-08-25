package com.emi.nwodcombat.rules;

import com.emi.nwodcombat.model.realm.Entry;

import java.util.ArrayList;

import io.realm.RealmModel;

/**
 * Created by emiliano.desantis on 14/07/2016.
 */
public class Rule implements RealmModel {
    String namespace;       // sphere: discipline, arcane, invocation; spell: devotion, spell, charm; merit
    String name;
    String description;
    int level;
    ArrayList<String> parameters = new ArrayList<>();
    ArrayList<ArrayList<Entry>> requirements = new ArrayList<>();

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(String parameter) {
        this.parameters.add(parameter);
    }

    public void removeParameter(String parameter) {
        this.parameters.remove(parameter);
    }

    public void addRequirement(ArrayList<Entry> entries) {
        this.requirements.add(entries);
    }

    public void removeRequirement(Object object) {
        this.requirements.remove(object);
    }

    public ArrayList<ArrayList<Entry>> getRequirements() {
        return this.requirements;
    }
}
