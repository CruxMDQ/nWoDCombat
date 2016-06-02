package com.emi.nwodcombat.characterwizard.mvp;

import android.content.Context;

import com.emi.nwodcombat.adapters.MeritsAdapter;

/**
 * Created by emiliano.desantis on 02/06/2016.
 * TODO UNTESTED - SPECIALTIES HAVE TO BE COMPLETED FIRST
 */
public class MeritsPresenter {
    private final CharacterWizardModel model;
    private final MeritsView view;
    private final Context context;

    private MeritsAdapter adapter;

    public MeritsPresenter(CharacterWizardModel model, MeritsView view) {
        this.model = model;
        this.view = view;
        this.context = view.getContext();
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
}
