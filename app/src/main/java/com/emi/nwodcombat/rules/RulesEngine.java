package com.emi.nwodcombat.rules;

import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.tools.Constants;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 14/07/2016.
 */
public class RulesEngine {
    static List<Rule> rules = new ArrayList<>();

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
     * @param namespaces Merit, Arcana/Discipline/Invocation, Charm/Devotion/Spell
     * @param character The character to test for available resources in the given namespace
     * @return List of resources on the given namespace that the character qualifies for
     */
    static public RealmList<Rule> evaluate(List<String> namespaces, Character character) {
        RealmList<Rule> result = new RealmList<>();

        for (Rule rule : rules) {
            List<Boolean> validations = new ArrayList<>();

            // * ----> Evaluate each of the rule's mandatory reqs against all of the character's entries
            if (validateNamespaces(namespaces, rule)) {

                // This logic is rather convoluted because there may be a condition giving a series
                // of criteria of which only one need be true
                for (List<Entry> requirementSet : rule.getRequirements()) {
                    boolean shouldAdd = false;

                    for (Entry requirement : requirementSet) {
                        for (Entry entry : character.getEntries()) {

                            // * ----> If the name matches, compare values
                            if (validateKeys(entry, requirement)) {

                                if (requirement.getType().equalsIgnoreCase(Constants.FIELD_TYPE_INTEGER)) {
                                    // * ----> If the value is not enough, the character does not qualify for this resource
                                    shouldAdd = compareIntegers(requirement, entry);
                                }

                                if (requirement.getType().equalsIgnoreCase(Constants.FIELD_TYPE_STRING)) {
                                    shouldAdd = compareStrings(requirement, entry);
                                }

                                if (shouldAdd) break;
                            }
                        }
                    }

                    validations.add(shouldAdd);
                }

                if (checkValidations(validations)) {
                    result.add(rule);
                }
            }
        }

        return result;
    }

    private static boolean checkValidations(List<Boolean> validations) {
        for (Boolean validation : validations) {
            if (!validation) {
                return false;
            }
        }
        return true;
    }

    private static boolean compareStrings(Entry requirement, Entry entry) {
        return entry.getValue().equalsIgnoreCase(requirement.getValue());
    }

    private static boolean compareIntegers(Entry requirement, Entry entry) {
        Integer entryValue = Integer.valueOf(entry.getValue());
        Integer requiredValue = Integer.valueOf(requirement.getValue());

        return entryValue >= requiredValue;
    }

    private static boolean validateNamespaces(List<String> namespaces, Rule rule) {
        for (String existing : rule.getNamespaces()) {
            if (!namespaces.contains(existing)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateKeys(Entry entry, Entry requirement) {
        String entryKey = entry.getKey();
        String requirementKey = requirement.getKey();

        return entryKey.equalsIgnoreCase(requirementKey);
    }

    // Method to add actions


}
