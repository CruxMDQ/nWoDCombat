package com.emi.nwodcombat.characterviewer.mvp;

import android.content.Context;

import com.emi.nwodcombat.adapters.DemeanorsAdapter;
import com.emi.nwodcombat.adapters.NaturesAdapter;
import com.emi.nwodcombat.adapters.ViceRealmAdapter;
import com.emi.nwodcombat.adapters.VirtueRealmAdapter;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerView.DemeanorTraitChangedEvent;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerView.ExperiencePoolChangeEvent;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerView.NatureTraitChangedEvent;
import com.emi.nwodcombat.interfaces.ExperienceSpender;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import io.realm.RealmResults;

import static com.emi.nwodcombat.characterviewer.mvp.CharacterViewerView.DeleteCharacterEvent;
import static com.emi.nwodcombat.characterviewer.mvp.CharacterViewerView.UpdateCharacterEvent;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerPresenter implements OnTraitChangedListener {

    private Context context;
    private CharacterViewerView view;
    private CharacterViewerModel model;

    // Character object being viewed
    private Character queriedCharacter;

    // Variable to keep track of the amount of experience the character has available to spend
    // (Spec: probably a good idea to do away with it? Or, at least, to streamline how experience
    // is managed - stuff is rather disorganized)
    private int experiencePool = 0;

    // This stores all the components that will increase or decrease the experience score
    private ArrayList<ExperienceSpender> experienceSpenders = new ArrayList<>();

    public CharacterViewerPresenter(Context context, CharacterViewerModel model, CharacterViewerView view) {
        this.context = context;
        this.model = model;
        this.view = view;
    }

    public void setUpView(long idCharacter) {
        queriedCharacter = model.getQueriedCharacter(idCharacter);

        experiencePool = Integer.valueOf(queriedCharacter.getExperience().getValue());

        setUpDemeanorsSpinner();
        setUpNaturesSpinner();
        setUpVicesSpinner();
        setUpVirtuesSpinner();

        // Populate personal info cardview
        view.setCharacterName(queriedCharacter.getName());
        view.setCharacterConcept(queriedCharacter.getConcept());
        view.setCharacterPlayer(queriedCharacter.getPlayer());

        // Populate personality traits cardview
        view.setCharacterNature(queriedCharacter.getFirstNature());
        view.setCharacterDemeanor(queriedCharacter.getFirstDemeanor());
        view.setCharacterVice(queriedCharacter.getFirstVice());
        view.setCharacterVirtue(queriedCharacter.getFirstVirtue());

        view.setUpExperienceSpendingWidget(queriedCharacter.getExperience());

        setUpStatWidgets();
    }

    public void setUpStatWidgets() {
        experienceSpenders.add(
            view.setUpWidgetIntelligence(this, queriedCharacter.getIntelligence()));
        experienceSpenders.add(view.setUpWidgetWits(this, queriedCharacter.getWits()));
        experienceSpenders.add(view.setUpWidgetResolve(this, queriedCharacter.getResolve()));
        experienceSpenders.add(view.setUpWidgetStrength(this, queriedCharacter.getStrength()));
        experienceSpenders.add(view.setUpWidgetDexterity(this, queriedCharacter.getDexterity()));
        experienceSpenders.add(view.setUpWidgetStamina(this, queriedCharacter.getStamina()));
        experienceSpenders.add(view.setUpWidgetPresence(this, queriedCharacter.getPresence()));
        experienceSpenders.add(
            view.setUpWidgetManipulation(this, queriedCharacter.getManipulation()));
        experienceSpenders.add(view.setUpWidgetComposure(this, queriedCharacter.getComposure()));

        experienceSpenders.add(view.setUpWidgetAcademics(this, queriedCharacter.getAcademics()));
        experienceSpenders.add(view.setUpWidgetComputer(this, queriedCharacter.getComputer()));
        experienceSpenders.add(view.setUpWidgetCrafts(this, queriedCharacter.getCrafts()));
        experienceSpenders.add(
            view.setUpWidgetInvestigation(this, queriedCharacter.getInvestigation()));
        experienceSpenders.add(view.setUpWidgetMedicine(this, queriedCharacter.getMedicine()));
        experienceSpenders.add(view.setUpWidgetOccult(this, queriedCharacter.getOccult()));
        experienceSpenders.add(view.setUpWidgetPolitics(this, queriedCharacter.getPolitics()));
        experienceSpenders.add(view.setUpWidgetScience(this, queriedCharacter.getScience()));

        experienceSpenders.add(view.setUpWidgetAthletics(this, queriedCharacter.getAthletics()));
        experienceSpenders.add(view.setUpWidgetBrawl(this, queriedCharacter.getBrawl()));
        experienceSpenders.add(view.setUpWidgetDrive(this, queriedCharacter.getDrive()));
        experienceSpenders.add(view.setUpWidgetFirearms(this, queriedCharacter.getFirearms()));
        experienceSpenders.add(view.setUpWidgetLarceny(this, queriedCharacter.getLarceny()));
        experienceSpenders.add(view.setUpWidgetStealth(this, queriedCharacter.getStealth()));
        experienceSpenders.add(view.setUpWidgetSurvival(this, queriedCharacter.getSurvival()));
        experienceSpenders.add(view.setUpWidgetWeaponry(this, queriedCharacter.getWeaponry()));

        experienceSpenders.add(view.setUpWidgetAnimalKen(this, queriedCharacter.getAnimalKen()));
        experienceSpenders.add(view.setUpWidgetEmpathy(this, queriedCharacter.getEmpathy()));
        experienceSpenders.add(view.setUpWidgetExpression(this, queriedCharacter.getExpression()));
        experienceSpenders.add(view.setUpWidgetIntimidation(this,
            queriedCharacter.getIntimidation()));
        experienceSpenders.add(view.setUpWidgetPersuasion(this, queriedCharacter.getPersuasion()));
        experienceSpenders.add(view.setUpWidgetSocialize(this, queriedCharacter.getSocialize()));
        experienceSpenders.add(view.setUpWidgetStreetwise(this, queriedCharacter.getStreetwise()));
        experienceSpenders.add(view.setUpWidgetSubterfuge(this, queriedCharacter.getSubterfuge()));

//        experienceSpenders.add(view.setUpWidgetDefense(this, queriedCharacter.getDefense()));
        view.setUpWidgetDefense(this, queriedCharacter.getDefense());
//        experienceSpenders.add(view.setUpWidgetHealth(this, queriedCharacter.getHealth()));
        view.setUpWidgetHealth(this, queriedCharacter.getHealth());
//        experienceSpenders.add(view.setUpWidgetInitiative(this, queriedCharacter.getInitiative()));
        view.setUpWidgetInitiative(this, queriedCharacter.getInitiative());
        experienceSpenders.add(view.setUpWidgetMorality(this, queriedCharacter.getMorality()));
//        experienceSpenders.add(view.setUpWidgetSpeed(this, queriedCharacter.getSpeed()));
        view.setUpWidgetSpeed(this, queriedCharacter.getSpeed());
        experienceSpenders.add(view.setUpWidgetWillpower(this, queriedCharacter.getWillpower()));
    }

    // Sends menu option selection event to the view for processing
    public void onCharacterDelete() {
        view.onCharacterDelete();
    }

    // Separate method for handling experience saving (once again, similar but not equal to other
    // traits - just why is it a good idea to do this differently again?)
    private void saveExperience() {

        // The entry to use as template for updating the remaining experience amount
        Entry template = queriedCharacter.getExperience();

        // Have the model update the data about remaining experience on the spot
        model.updateEntry(queriedCharacter.getId(), template.getId(), experiencePool);
    }

    @Override
    public void onTraitChanged(Object caller, int value, String constant) {
        // De-abstract object into widget
        ValueSetter widget = (ValueSetter) caller;

        // Retrieve entry associated with widget as tag
        Entry template = (Entry) widget.getTag();

        // Spend experience, if not cheating
        experiencePool = view.changeWidgetValue(constant, value, experiencePool, model.getPreferences().getBoolean(
            Constants.SETTING_CHEAT, false));

        // Have the model update the data about the entry on the spot
        model.updateEntry(queriedCharacter.getId(), template.getId(), widget.getCurrentValue());

        // Notifies all widgets registered as experience spenders and disables/enables
        // increasing/decreasing buttons as necessary
        notifyExperienceSpenders();

        // Updates remaining experience, if any
        saveExperience();
    }

    /**
     * Method for triggering actions on all objects on the experienceSpenders watch list
     */
    private void notifyExperienceSpenders() {
        for (ExperienceSpender experienceSpender : experienceSpenders) {
            // What happens, so far, depends on what is coded on ValueSetterWidget (just why did
            // I code this on an interface as opposed to simply adding a method to the widget?)
            experienceSpender.onCharacterExperienceChanged(experiencePool);
        }
    }

    // Separate method for setting up a spinner; I've been trying to generify this but without
    // success so far - read below why
    public void setUpDemeanorsSpinner() {
        /** This extends a parametrized {@link io.realm.RealmBaseAdapter} */
        DemeanorsAdapter demeanorsAdapter;

        // Initializing the spinner takes:
        // - a context
        // - a list of objects of the right class
        // - a flag for automaticUpdate (don't yet know what 'automatic update' means in this context)
        demeanorsAdapter = new DemeanorsAdapter(context, model.getDemeanors(), true);

        // Associate the adapter to the spinner (well, duh)
        view.setDemeanorsSpinnerAdapter(demeanorsAdapter);

        setDemeanorsSpinnerSelection();
    }

    private void setDemeanorsSpinnerSelection() {
        // Cycle through the list of objects and if the name matches that of the first item on the
        // corresponding list for the character, set it as the selection for the spinner
        RealmResults<Demeanor> demeanors = model.getDemeanors();

        for (int i = 0; i < model.getDemeanors().size(); i++) {
            Demeanor cycledDemeanor = demeanors.get(i);

            Demeanor currentDemeanor = queriedCharacter.getDemeanorTraits()
                .where()
                .equalTo(Constants.FIELD_TRAIT_ORDINAL, 0)
                .findFirst()
                .getDemeanor();

            if (cycledDemeanor.getName().equals(currentDemeanor.getName())) {
                view.setDemeanorsSpinnerSelection(i);
                view.setDemeanorSpinnerLabel(currentDemeanor.getName());
                break;
            }
        }
    }

    public void setUpNaturesSpinner() {
        NaturesAdapter naturesAdapter;

        naturesAdapter = new NaturesAdapter(context, model.getNatures(), true);

        view.setNaturesSpinnerAdapter(naturesAdapter);

        setNaturesSpinnerSelection();
    }

    private void setNaturesSpinnerSelection() {
        RealmResults<Nature> natures = model.getNatures();

        for (int i = 0; i < model.getNatures().size(); i++) {
            Nature cycledNature = natures.get(i);

            Nature currentNature = queriedCharacter.getNatureTraits()
                .where()
                .equalTo(Constants.FIELD_TRAIT_ORDINAL, 0)
                .findFirst()
                .getNature();

            if (cycledNature.getName().equals(currentNature.getName())) {
                view.setNaturesSpinnerSelection(i);
                view.setNatureSpinnerLabel(currentNature.getName());
            }
        }
    }

    public void setUpVicesSpinner() {
        ViceRealmAdapter viceRealmAdapter;

        viceRealmAdapter = new ViceRealmAdapter(context, model.getVices(), true);

        view.setVicesSpinnerAdapter(viceRealmAdapter);

        setVicesSpinnerSelection();
    }

    private void setVicesSpinnerSelection() {
        RealmResults<Vice> vices = model.getVices();

        for (int i = 0; i < model.getVices().size(); i++) {
            Vice cycledVice = vices.get(i);

            Vice currentVice = queriedCharacter.getViceTraits()
                .where()
                .equalTo(Constants.FIELD_TRAIT_ORDINAL, 0)
                .findFirst()
                .getVice();

            if (cycledVice.getName().equals(currentVice.getName())) {
                view.setVicesSpinnerSelection(i);
                view.setViceSpinnerLabel(currentVice.getName());
            }
        }
    }

    public void setUpVirtuesSpinner() {
        VirtueRealmAdapter virtueRealmAdapter;

        virtueRealmAdapter = new VirtueRealmAdapter(context, model.getVirtues(), true);

        view.setVirtuesSpinnerAdapter(virtueRealmAdapter);

        setVirtuesSpinnerSelection();
    }

    private void setVirtuesSpinnerSelection() {
        RealmResults<Virtue> virtues = model.getVirtues();

        for (int i = 0; i < model.getVirtues().size(); i++) {
            Virtue cycledVirtue = virtues.get(i);

            Virtue currentVirtue = queriedCharacter.getVirtueTraits()
                .where()
                .equalTo(Constants.FIELD_TRAIT_ORDINAL, 0)
                .findFirst()
                .getVirtue();

            if (cycledVirtue.getName().equals(currentVirtue.getName())) {
                view.setVirtuesSpinnerSelection(i);
                view.setVirtueSpinnerLabel(currentVirtue.getName());
            }
        }
    }

    @Subscribe
    public void onUpdateCharacterEvent(UpdateCharacterEvent event) {
        model.updateCharacter(event.characterToUpdate);
    }

    @Subscribe
    public void onDeleteCharacterEvent(DeleteCharacterEvent event) {
        model.deleteCharacter(event.characterToDelete);
    }

    @Subscribe
    public void onExperiencePoolChange(ExperiencePoolChangeEvent event) {
        if (event.isIncrease) {

            // Changes amount of experience in the pool
            experiencePool++;

            // Updates text in experience tracker widget
            view.setExperience(String.valueOf(experiencePool));

            // Notifies all widgets registered as experience spenders and disables/enables
            // increasing/decreasing buttons as necessary
            notifyExperienceSpenders();

            // Updates remaining experience, if any
            saveExperience();
        } else {

            // It's pointless to execute this block if trying to decrease the experience below 0
            if (experiencePool > 0) {

                // Changes amount of experience in the pool
                experiencePool--;

                // Updates text in experience tracker widget
                view.setExperience(String.valueOf(experiencePool));

                // Notifies all widgets registered as experience spenders and disables/enables
                // increasing/decreasing buttons as necessary
                notifyExperienceSpenders();

                // Updates remaining experience, if any
                saveExperience();
            }
        }
    }

    @Subscribe
    public void onDemeanorTraitChangedEvent(DemeanorTraitChangedEvent event) {
        // Pass the updating operation straight out to the model for handling
        model.updateDemeanorTrait(queriedCharacter.getId(), event.demeanorTrait);
    }

    @Subscribe
    public void onNatureTraitChangedEvent(NatureTraitChangedEvent event) {
        model.updateNatureTrait(queriedCharacter.getId(), event.natureTrait);
    }

    @Subscribe
    public void onViceTraitChangedEvent(CharacterViewerView.ViceTraitChangedEvent event) {
        model.updateViceTrait(queriedCharacter.getId(), event.viceTrait);
    }

    @Subscribe
    public void onVirtueTraitChangedEvent(CharacterViewerView.VirtueTraitChangedEvent event) {
        model.updateVirtueTrait(queriedCharacter.getId(), event.virtueTrait);
    }
}
