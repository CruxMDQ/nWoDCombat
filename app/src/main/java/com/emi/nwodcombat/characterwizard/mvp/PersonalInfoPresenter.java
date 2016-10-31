package com.emi.nwodcombat.characterwizard.mvp;

import android.content.Context;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.adapters.DemeanorsAdapter;
import com.emi.nwodcombat.adapters.NaturesAdapter;
import com.emi.nwodcombat.adapters.VicesAdapter;
import com.emi.nwodcombat.adapters.VirtuesAdapter;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Subscribe;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 18/05/2016.
 */
public class PersonalInfoPresenter {
    private final Context context;
    private final PersonalInfoView view;
    private final CharacterWizardModel model;

    private DemeanorsAdapter demeanorsAdapter;

    private VicesAdapter vicesAdapter;

    private NaturesAdapter naturesAdapter;

    private VirtuesAdapter virtuesAdapter;

    public PersonalInfoPresenter(CharacterWizardModel model, PersonalInfoView view) {
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

        setupTestData();
    }

    private void setupTestData() {
        if (Constants.MODE_TEST) {
            view.setName(context.getString(R.string.test_info_name));
            view.setConcept(context.getString(R.string.test_info_concept));
            view.setPlayer(context.getString(R.string.default_step_personal_info_player));
        }
    }

    private void setupDemeanorsSpinner() {
        RealmResults<Demeanor> demeanors = model.getDemeanors();

        demeanorsAdapter = new DemeanorsAdapter(context, demeanors);

        view.setDemeanorsSpinnerAdapter(demeanorsAdapter);
    }

    private void setupNaturesSpinner() {
        RealmResults<Nature> natures = model.getNatures();

        naturesAdapter = new NaturesAdapter(context, natures);

        view.setNaturesSpinnerAdapter(naturesAdapter);
    }

    private void setupVicesSpinner() {
        RealmResults<Vice> vices = model.getVices();

        vicesAdapter = new VicesAdapter(context, vices);

        view.setVicesSpinnerAdapter(vicesAdapter);
    }

    private void setupVirtuesSpinner() {
        RealmResults<Virtue> virtues = model.getVirtues();

        virtuesAdapter = new VirtuesAdapter(context, virtues);

        view.setVirtuesSpinnerAdapter(virtuesAdapter);
    }

    @Subscribe
    public void onDemeanorTraitChangedEvent(Events.DemeanorChanged event) {
        model.addOrUpdateDemeanorTrait(demeanorsAdapter.getItem(event.position));
    }

    @Subscribe
    public void onNatureTraitChangedEvent(Events.NatureChanged event) {
        model.addOrUpdateNatureTrait(naturesAdapter.getItem(event.position));
    }

    @Subscribe
    public void onViceTraitChangedEvent(Events.ViceChanged event) {
        model.addOrUpdateViceTrait(vicesAdapter.getItem(event.position));
    }

    @Subscribe
    public void onVirtueTraitChangedEvent(Events.VirtueChanged event) {
        model.addOrUpdateVirtueTrait(virtuesAdapter.getItem(event.position));
    }

    @Subscribe
    public void onEntryChanged(Events.TextEntryChanged event) {
        model.addOrUpdateEntry(event.key, event.value);
    }

    @Subscribe
    public void onFragmentVisible(Events.InfoFragmentLoaded event) {
        model.refreshCharacter();
    }
}