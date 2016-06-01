package com.emi.nwodcombat.characterviewer.mvp;

import android.app.FragmentManager;
import android.content.Context;

import com.emi.nwodcombat.adapters.DemeanorsAdapter;
import com.emi.nwodcombat.adapters.NaturesAdapter;
import com.emi.nwodcombat.adapters.VicesAdapter;
import com.emi.nwodcombat.adapters.VirtuesAdapter;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.utils.Events.DemeanorChanged;
import com.emi.nwodcombat.utils.Events.ExperiencePoolChanged;
import com.emi.nwodcombat.utils.Events.NatureChanged;
import com.emi.nwodcombat.utils.Events.ViceChanged;
import com.emi.nwodcombat.utils.Events.VirtueChanged;
import com.squareup.otto.Subscribe;

import io.realm.RealmResults;

import static com.emi.nwodcombat.utils.Events.CharacterDeleted;
import static com.emi.nwodcombat.utils.Events.ValueChanged;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerPresenter // implements OnSettingChangedListener  //implements OnTraitChangedListener {
{
    private Context context;
    private CharacterViewerView view;
    private CharacterViewerModel model;

    // Character object being viewed
    private Character queriedCharacter;

    // Variable to keep track of the amount of experience the character has available to spend
    // (Spec: probably a good idea to do away with it? Or, at least, to streamline how experience
    // is managed - stuff is rather disorganized)
    // TODO Find a way to move it into the model
    private int experiencePool = 0;

    // This stores all the components that will increase or decrease the experience score
    private DemeanorsAdapter demeanorsAdapter;

    private VicesAdapter vicesAdapter;

    private NaturesAdapter naturesAdapter;

    private VirtuesAdapter virtuesAdapter;

    public CharacterViewerPresenter(CharacterViewerModel model, CharacterViewerView view) {
        this.context = view.getContext();
        this.model = model;
        this.view = view;
        setupView(model.getQueriedCharacter());
    }

    private void setupView(Character character) {
        queriedCharacter = character;

        experiencePool = model.getExperience();

        setupDemeanorsSpinner();
        setupNaturesSpinner();
        setupVicesSpinner();
        setupVirtuesSpinner();

        view.setCharacterName(queriedCharacter.getName());
        view.setCharacterConcept(queriedCharacter.getConcept());
        view.setCharacterPlayer(queriedCharacter.getPlayer());
        view.setupExperienceSpendingWidget(queriedCharacter.getExperience());

        view.setUpUI();

        view.setValues(queriedCharacter.getEntries());

        if (!model.isCheating()) {
            view.notifyExperienceSpenders(experiencePool);
        }
    }

    // Sends menu option selection event to the view for processing
    public void onCharacterDelete() {
        view.showDeleteSnackbar(queriedCharacter.getId());
    }

    // Separate method for handling experience saving (once again, similar but not equal to other
    // traits - just why is it a good idea to do this differently again?)
    private void saveExperience() {

        // The entry to use as template for updating the remaining experience amount
        Entry template = queriedCharacter.getExperience();

        // Have the model update the data about remaining experience on the spot
        model.updateEntry(queriedCharacter.getId(), template.getId(), experiencePool);
    }

    // Separate method for setting up a spinner; I've been trying to generify this but without
    // success so far - read below why
    public void setupDemeanorsSpinner() {
        // Initializing the spinner takes:
        // - a context
        // - a list of objects of the right class
        // - a flag for automaticUpdate (don't yet know what 'automatic update' means in this context)
        RealmResults<Demeanor> demeanors = model.getDemeanors();

        demeanorsAdapter = new DemeanorsAdapter(context, demeanors);

        // Associate the adapter to the spinner (well, duh)
        view.setDemeanorsSpinnerAdapter(demeanorsAdapter);

        setDemeanorsSpinnerSelection(demeanors);
    }

    private void setDemeanorsSpinnerSelection(RealmResults<Demeanor> demeanors) {
        // Cycle through the list of objects and if the name matches that of the first item on the
        // corresponding list for the character, set it as the selection for the spinner
        Demeanor currentDemeanor = queriedCharacter.getDemeanorTraits()
                .where()
                .equalTo(Constants.FIELD_TRAIT_ORDINAL, 0)
                .findFirst()
                .getDemeanor();

        for (int i = 0; i < model.getDemeanors().size(); i++) {
            Demeanor cycledDemeanor = demeanors.get(i);

            if (cycledDemeanor.getName().equals(currentDemeanor.getName())) {
                view.setDemeanorsSpinnerSelection(i);
                break;
            }
        }
    }

    public void setupNaturesSpinner() {
        RealmResults<Nature> natures = model.getNatures();

        naturesAdapter = new NaturesAdapter(context, natures);

        view.setNaturesSpinnerAdapter(naturesAdapter);

        setNaturesSpinnerSelection(natures);
    }

    private void setNaturesSpinnerSelection(RealmResults<Nature> natures) {
        Nature currentNature = queriedCharacter.getNatureTraits()
                .where()
                .equalTo(Constants.FIELD_TRAIT_ORDINAL, 0)
                .findFirst()
                .getNature();

        for (int i = 0; i < model.getNatures().size(); i++) {
            Nature cycledNature = natures.get(i);

            if (cycledNature.getName().equals(currentNature.getName())) {
                view.setNaturesSpinnerSelection(i);
            }
        }
    }

    public void setupVicesSpinner() {
        RealmResults<Vice> vices = model.getVices();

        vicesAdapter = new VicesAdapter(context, vices);

        view.setVicesSpinnerAdapter(vicesAdapter);

        setVicesSpinnerSelection(vices);
    }

    private void setVicesSpinnerSelection(RealmResults<Vice> vices) {
        Vice currentVice = queriedCharacter.getViceTraits()
                .where()
                .equalTo(Constants.FIELD_TRAIT_ORDINAL, 0)
                .findFirst()
                .getVice();

        for (int i = 0; i < model.getVices().size(); i++) {
            Vice cycledVice = vices.get(i);

            if (cycledVice.getName().equals(currentVice.getName())) {
                view.setVicesSpinnerSelection(i);
            }
        }
    }

    public void setupVirtuesSpinner() {
        RealmResults<Virtue> virtues = model.getVirtues();

        virtuesAdapter = new VirtuesAdapter(context, virtues);

        view.setVirtuesSpinnerAdapter(virtuesAdapter);

        setVirtuesSpinnerSelection(virtues);
    }

    private void setVirtuesSpinnerSelection(RealmResults<Virtue> virtues) {
        Virtue currentVirtue = queriedCharacter.getVirtueTraits()
                .where()
                .equalTo(Constants.FIELD_TRAIT_ORDINAL, 0)
                .findFirst()
                .getVirtue();

        for (int i = 0; i < virtues.size(); i++) {
            Virtue cycledVirtue = virtues.get(i);

            if (cycledVirtue.getName().equals(currentVirtue.getName())) {
                view.setVirtuesSpinnerSelection(i);
            }
        }
    }

    @Subscribe
    public void onDeleteCharacterEvent(CharacterDeleted event) {
        model.deleteCharacter(event.id);
        // Pop back stack and remove this fragment
        FragmentManager fm = view.getFragmentManager();
        if (fm == null) {
            return;
        }
        fm.popBackStack();
    }

    @Subscribe
    public void onExperiencePoolChange(ExperiencePoolChanged event) {
        experiencePool = experiencePool + (event.isIncrease ? 1 : experiencePool == 0 ? 0 : -1);

        // Changes amount of experience in the pool

        // Updates text in experience tracker widget
        view.setExperience(String.valueOf(experiencePool));

        // Notifies all widgets registered as experience spenders and disables/enables
        // increasing/decreasing buttons as necessary
        if (!model.isCheating()) {
            view.notifyExperienceSpenders(experiencePool);
        }

        // Updates remaining experience, if any
        saveExperience();
    }

    @Subscribe
    public void onDemeanorTraitChangedEvent(DemeanorChanged event) {
        // Pass the updating operation straight out to the model for handling

        // Retrieve object based on spinner position
        model.updateDemeanorTrait(queriedCharacter.getId(), demeanorsAdapter.getItem(event.position));
    }

    @Subscribe
    public void onNatureTraitChangedEvent(NatureChanged event) {
        model.updateNatureTrait(queriedCharacter.getId(), naturesAdapter.getItem(event.position));
    }

    @Subscribe
    public void onViceTraitChangedEvent(ViceChanged event) {
        model.updateViceTrait(queriedCharacter.getId(), vicesAdapter.getItem(event.position));
    }

    @Subscribe
    public void onVirtueTraitChangedEvent(VirtueChanged event) {
        model.updateVirtueTrait(queriedCharacter.getId(), virtuesAdapter.getItem(event.position));
    }

    @Subscribe
    public void onValueChanged(ValueChanged event) {
        changeValue(event.isIncrease, event.key, event.category);
    }

    private void changeValue(boolean isIncrease, String key, String category) {
        // Determine whether it's an increase or a decrease
        Integer change = isIncrease ? 1 : -1;

        // Determine experience cost for raising this value
        Integer experienceCost = model.getExperienceCost(category);

        // Get character experience
        Integer experiencePool = model.getExperience();

//        if (!model.isCheating()) {

        int existingScore = model.findEntryValue(key, category);

        int newScore;

        newScore = change + existingScore;

        if (!model.isCheating()) {
            if (change > 0 && model.checkIfCharacterHasEnoughXP(category)) {

                experiencePool -= experienceCost;

                doUpdate(key, experiencePool, newScore);
            } else if (change < 0 && existingScore > 0) {
                experiencePool += experienceCost;

                doUpdate(key, experiencePool, newScore);
            }
        } else {
            doUpdate(key, newScore);
        }
    }

    private void doUpdate(String key, int newScore) {
        model.addOrUpdateEntry(key, Constants.FIELD_TYPE_INTEGER, String.valueOf(newScore));

        view.changeWidgetValue(key, newScore);
    }


    private void doUpdate(String key, Integer experiencePool, int newScore) {
        model.addOrUpdateEntry(key, Constants.FIELD_TYPE_INTEGER, String.valueOf(newScore));

        model.addOrUpdateEntry(Constants.CHARACTER_EXPERIENCE, Constants.FIELD_TYPE_INTEGER,
            String.valueOf(experiencePool));

        view.changeWidgetValue(key, newScore, experiencePool);
    }

    public void checkSettings() {
        view.toggleEditionPanel(model.isCheating());
    }
}
