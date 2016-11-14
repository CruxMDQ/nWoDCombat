package com.emi.nwodcombat.characterwizard.mvp;

import android.view.LayoutInflater;

import com.emi.nwodcombat.adapters.MeritsAdapter;
import com.emi.nwodcombat.application.NwodCombatApplication;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.rules.Rule;
import com.emi.nwodcombat.rules.RulesEngine;
import com.emi.nwodcombat.tools.BusProvider;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 02/06/2016.
 */
public class MeritsPresenter {
    private final CharacterWizardModel model;
    private final MeritsView view;

    private MeritsAdapter adapter;

    public MeritsPresenter(CharacterWizardModel model, MeritsView view) {
        this.model = model;
        this.view = view;
        this.adapter = MeritsAdapter.newInstance(null, view.getContext(), BusProvider.getInstance());
        setupWidgets();
    }

    private void setupWidgets() {
        view.setupUI();

        view.setupRV(adapter);
    }

    public void checkSettings() {
        // TODO Code stuff to ignore point-buy limits
        model.isCheating();
    }

    @Subscribe
    public void onFragmentVisible(Events.MeritsFragmentLoaded event) {
        List<String> namespaces = new ArrayList<>();

        namespaces.add(Constants.NAMESPACE_COMMON);
        namespaces.add(Constants.NAMESPACE_AWAKENED);
        namespaces.add(Constants.NAMESPACE_MERIT);

        RealmList<Rule> merits = RulesEngine.evaluate(namespaces, model.getCharacter());

        adapter.setMerits(merits);
    }

    @Subscribe
    public void onMeritValueChanged(Events.MeritValueChanged event) {
        String name = event.rule.getName();
        List<Integer> levels = event.rule.getLevels();
        int updatedScore = 0;
        /**
         * Pseudocode:
         * - Scan model and check if merit already exists
         * - If yes:
         * --- Get existing merit level
         * --- Set it to the next immediate level
         * --- Deduct point cost from point reserve
         * --- If new reserve value is not enough to purchase the next merit level, or if there are no more levels available, make button invisible
         * - If no:
         * --- Create a new entry for the merit
         * --- Set value to merit level
         * --- Make decrease button visible
         * - In either case:
         * --- Trigger a re-evaluation of all merits to check if the character qualifies for new merits
         */

        /**
         * Pseudocode:
         * - Scan model and check if merit already exists
         * - If yes:
         * --- Get existing merit level
         * --- Set it to the previous immediate level
         * --- Increase point reserve
         * --- If level of merit is reduced to zero, delete merit entry
         */

        // Scan model and check if merit already exists
        Entry merit = model.getEntry(name);

        // This is true if the character ALREADY has the merit at a lower value
        if (merit != null) {
            int currentScore = Integer.valueOf(merit.getValue());

            /**
             * Conditions for increasing:
             * - Character must have enough points to spend on an increase
             * - The current value must not be the maximum
             */

            if (event.isIncrease) {
                if (Collections.max(levels) != currentScore) {
                    int currentIndex = levels.indexOf(currentScore);
                    int nextIndex = currentIndex + 1;
                    updatedScore = levels.get(nextIndex);

                    // TODO Introduce comparison against free merit points
                    // int cost = nextScore - currentScore;
                } else {
                    updatedScore = currentScore;
                }
            } else {
                if (Collections.min(levels) != currentScore) {
                    int currentIndex = levels.indexOf(currentScore);
                    int previousIndex = currentIndex - 1;
                    updatedScore = levels.get(previousIndex);

                    // TODO Introduce comparison against free merit points
                    // int cost = nextScore - currentScore;
                }
            }

            if (!model.hasEntry(name)) {
                model.addEntry(name, Constants.FIELD_TYPE_INTEGER, String.valueOf(updatedScore));
            } else {
                model.updateEntry(name, String.valueOf(updatedScore));
            }
        } else {
            updatedScore = levels.get(0);
            model.addOrUpdateEntry(name, updatedScore);
        }

        event.holder.displayCurrentAndPossiblePointValues(
            LayoutInflater.from(NwodCombatApplication.getAppContext()),
            event.rule,
            updatedScore
        );
    }
}
