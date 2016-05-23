package com.emi.nwodcombat.characterwizard.mvp;

import android.content.Context;

import com.emi.nwodcombat.adapters.DemeanorsAdapter;
import com.emi.nwodcombat.adapters.NaturesAdapter;
import com.emi.nwodcombat.adapters.VicesAdapter;
import com.emi.nwodcombat.adapters.VirtuesAdapter;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.utils.Events;
import com.squareup.otto.Subscribe;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 18/05/2016.
 */
public class PersonalInfoPresenter {
    private final Context context;
    private PersonalInfoView view;
    private CharacterWizardModel model;

    private DemeanorsAdapter demeanorsAdapter;

    private VicesAdapter vicesAdapter;

    private NaturesAdapter naturesAdapter;

    private VirtuesAdapter virtuesAdapter;

    public PersonalInfoPresenter(Context context, CharacterWizardModel model, PersonalInfoView view) {
        this.model = model;
        this.view = view;
        this.context = view.getContext();
        setupWidgets();
    }

    private void setupWidgets() {
        setupDemeanorsSpinner();
        setupNaturesSpinner();
        setupVicesSpinner();
        setupVirtuesSpinner();

        view.setUpTextWatcher();
        view.setupTestData();
    }

    public void setupDemeanorsSpinner() {
        RealmResults<Demeanor> demeanors = model.getDemeanors();

        demeanorsAdapter = new DemeanorsAdapter(context, demeanors, true);

        view.setDemeanorsSpinnerAdapter(demeanorsAdapter);
    }

    public void setupNaturesSpinner() {
        RealmResults<Nature> natures = model.getNatures();

        naturesAdapter = new NaturesAdapter(context, natures, true);

        view.setNaturesSpinnerAdapter(naturesAdapter);
    }

    public void setupVicesSpinner() {
        RealmResults<Vice> vices = model.getVices();

        vicesAdapter = new VicesAdapter(context, vices, true);

        view.setVicesSpinnerAdapter(vicesAdapter);
    }

    public void setupVirtuesSpinner() {
        RealmResults<Virtue> virtues = model.getVirtues();

        virtuesAdapter = new VirtuesAdapter(context, virtues, true);

        view.setVirtuesSpinnerAdapter(virtuesAdapter);
    }

    @Subscribe
    public void onDemeanorTraitChangedEvent(Events.DemeanorTraitChanged event) {
        // Pass the updating operation straight out to the model for handling

        // Retrieve object based on spinner position
        model.addOrUpdateDemeanorTrait(demeanorsAdapter.getItem(event.position));
    }

    @Subscribe
    public void onNatureTraitChangedEvent(Events.NatureTraitChanged event) {
        model.addOrUpdateNatureTrait(naturesAdapter.getItem(event.position));
    }

    @Subscribe
    public void onViceTraitChangedEvent(Events.ViceTraitChanged event) {
        model.addOrUpdateViceTrait(vicesAdapter.getItem(event.position));
    }

    @Subscribe
    public void onVirtueTraitChangedEvent(Events.VirtueTraitChanged event) {
        model.addOrUpdateVirtueTrait(virtuesAdapter.getItem(event.position));
    }

    @Subscribe
    public void onEntryChanged(Events.TextEntryChanged event) {
        Entry entry = event.entry;

        model.addOrUpdateEntry(entry);
    }
}