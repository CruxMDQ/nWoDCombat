package com.emi.nwodcombat.characterviewer.mvp;

import android.content.Context;

import com.emi.nwodcombat.adapters.DemeanorsAdapter;
import com.emi.nwodcombat.adapters.NaturesAdapter;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerView.ExperiencePoolChangeEvent;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerView.TraitChangedEvent;
import com.emi.nwodcombat.interfaces.ExperienceSpender;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.model.realm.DemeanorTrait;
import com.emi.nwodcombat.tools.ArrayHelper;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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

    // Object that will be used to update the character being viewed
    private Character updatedCharacter = new Character();

    // This flag checks whether anything has been changed on the updatedCharacter object - if it's
    // called far from it, then probably there's trouble
    private boolean hasChanges = false;

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
        updatedCharacter.setId(queriedCharacter.getId());
        updatedCharacter.setDemeanorTraits(queriedCharacter.getDemeanorTraits());

        experiencePool = Integer.valueOf(queriedCharacter.getExperience().getValue());

        setUpDemeanorsSpinner();
//        view.setUpVirtueSpinner(model.getVirtues());
//        view.setUpViceSpinner(model.getVices());
//        view.setUpNaturesSpinner(model.getNatures());
//        view.setUpDemeanorsSpinner(model.getDemeanors());

        // Populate personal info cardview
        view.setCharacterName(queriedCharacter.getName());
        view.setCharacterConcept(queriedCharacter.getConcept());
        view.setCharacterPlayer(queriedCharacter.getPlayer());

        // Populate personality traits cardview
        view.setCharacterVirtue(queriedCharacter.getFirstVirtue());
        view.setCharacterVice(queriedCharacter.getFirstVice());
        view.setCharacterNature(queriedCharacter.getFirstNature());
        view.setCharacterDemeanor(queriedCharacter.getFirstDemeanor());

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

    public void onPause() {
        // If there have been any changes...
        if (hasChanges) {
            // ...you update the character
            model.updateCharacter(updatedCharacter);
        }
    }

    // Sends menu option selection event to the view for processing
    public void onCharacterDelete() {
        view.onCharacterDelete();
    }

    // Separate method for handling experience saving (once again, similar but not equal to other
    // traits - just why is it a good idea to do this differently again?)
    private void saveExperience() {

        // Any change in the experience pool implies a change to the character
        hasChanges = true;

        // The entry to use as template for updating the remaining experience amount
        Entry template = queriedCharacter.getExperience();

        // If this was not yet edited, there will be no entry on the updatedCharacter object, so it
        // will be necessary to create one
        // TODO Check if a NSEE could be triggered by other means
        try {
            editEntryToUpdate(template, experiencePool);
        } catch (NoSuchElementException e) {
            addEntryToUpdate(template, experiencePool);
        }
    }

    @Override
    public void onTraitChanged(Object caller, int value, String constant) {
        // If this flag is not set, changes will not be saved
        hasChanges = true;

        // De-abstract object into widget
        ValueSetter widget = (ValueSetter) caller;

        // Retrieve entry associated with widget as tag
        Entry template = (Entry) widget.getTag();

        experiencePool = view.changeWidgetValue(constant, value, experiencePool, model.getPreferences().getBoolean(
            Constants.SETTING_CHEAT, false));

        try {
            // Try editing an existing entry to update
            editEntryToUpdate(template, widget.getCurrentValue());

        } catch (NoSuchElementException e) {
            // If we got a NSEE, it (probably, most likely - I know >.< ) means that there is no
            // entry that matches the parameters, so we create one
            addEntryToUpdate(template, widget.getCurrentValue());
        }

        // Notifies all widgets registered as experience spenders and disables/enables
        // increasing/decreasing buttons as necessary
        notifyExperienceSpenders();

        // Updates remaining experience, if any
        saveExperience();
    }

    /**
     * Method to add a value for updating - nothing strange here, just create and add to list
     * @param template The entry to update
     * @param value The new value for the entry
     */
    private void addEntryToUpdate(Entry template, int value) {
        Entry entry = new Entry()
            .setId(template.getId())
            .setKey(template.getKey())
            .setType(Constants.FIELD_TYPE_INTEGER)
            .setValue(String.valueOf(value));

        updatedCharacter.getEntries().add(entry);
    }

    /**
     * Method for editing an existing value for updating
     * @param template The entry to update
     * @param value The new value for the entry
     */
    private void editEntryToUpdate(Entry template, int value) throws NoSuchElementException {
        // Look up if the template is on the list - this will result in a NoSuchElementException if
        // it does not
        Entry entry = ArrayHelper.findEntry(updatedCharacter.getEntries(), template.getId());

        // Change the value on the entry
        entry.setValue(String.valueOf(value));
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

            // This one is REALLY shaky: the whole 'personality trait list' mumbo-jumbo is set up
            // for the case that a character has a merit that lets it select multiple traits. This
            // assumes that the object we want will always be the first one on the list, but what
            // happens if, having more than one trait, somehow the order is changed?
            // Having a map instead of a list probably would solve the issue, but there is no such
            // thing as a RealmMap. An alternative would be to simply add a boolean isDefault flag
            // to the trait class.
            // TODO Find a better way to figure out which object to get, as opposed to simply the first

            Demeanor currentDemeanor = queriedCharacter.getDemeanorTraits()
                    .where()
                    .equalTo("ordinal", 0)
                    .findFirst()
                    .getDemeanor();

            if (cycledDemeanor.getName().equals(currentDemeanor.getName())) {
                view.setDemeanorsSpinnerSelection(i);
                break;
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
    public void onTraitChangedEvent(TraitChangedEvent event) {
        DemeanorTrait demeanorTrait = event.demeanorTrait;
        Long ordinal = demeanorTrait.getOrdinal();

        Demeanor demeanor = demeanorTrait.getDemeanor();

        // This will only work within a Realm transaction, gotta figure this out the old way
//        updatedCharacter.getDemeanorTraits()
//                .where()
//                .equalTo("ordinal", ordinal)
//                .findFirst()
//                .setDemeanor(demeanor);

        // This doesn't work either. Fuck.
        for (DemeanorTrait trait : updatedCharacter.getDemeanorTraits()) {
            if (trait.getOrdinal().equals(ordinal)) {
                trait.setDemeanor(demeanor);
                break;
            }
        }

        // TODO Try committing this update now
    }
}
