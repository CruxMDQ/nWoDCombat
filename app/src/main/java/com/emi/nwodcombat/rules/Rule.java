package com.emi.nwodcombat.rules;

import com.emi.nwodcombat.model.realm.Entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmModel;

/**
 * Created by emiliano.desantis on 14/07/2016.
 * Implements item 2 of Effective Java, 2nd Edition
 */
public class Rule implements RealmModel {
    private String name;
    private String hint;
    private String description;
    private List<Integer> levels = new ArrayList<>();
    private List<String> namespaces = new ArrayList<>(); // sphere: discipline, arcane, invocation; spell: devotion, spell, charm; merit
    private List<List<Entry>> requirements = new ArrayList<>();

    static public class Builder {
        // Mandatory parameters
        private String name;
        private String hint;
        private String description;

        // At least one value on each of those is required
        private List<Integer> levels = new ArrayList<>();
        private List<String> namespaces = new ArrayList<>(); // sphere: discipline, arcane, invocation; spell: devotion, spell, charm; merit

        // A few merits, to name something, have no requirements
        private List<List<Entry>> requirements = new ArrayList<>();

        public Builder(String name, String hint, String description) {
            this.name = name;
            this.hint = hint;
            this.description = description;
        }

        public Builder levels(Integer... levels) {
            this.levels = Arrays.asList(levels);
            return this;
        }

        public Builder namespaces(String... namespaces) {
            this.namespaces = Arrays.asList(namespaces);
            return this;
        }

        @SafeVarargs
        public final Builder requirements(List<Entry>... requirements) {
            this.requirements = Arrays.asList(requirements);
            return this;
        }

        public Rule build() {
            return new Rule(this);
        }
    }

    private Rule(Builder builder) {
        this.name = builder.name;
        this.hint = builder.hint;
        this.description = builder.description;
        this.levels = builder.levels;
        this.namespaces = builder.namespaces;
        this.requirements = builder.requirements;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHint() {
        return hint;
    }

    List<List<Entry>> getRequirements() {
        return this.requirements;
    }

    public List<Integer> getLevels() {
        return this.levels;
    }

    List<String> getNamespaces() {
        return namespaces;
    }
}
