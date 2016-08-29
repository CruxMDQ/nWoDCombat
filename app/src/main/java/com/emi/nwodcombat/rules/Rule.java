package com.emi.nwodcombat.rules;

import com.emi.nwodcombat.model.realm.Entry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.RealmModel;

/**
 * Created by emiliano.desantis on 14/07/2016.
 */
public class Rule implements RealmModel {
    String name;
    String hint;
    String description;
    List<Integer> levels = new ArrayList<>();
    List<String> namespaces = new ArrayList<>(); // sphere: discipline, arcane, invocation; spell: devotion, spell, charm; merit
    List<List<Entry>> requirements = new ArrayList<>();

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

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void addRequirement(List<Entry> entries) {
        this.requirements.add(entries);
    }

    public void removeRequirement(Object object) {
        this.requirements.remove(object);
    }

    public List<List<Entry>> getRequirements() {
        return this.requirements;
    }

    public void addLevels(Integer... integers) {
        Collections.addAll(this.levels, integers);
    }

    public void removeLevel(Object object) {
        this.levels.remove(object);
    }

    public List<Integer> getLevels() {
        return this.levels;
    }

    public void addNamespace(String namespace) {
        this.namespaces.add(namespace);
    }

    public void addNamespaces(String... namespaces) {
        Collections.addAll(this.namespaces, namespaces);
    }

    public void removeNamespace(Object object) {
        this.namespaces.remove(object);
    }

    public List<String> getNamespaces() {
        return namespaces;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
