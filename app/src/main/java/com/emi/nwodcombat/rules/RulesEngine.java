package com.emi.nwodcombat.rules;

import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Entry;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 14/07/2016.
 */
public class RulesEngine {
    static ArrayList<Rule> rules = new ArrayList<>();

    // Method to add rules
    static public void addRule(Rule rule) {
        rules.add(rule);
    }

    /**
     * Pseudocode:
     * - For each rule:
     * --> Check that the rule is actually on the correct namespace
     * --> Evaluate the rule against all of the character entries
     * ----> Evaluate each of the rule's mandatoryRequirements against all of the character's entries
     * ----> If the name matches, compare values
     * ----> If the value is not enough, the character does not qualify for this resource, so break loop
     * @param namespace Merit, Arcana/Discipline/Invocation, Charm/Devotion/Spell
     * @param character The character to test for available resources in the given namespace
     * @return List of resources on the given namespace that the character qualifies for
     */
    static public RealmList<Rule> eval(String namespace, Character character) {
        RealmList<Rule> result = new RealmList<>();

        for (Rule rule : rules) {
            boolean shouldAdd = true;

            // * --> Check that the rule is actually on the correct namespace
            if (rule.getNamespace().equalsIgnoreCase(namespace)) {

//                for (Entry entry : character.getEntries()) {
//                    shouldAdd = evaluateRequisites(rule, entry);
//                }

//                // * --> Evaluate the rule against all of the character entries
                shouldAdd = evaluateRequisites(rule, character.getEntries());
            }

            if (shouldAdd) {
                result.add(rule);
            }
        }

        return result;
    }

    private static boolean evaluateRequisites(Rule rule, RealmList<Entry> entries) {
        ArrayList<Boolean> validations = new ArrayList<>();

        // * ----> Evaluate each of the rule's mandatory reqs against all of the character's entries
        for (ArrayList<Entry> requirementSet : rule.getRequirements()) {
            boolean shouldAdd = false;

            for (Entry requirement : requirementSet) {
                for (Entry entry : entries) {

                    // * ----> If the name matches, compare values
                    String entryKey = entry.getKey();
                    String requirementKey = requirement.getKey();

                    if (entryKey.equalsIgnoreCase(requirementKey)) {

                        // * ----> If the value is not enough, the character does not qualify for this resource
                        Integer entryValue = Integer.valueOf(entry.getValue());
                        Integer requiredValue = Integer.valueOf(requirement.getValue());

                        shouldAdd = entryValue >= requiredValue;

                        if (shouldAdd) break;
                    }
                    if (shouldAdd) break;
                }
            }

            validations.add(shouldAdd);
        }

        for (Boolean validation : validations) {
            if (!validation) {
                return false;
            }
        }

        return true;
    }

    private static boolean evaluateRequisites(Rule rule, Entry entry) {
        ArrayList<Boolean> validations = new ArrayList<>();

        // * ----> Evaluate each of the rule's mandatory reqs against all of the character's entries
        for (ArrayList<Entry> requirementSet : rule.getRequirements()) {
            boolean shouldAdd = false;

            for (Entry requirement : requirementSet) {
                // * ----> If the name matches, compare values
                if (entry.getKey().equalsIgnoreCase(requirement.getKey())) {

                    // * ----> If the value is not enough, the character does not qualify for this resource
                    Integer entryValue = Integer.valueOf(entry.getValue());
                    Integer requiredValue = Integer.valueOf(requirement.getValue());

                    shouldAdd = entryValue >= requiredValue;

                    if (shouldAdd) break;
                }
            }

            validations.add(shouldAdd);
        }

        for (Boolean validation : validations) {
            if (!validation) {
                return false;
            }
        }

        return true;
    }

    // Method to add actions


}
