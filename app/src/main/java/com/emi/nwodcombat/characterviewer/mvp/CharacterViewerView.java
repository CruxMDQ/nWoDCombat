package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.adapters.NaturesAdapter;
import com.emi.nwodcombat.adapters.ViceRealmAdapter;
import com.emi.nwodcombat.adapters.VirtueRealmAdapter;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.interfaces.ExperienceSpender;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.tools.ArrayHelper;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.widgets.ValueSetterWidget;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerView extends FragmentView implements OnTraitChangedListener {

    // Used for deciding what to do whenever a ValueSetterWidget value is changed: if the 'cheat'
    // flag is on, experience is disregarded
    private SharedPreferences preferences;

    // Otto bus is used to forward actions to the model, bypassing the presenter
    // (Consideration: is it a good idea to bypass the presenter in the first place?)
    private final Bus bus;

    // Character object being viewed
    private Character character;

    // Object that will be used to update the character being viewed
    private Character updatedCharacter = new Character();

    // This stores all the components that will increase or decrease the experience score
    private ArrayList<ExperienceSpender> experienceSpenders = new ArrayList<>();

    // This flag checks whether anything has been changed on the updatedCharacter object - if it's
    // called far from it, then probably there's trouble
    private boolean hasChanges = false;

    // Variable to keep track of the amount of experience the character has available to spend
    // (Spec: probably a good idea to do away with it? Or, at least, to streamline how experience
    // is managed - stuff is rather disorganized)
    private int experiencePool = 0;

    @Bind(R.id.txtCharacterName) TextView txtCharacterName;
    @Bind(R.id.txtCharacterConcept) TextView txtCharacterConcept;
    @Bind(R.id.txtCharacterPlayer) TextView txtCharacterPlayer;

    @Bind(R.id.spinnerVirtue) Spinner spinnerVirtue;
    @Bind(R.id.spinnerVice) Spinner spinnerVice;
    @Bind(R.id.spinnerNature) Spinner spinnerNature;
    @Bind(R.id.spinnerDemeanor) Spinner spinnerDemeanor;

    @Bind(R.id.txtCharacterVirtue) TextView txtCharacterVirtue;
    @Bind(R.id.txtCharacterVice) TextView txtCharacterVice;
    @Bind(R.id.txtCharacterNature) TextView txtCharacterNature;
    @Bind(R.id.txtCharacterDemeanor) TextView txtCharacterDemeanor;

    @Bind(R.id.txtMentalAttrsTitle) TextView txtMentalAttrsTitle;
    @Bind(R.id.txtPhysicalAttrsTitle) TextView txtPhysicalAttrsTitle;
    @Bind(R.id.txtSocialAttrsTitle) TextView txtSocialAttrsTitle;

    @Bind(R.id.valueSetterInt) ValueSetterWidget valueSetterIntelligence;
    @Bind(R.id.valueSetterWits) ValueSetterWidget valueSetterWits;
    @Bind(R.id.valueSetterRes) ValueSetterWidget valueSetterResolve;
    @Bind(R.id.valueSetterStr) ValueSetterWidget valueSetterStrength;
    @Bind(R.id.valueSetterDex) ValueSetterWidget valueSetterDexterity;
    @Bind(R.id.valueSetterSta) ValueSetterWidget valueSetterStamina;
    @Bind(R.id.valueSetterPre) ValueSetterWidget valueSetterPresence;
    @Bind(R.id.valueSetterMan) ValueSetterWidget valueSetterManipulation;
    @Bind(R.id.valueSetterCom) ValueSetterWidget valueSetterComposure;

    @Bind(R.id.txtMentalSkillsTitle) TextView txtMentalSkillsTitle;
    @Bind(R.id.txtPhysicalSkillsTitle) TextView txtPhysicalSkillsTitle;
    @Bind(R.id.txtSocialSkillsTitle) TextView txtSocialSkillsTitle;

    @Bind(R.id.valueSetterAcademics) ValueSetterWidget valueSetterAcademics;
    @Bind(R.id.valueSetterComputer) ValueSetterWidget valueSetterComputer;
    @Bind(R.id.valueSetterCrafts) ValueSetterWidget valueSetterCrafts;
    @Bind(R.id.valueSetterInvestigation) ValueSetterWidget valueSetterInvestigation;
    @Bind(R.id.valueSetterMedicine) ValueSetterWidget valueSetterMedicine;
    @Bind(R.id.valueSetterOccult) ValueSetterWidget valueSetterOccult;
    @Bind(R.id.valueSetterPolitics) ValueSetterWidget valueSetterPolitics;
    @Bind(R.id.valueSetterScience) ValueSetterWidget valueSetterScience;

    @Bind(R.id.valueSetterAthletics) ValueSetterWidget valueSetterAthletics;
    @Bind(R.id.valueSetterBrawl) ValueSetterWidget valueSetterBrawl;
    @Bind(R.id.valueSetterDrive) ValueSetterWidget valueSetterDrive;
    @Bind(R.id.valueSetterFirearms) ValueSetterWidget valueSetterFirearms;
    @Bind(R.id.valueSetterLarceny) ValueSetterWidget valueSetterLarceny;
    @Bind(R.id.valueSetterStealth) ValueSetterWidget valueSetterStealth;
    @Bind(R.id.valueSetterSurvival) ValueSetterWidget valueSetterSurvival;
    @Bind(R.id.valueSetterWeaponry) ValueSetterWidget valueSetterWeaponry;

    @Bind(R.id.valueSetterAnimalKen) ValueSetterWidget valueSetterAnimalKen;
    @Bind(R.id.valueSetterEmpathy) ValueSetterWidget valueSetterEmpathy;
    @Bind(R.id.valueSetterExpression) ValueSetterWidget valueSetterExpression;
    @Bind(R.id.valueSetterIntimidation) ValueSetterWidget valueSetterIntimidation;
    @Bind(R.id.valueSetterPersuasion) ValueSetterWidget valueSetterPersuasion;
    @Bind(R.id.valueSetterSocialize) ValueSetterWidget valueSetterSocialize;
    @Bind(R.id.valueSetterStreetwise) ValueSetterWidget valueSetterStreetwise;
    @Bind(R.id.valueSetterSubterfuge) ValueSetterWidget valueSetterSubterfuge;

    @Bind(R.id.valueSetterDefense) ValueSetterWidget valueSetterDefense;
    @Bind(R.id.valueSetterHealth) ValueSetterWidget valueSetterHealth;
    @Bind(R.id.valueSetterInitiative) ValueSetterWidget valueSetterInitiative;
    @Bind(R.id.valueSetterMorality) ValueSetterWidget valueSetterMorality;
    @Bind(R.id.valueSetterSpeed) ValueSetterWidget valueSetterSpeed;
    @Bind(R.id.valueSetterWillpower) ValueSetterWidget valueSetterWillpower;

    @Bind(R.id.txtExperience) TextView txtExperience;

    @Bind(R.id.scrollCharView) ScrollView scrollCharView;

    /**
     * Default constructor
     * @param fragment Fragment to bind
     * @param bus Bus object to manage events
     */
    public CharacterViewerView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
    }

    /**
     * Populates UI with data from the character to view.
     * @param queriedCharacter Self-explanatory.
     */
    public void setUpView(Character queriedCharacter) {
        this.character = queriedCharacter;

        // ID setting necessary to make sure the changes are applied to the correct character
        updatedCharacter.setId(queriedCharacter.getId());

        // Populate personal info cardview
        txtCharacterName.setText(
            character.getValue(Constants.CHARACTER_NAME, String.class).toString());
        txtCharacterConcept.setText(character.getValue(Constants.CHARACTER_CONCEPT, String.class).toString());
        txtCharacterPlayer.setText(character.getValue(Constants.CHARACTER_PLAYER, String.class).toString());

        // Populate personality traits cardview
        txtCharacterVirtue.setText(character.getVirtues().first().getName());
        txtCharacterVice.setText(character.getVices().first().getName());
        txtCharacterNature.setText(character.getNatures().first().getName());
        txtCharacterDemeanor.setText(character.getDemeanors().first().getName());

        // Load up available experience to spend - this is handled in a way similar to but separate
        // from a ValueSetterWidget (is it okay? Or is there a better way?)
        txtExperience.setText(
            character.getValue(Constants.CHARACTER_EXPERIENCE, Integer.class).toString());

        setUpValueSetters();

        setUpExperienceSpendingWidget();
    }

    /*** Sets up the components that does the experience spending - in a way similar to but not
     * exactly equal to a ValueSetter (which invites again the question: is it worth handling
     * separately? Just how much experience is a character going to have at any time?)
     */
    private void setUpExperienceSpendingWidget() {
        Entry experience = character.getEntry(Constants.CHARACTER_EXPERIENCE);
        txtExperience.setTag(experience);
        experiencePool = Integer.valueOf(experience.getValue());
        txtExperience.setText(String.valueOf(experiencePool));
    }

    /** Container method for all the calls to setUpValueSetter
     */
    private void setUpValueSetters() {
        setUpValueSetter(valueSetterAcademics, Constants.SKILL_ACADEMICS, true);
        setUpValueSetter(valueSetterComputer, Constants.SKILL_COMPUTER, true);
        setUpValueSetter(valueSetterCrafts, Constants.SKILL_CRAFTS, true);
        setUpValueSetter(valueSetterInvestigation, Constants.SKILL_INVESTIGATION, true);
        setUpValueSetter(valueSetterMedicine, Constants.SKILL_MEDICINE, true);
        setUpValueSetter(valueSetterOccult, Constants.SKILL_OCCULT, true);
        setUpValueSetter(valueSetterPolitics, Constants.SKILL_POLITICS, true);
        setUpValueSetter(valueSetterScience, Constants.SKILL_SCIENCE, true);

        setUpValueSetter(valueSetterAthletics, Constants.SKILL_ATHLETICS, true);
        setUpValueSetter(valueSetterBrawl, Constants.SKILL_BRAWL, true);
        setUpValueSetter(valueSetterDrive, Constants.SKILL_DRIVE, true);
        setUpValueSetter(valueSetterFirearms, Constants.SKILL_FIREARMS, true);
        setUpValueSetter(valueSetterLarceny, Constants.SKILL_LARCENY, true);
        setUpValueSetter(valueSetterStealth, Constants.SKILL_STEALTH, true);
        setUpValueSetter(valueSetterSurvival, Constants.SKILL_SURVIVAL, true);
        setUpValueSetter(valueSetterWeaponry, Constants.SKILL_WEAPONRY, true);

        setUpValueSetter(valueSetterAnimalKen, Constants.SKILL_ANIMAL_KEN, true);
        setUpValueSetter(valueSetterEmpathy, Constants.SKILL_EMPATHY, true);
        setUpValueSetter(valueSetterExpression, Constants.SKILL_EXPRESSION, true);
        setUpValueSetter(valueSetterIntimidation, Constants.SKILL_INTIMIDATION, true);
        setUpValueSetter(valueSetterPersuasion, Constants.SKILL_PERSUASION, true);
        setUpValueSetter(valueSetterSocialize, Constants.SKILL_SOCIALIZE, true);
        setUpValueSetter(valueSetterStreetwise, Constants.SKILL_STREETWISE, true);
        setUpValueSetter(valueSetterSubterfuge, Constants.SKILL_SUBTERFUGE, true);

        setUpValueSetter(valueSetterIntelligence, Constants.ATTR_INT, true);
        setUpValueSetter(valueSetterWits, Constants.ATTR_WIT, true);
        setUpValueSetter(valueSetterResolve, Constants.ATTR_RES, true);
        setUpValueSetter(valueSetterStrength, Constants.ATTR_STR, true);
        setUpValueSetter(valueSetterDexterity, Constants.ATTR_DEX, true);
        setUpValueSetter(valueSetterStamina, Constants.ATTR_STA, true);
        setUpValueSetter(valueSetterPresence, Constants.ATTR_PRE, true);
        setUpValueSetter(valueSetterManipulation, Constants.ATTR_MAN, true);
        setUpValueSetter(valueSetterComposure, Constants.ATTR_MAN, true);

        setUpValueSetter(valueSetterDefense, Constants.TRAIT_DERIVED_DEFENSE, false);
        setUpValueSetter(valueSetterHealth, Constants.TRAIT_DERIVED_HEALTH, false);
        setUpValueSetter(valueSetterInitiative, Constants.TRAIT_DERIVED_INITIATIVE, false);
        setUpValueSetter(valueSetterMorality, Constants.TRAIT_MORALITY, true);
        setUpValueSetter(valueSetterSpeed, Constants.TRAIT_DERIVED_SPEED, false);
        setUpValueSetter(valueSetterWillpower, Constants.TRAIT_DERIVED_WILLPOWER, true);
    }

    /*** Demigod method doing four things:
     * - Sets this view as the OnTraitChangedListener
     * - Sets a constant as the content description for the widget
     * - If a flag is set to true, adds the widget to the experience spenders list
     * - After the whole shebang is done, loads values on each ValueSetter
     */
    private void setUpValueSetter(ValueSetterWidget valueSetter, String valueConstant, boolean spendsExperience) {
        valueSetter.setListener(this);
        valueSetter.setContentDescription(valueConstant);
        if (spendsExperience) {
            experienceSpenders.add(valueSetter);
        }
        valueSetter.setCurrentValue(character.getEntry(valueConstant));
    }

    // Personality trait on-click method - may get the axe if UI changes into something better
    @OnClick(R.id.txtCharacterNature)
    public void onNatureClicked() {
        txtCharacterNature.setVisibility(View.GONE);
        spinnerNature.setVisibility(View.VISIBLE);
    }

    // Personality trait on-click method - may get the axe if UI changes into something better
    @OnClick(R.id.txtCharacterDemeanor)
    public void onDemeanorClicked() {
        txtCharacterDemeanor.setVisibility(View.GONE);
        spinnerDemeanor.setVisibility(View.VISIBLE);
    }

    // Personality trait on-click method - may get the axe if UI changes into something better
    @OnClick(R.id.txtCharacterVice)
    public void onViceClicked() {
        txtCharacterVice.setVisibility(View.GONE);
        spinnerVice.setVisibility(View.VISIBLE);
    }

    // Personality trait on-click method - may get the axe if UI changes into something better
    @OnClick(R.id.txtCharacterVirtue)
    public void onVirtueClicked() {
        txtCharacterVirtue.setVisibility(View.GONE);
        spinnerVirtue.setVisibility(View.VISIBLE);
    }

    // Triggered when experience increases via tapping of the 'plus' button on the view
    @OnClick(R.id.btnAddExp)
    public void onExperienceAdded() {
        experiencePool++;
        txtExperience.setText(String.valueOf(experiencePool));
        notifyExperienceSpenders();
        saveExperience();
    }

    // Triggered when experience increases via tapping of the 'minus' button on the view
    @OnClick(R.id.btnRemoveExp)
    public void onExperienceRemoved() {
        if (experiencePool > 0) {
            experiencePool--;
            txtExperience.setText(String.valueOf(experiencePool));
            notifyExperienceSpenders();
            saveExperience();
        }
    }

    // Separate method for handling experience saving (once again, similar but not equal to other
    // traits - just why is it a good idea to do this differently again?)
    private void saveExperience() {
        hasChanges = true;

        Entry template = (Entry) txtExperience.getTag();

        try {
            editEntryToUpdate(template, experiencePool);
        } catch (NoSuchElementException e) {
            addEntryToUpdate(template, experiencePool);
        }
    }

    // Separate method for setting up a spinner; I've been trying to generify this but without
    // success so far - read below why
    public void setUpDemeanorsSpinner(RealmResults<Demeanor> traits) {
        // This extends a parametrized {@link RealmBaseAdapter}
        NaturesAdapter demeanors;

        // Initializing the spinner takes:
        // - a context
        // - a list of objects of the right class
        // - a flag for automaticUpdate (don't yet know what 'automatic update' means in this context)
        demeanors = new NaturesAdapter(getActivity(), traits, true);

        // Associate the adapter to the spinner (well, duh)
        spinnerDemeanor.setAdapter(demeanors);

        // Cycle through the list of objects and if the name matches that of the first item on the
        // corresponding list for the character, set it as the selection for the spinner
        for (int i = 0; i < traits.size(); i++) {
            Demeanor demeanor = traits.get(i);

            // This one is REALLY shaky: the whole 'personality trait list' mumbo-jumbo is set up
            // for the case that a character has a merit that lets it select multiple traits. This
            // assumes that the object we want will always be the first one on the list, but what
            // happens if, having more than one trait, somehow the order is changed?
            // Having a map instead of a list probably would solve the issue, but there is no such
            // thing as a RealmMap. An alternative would be to simply add a boolean isDefault flag
            // to the trait class.
            // TODO Find a better way to figure out which object to get, as opposed to simply the first
            if (demeanor.getName().equals(character.getDemeanors().get(0).getName())) {
                spinnerDemeanor.setSelection(i);
                break;
            }
        }

        // Vanilla listener setting - could have been managed as a parameter, except for one of one
        // of the variables requiring that it be final, don't recall which now
        spinnerDemeanor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve object based on spinner position
                Demeanor demeanor = ((Demeanor) spinnerDemeanor.getItemAtPosition(position));

                // Set textView text according to object value
                txtCharacterDemeanor.setText(demeanor.getName());
                // Set textView visible
                txtCharacterDemeanor.setVisibility(View.VISIBLE);
                // Conceal spinner
                spinnerDemeanor.setVisibility(View.GONE);

                // Empties list in the updatedCharacter object and adds the modified one.
                // Major problem: if there are other objects in this list, they are gone, but
                // modifying the entry itself means making changes to objects shared with other
                // characters.
                // Probably it would be a better idea to simply handle Entries, but personality
                // traits have fields exclusive to their type, depending on which they are.
                // Still need to figure this out, but as a proof of concept this is okay. Ish.
                // (Spec: maybe storing the original value before replacement helps?)
                // TODO Find a solution for the multiple item problem.
                updatedCharacter.getDemeanors().clear();
                updatedCharacter.getDemeanors().add(demeanor);

                // If this flag is not set, changes will not be saved
                hasChanges = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    // Another spinner setting up method - refer to setUpDemeanorsSpinner for info
    public void setUpNaturesSpinner(RealmResults<Nature> traits) {
        NaturesAdapter natures;

        natures = new NaturesAdapter(getActivity(), traits, true);

        spinnerNature.setAdapter(natures);

        for (int i = 0; i < traits.size(); i++) {
            Nature nature = traits.get(i);
            if (nature.getName().equals(character.getNatures().get(0).getName())) {
                spinnerNature.setSelection(i);
                break;
            }
        }

        spinnerNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Nature nature = ((Nature) spinnerNature
                    .getItemAtPosition(position));

                txtCharacterNature.setText(nature.getName());
                txtCharacterNature.setVisibility(View.VISIBLE);
                spinnerNature.setVisibility(View.GONE);

                updatedCharacter.getNatures().clear();
                updatedCharacter.getNatures().add(nature);

                hasChanges = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    // Another spinner setting up method - refer to setUpDemeanorsSpinner for info
    public void setUpViceSpinner(RealmResults<Vice> vices) {
        ViceRealmAdapter adapter;

        adapter = new ViceRealmAdapter(getActivity(), vices, true);

        spinnerVice.setAdapter(adapter);

        for (int i = 0; i < vices.size(); i++) {
            Vice vice = vices.get(i);
            if (vice.getName().equals(character.getVices().get(0).getName())) {
                spinnerVice.setSelection(i);
                break;
            }
        }

        spinnerVice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vice vice = ((Vice) spinnerVice.getItemAtPosition(position));

                txtCharacterVice.setText(vice.getName());
                txtCharacterVice.setVisibility(View.VISIBLE);
                spinnerVice.setVisibility(View.GONE);

                updatedCharacter.getVices().clear();
                updatedCharacter.getVices().add(vice);

                hasChanges = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    // Another spinner setting up method - refer to setUpDemeanorsSpinner for info
    public void setUpVirtueSpinner(RealmResults<Virtue> virtues) {
        VirtueRealmAdapter adapter;

        adapter = new VirtueRealmAdapter(getActivity(), virtues, true);

        spinnerVirtue.setAdapter(adapter);

        for (int i = 0; i < virtues.size(); i++) {
            Virtue virtue = virtues.get(i);
            if (virtue.getName().equals(character.getVirtues().get(0).getName())) {
                spinnerVirtue.setSelection(i);
                break;
            }
        }

        spinnerVirtue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    Virtue virtue = ((Virtue) spinnerVirtue.getItemAtPosition(position));

                    txtCharacterVirtue.setText(virtue.getName());
                    txtCharacterVirtue.setVisibility(View.VISIBLE);
                    spinnerVirtue.setVisibility(View.GONE);

                    updatedCharacter.getVirtues().clear();
                    updatedCharacter.getVirtues().add(virtue);

                    hasChanges = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    // Triggered on fragment's onPause, via the presenter
    public void onPause() {
        // If there have been any changes...
        if (hasChanges) {
            // ...you post a new event for the model class to digest, bypassing the presenter
            bus.post(new UpdateCharacterEvent(updatedCharacter));
        }
    }

    @Override
    public void onTraitChanged(Object caller, int value) {
        // If this flag is not set, changes will not be saved
        hasChanges = true;

        // De-abstract object into widget
        ValueSetterWidget widget = (ValueSetterWidget) caller;
        // Retrieve enty associated with widget as tag
        Entry template = (Entry) widget.getTag();

        // Evaluates preference setting: if cheating is on, attributes can be edited at will,
        // regardless of available experience
        if (getPreferences().getBoolean(Constants.SETTING_CHEAT, false)) {
            widget.changeValue(value);
        } else {
            // Experience pool becomes whatever results from trying to spend experience; whether this
            // succeeds or fails is processed inside changeValue()
            // This is rather convoluted, but I cannot think of a better way
            experiencePool = widget.changeValue(value, experiencePool, widget.getPointCost());

            // Refresh text on the experience textView
            txtExperience.setText(String.valueOf(experiencePool));
        }

        try {
            // Try editing an existing entry to update
            editEntryToUpdate(template, widget.getCurrentValue());
        } catch (NoSuchElementException e) {
            // If we got a NSEE, it (probably, most likely - I know >.< ) means that there is no
            // entry that matches the parameters, so we create one
            addEntryToUpdate(template, widget.getCurrentValue());
        }

        // Notifies the list of experience spending widgets that there is a change in the experience
        // pool - just why is this outside the if-else?
        notifyExperienceSpenders();
    }

    /**
     * Method for adding a value for updating - nothing strange here, just create and add to list
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

    /**
     * Method for returning singleton instance of SharedPreferences
     * @return Preferences object containing app settings
     */
    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = getActivity().getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    /**
     * Callback from presenter; handles what happens when the 'Delete' button is tapped
     */
    public void onCharacterDelete() {
        // Just your run-of-the-mill Snackbar instantiation - nothing to see here
        final Snackbar snackbar = Snackbar.make(scrollCharView,
                getActivity().getString(R.string.alert_character_delete), Snackbar.LENGTH_SHORT);

        // Setting the action for the Snackbar
        snackbar.setAction("Delete", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Create event for character deletion, to be digested by model class
                        bus.post(new DeleteCharacterEvent(updatedCharacter));
                        // Pop back stack and remove this fragment
                        CharacterViewerView.this.getFragmentManager().popBackStack();
                    }
                });

        snackbar.show();
    }

    public static class DeleteCharacterEvent {
        public Character characterToDelete;

        DeleteCharacterEvent(Character character) {
            this.characterToDelete = character;
        }
    }

    public static class UpdateCharacterEvent {
        public Character characterToUpdate;

        UpdateCharacterEvent(Character character) {
            this.characterToUpdate = character;
        }
    }
}
