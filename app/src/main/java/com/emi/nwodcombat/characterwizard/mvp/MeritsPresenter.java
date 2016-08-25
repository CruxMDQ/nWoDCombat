package com.emi.nwodcombat.characterwizard.mvp;

import android.content.Context;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.adapters.MeritsAdapter;
import com.emi.nwodcombat.rules.Rule;
import com.emi.nwodcombat.rules.RulesEngine;
import com.emi.nwodcombat.tools.BusProvider;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 02/06/2016.
 * TODO UNTESTED - SPECIALTIES HAVE TO BE COMPLETED FIRST
 */
public class MeritsPresenter {
    private final CharacterWizardModel model;
    private final MeritsView view;
    private final Context context;
    final Bus bus;

    private MeritsAdapter adapter;

    public MeritsPresenter(CharacterWizardModel model, MeritsView view) {
        this.bus = BusProvider.getInstance();
        this.model = model;
        this.view = view;
        this.context = view.getContext();
        this.adapter = new MeritsAdapter(null, this.context, R.layout.row_merit, BusProvider.getInstance());
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
        RealmList<Rule> merits = RulesEngine.eval("Awakened", CharacterWizardModel.character);
        adapter.setMerits(merits);
//        this.adapter = new MeritsAdapter(merits, this.context, R.layout.row_merit, BusProvider.getInstance());
//        view.setupRV(adapter);
    }
}
