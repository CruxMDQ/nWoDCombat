package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
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
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/04/2016.
 * Corrections to apply:
 * - One method per component to update
 * - The only operations performed here are component value changes
 * - Consider that the view is not testable
 * - Any 'new' operation done within a method is not testable
 * - Only public, package or protected methods are testable
 */
public class CharacterViewerView extends FragmentView //implements OnTraitChangedListener {
{

    // Used for deciding what to do whenever a ValueSetterWidget value is changed: if the 'cheat'
    // flag is on, experience is disregarded
    // TODO Move this to model class
    private SharedPreferences preferences;

    // Otto bus is used to forward actions to the model, bypassing the presenter
    // (Consideration: is it a good idea to bypass the presenter in the first place?)
    private final Bus bus;

    // Character object being viewed
    private Character character;

    // Object that will be used to update the character being viewed
    private Character updatedCharacter = new Character();

    // This stores all the components that will increase or decrease the experience score
    private ArrayList<ValueSetter> valueSetters = new ArrayList<>();

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

    @Bind(R.id.valueSetterInt) ValueSetter valueSetterIntelligence;
    @Bind(R.id.valueSetterWits) ValueSetter valueSetterWits;
    @Bind(R.id.valueSetterRes) ValueSetter valueSetterResolve;
    @Bind(R.id.valueSetterStr) ValueSetter valueSetterStrength;
    @Bind(R.id.valueSetterDex) ValueSetter valueSetterDexterity;
    @Bind(R.id.valueSetterSta) ValueSetter valueSetterStamina;
    @Bind(R.id.valueSetterPre) ValueSetter valueSetterPresence;
    @Bind(R.id.valueSetterMan) ValueSetter valueSetterManipulation;
    @Bind(R.id.valueSetterCom) ValueSetter valueSetterComposure;

    @Bind(R.id.txtMentalSkillsTitle) TextView txtMentalSkillsTitle;
    @Bind(R.id.txtPhysicalSkillsTitle) TextView txtPhysicalSkillsTitle;
    @Bind(R.id.txtSocialSkillsTitle) TextView txtSocialSkillsTitle;

    @Bind(R.id.valueSetterAcademics) ValueSetter valueSetterAcademics;
    @Bind(R.id.valueSetterComputer) ValueSetter valueSetterComputer;
    @Bind(R.id.valueSetterCrafts) ValueSetter valueSetterCrafts;
    @Bind(R.id.valueSetterInvestigation) ValueSetter valueSetterInvestigation;
    @Bind(R.id.valueSetterMedicine) ValueSetter valueSetterMedicine;
    @Bind(R.id.valueSetterOccult) ValueSetter valueSetterOccult;
    @Bind(R.id.valueSetterPolitics) ValueSetter valueSetterPolitics;
    @Bind(R.id.valueSetterScience) ValueSetter valueSetterScience;

    @Bind(R.id.valueSetterAthletics) ValueSetter valueSetterAthletics;
    @Bind(R.id.valueSetterBrawl) ValueSetter valueSetterBrawl;
    @Bind(R.id.valueSetterDrive) ValueSetter valueSetterDrive;
    @Bind(R.id.valueSetterFirearms) ValueSetter valueSetterFirearms;
    @Bind(R.id.valueSetterLarceny) ValueSetter valueSetterLarceny;
    @Bind(R.id.valueSetterStealth) ValueSetter valueSetterStealth;
    @Bind(R.id.valueSetterSurvival) ValueSetter valueSetterSurvival;
    @Bind(R.id.valueSetterWeaponry) ValueSetter valueSetterWeaponry;

    @Bind(R.id.valueSetterAnimalKen) ValueSetter valueSetterAnimalKen;
    @Bind(R.id.valueSetterEmpathy) ValueSetter valueSetterEmpathy;
    @Bind(R.id.valueSetterExpression) ValueSetter valueSetterExpression;
    @Bind(R.id.valueSetterIntimidation) ValueSetter valueSetterIntimidation;
    @Bind(R.id.valueSetterPersuasion) ValueSetter valueSetterPersuasion;
    @Bind(R.id.valueSetterSocialize) ValueSetter valueSetterSocialize;
    @Bind(R.id.valueSetterStreetwise) ValueSetter valueSetterStreetwise;
    @Bind(R.id.valueSetterSubterfuge) ValueSetter valueSetterSubterfuge;

    @Bind(R.id.valueSetterDefense) ValueSetter valueSetterDefense;
    @Bind(R.id.valueSetterHealth) ValueSetter valueSetterHealth;
    @Bind(R.id.valueSetterInitiative) ValueSetter valueSetterInitiative;
    @Bind(R.id.valueSetterMorality) ValueSetter valueSetterMorality;
    @Bind(R.id.valueSetterSpeed) ValueSetter valueSetterSpeed;
    @Bind(R.id.valueSetterWillpower) ValueSetter valueSetterWillpower;

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
        bus.post(new ExperiencePoolChangeEvent(true));
    }

    // Triggered when experience increases via tapping of the 'minus' button on the view
    @OnClick(R.id.btnRemoveExp)
    public void onExperienceRemoved() {
        bus.post(new ExperiencePoolChangeEvent(false));
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

    public ExperienceSpender setUpWidgetIntelligence(OnTraitChangedListener listener, Entry entry) {
        valueSetterIntelligence.setListener(listener);
        valueSetterIntelligence.setContentDescription(entry.getKey());
        valueSetterIntelligence.setCurrentValue(entry);
        valueSetters.add(valueSetterIntelligence);
        return valueSetterIntelligence;
    }

    public ExperienceSpender setUpWidgetWits(OnTraitChangedListener listener, Entry entry) {
        valueSetterWits.setListener(listener);
        valueSetterWits.setContentDescription(entry.getKey());
        valueSetterWits.setCurrentValue(entry);
        valueSetters.add(valueSetterWits);
        return valueSetterWits;
    }

    public ExperienceSpender setUpWidgetResolve(OnTraitChangedListener listener, Entry entry) {
        valueSetterResolve.setListener(listener);
        valueSetterResolve.setContentDescription(entry.getKey());
        valueSetterResolve.setCurrentValue(entry);
        valueSetters.add(valueSetterResolve);
        return valueSetterResolve;
    }

    public ExperienceSpender setUpWidgetStrength(OnTraitChangedListener listener, Entry entry) {
        valueSetterStrength.setListener(listener);
        valueSetterStrength.setContentDescription(entry.getKey());
        valueSetterStrength.setCurrentValue(entry);
        valueSetters.add(valueSetterStrength);
        return valueSetterStrength;
    }

    public ExperienceSpender setUpWidgetDexterity(OnTraitChangedListener listener, Entry entry) {
        valueSetterDexterity.setListener(listener);
        valueSetterDexterity.setContentDescription(entry.getKey());
        valueSetterDexterity.setCurrentValue(entry);
        valueSetters.add(valueSetterDexterity);
        return valueSetterDexterity;
    }

    public ExperienceSpender setUpWidgetStamina(OnTraitChangedListener listener, Entry entry) {
        valueSetterStamina.setListener(listener);
        valueSetterStamina.setContentDescription(entry.getKey());
        valueSetterStamina.setCurrentValue(entry);
        valueSetters.add(valueSetterStamina);
        return valueSetterStamina;
    }

    public ExperienceSpender setUpWidgetPresence(OnTraitChangedListener listener, Entry entry) {
        valueSetterPresence.setListener(listener);
        valueSetterPresence.setContentDescription(entry.getKey());
        valueSetterPresence.setCurrentValue(entry);
        valueSetters.add(valueSetterPresence);
        return valueSetterPresence;
    }

    public ExperienceSpender setUpWidgetManipulation(OnTraitChangedListener listener, Entry entry) {
        valueSetterManipulation.setListener(listener);
        valueSetterManipulation.setContentDescription(entry.getKey());
        valueSetterManipulation.setCurrentValue(entry);
        valueSetters.add(valueSetterManipulation);
        return valueSetterManipulation;
    }

    public ExperienceSpender setUpWidgetComposure(OnTraitChangedListener listener, Entry entry) {
        valueSetterComposure.setListener(listener);
        valueSetterComposure.setContentDescription(entry.getKey());
        valueSetterComposure.setCurrentValue(entry);
        valueSetters.add(valueSetterComposure);
        return valueSetterComposure;
    }

    public ExperienceSpender setUpWidgetAcademics(OnTraitChangedListener listener, Entry entry) {
        valueSetterAcademics.setListener(listener);
        valueSetterAcademics.setContentDescription(entry.getKey());
        valueSetterAcademics.setCurrentValue(entry);
        valueSetters.add(valueSetterAcademics);
        return valueSetterAcademics;
    }

    public ExperienceSpender setUpWidgetComputer(OnTraitChangedListener listener, Entry entry) {
        valueSetterComputer.setListener(listener);
        valueSetterComputer.setContentDescription(entry.getKey());
        valueSetterComputer.setCurrentValue(entry);
        valueSetters.add(valueSetterComputer);
        return valueSetterComputer;
    }

    public ExperienceSpender setUpWidgetCrafts(OnTraitChangedListener listener, Entry entry) {
        valueSetterCrafts.setListener(listener);
        valueSetterCrafts.setContentDescription(entry.getKey());
        valueSetterCrafts.setCurrentValue(entry);
        valueSetters.add(valueSetterCrafts);
        return valueSetterCrafts;
    }

    public ExperienceSpender setUpWidgetInvestigation(OnTraitChangedListener listener, Entry entry) {
        valueSetterInvestigation.setListener(listener);
        valueSetterInvestigation.setContentDescription(entry.getKey());
        valueSetterInvestigation.setCurrentValue(entry);
        valueSetters.add(valueSetterInvestigation);
        return valueSetterInvestigation;
    }

    public ExperienceSpender setUpWidgetMedicine(OnTraitChangedListener listener, Entry entry) {
        valueSetterMedicine.setListener(listener);
        valueSetterMedicine.setContentDescription(entry.getKey());
        valueSetterMedicine.setCurrentValue(entry);
        valueSetters.add(valueSetterMedicine);
        return valueSetterMedicine;
    }

    public ExperienceSpender setUpWidgetOccult(OnTraitChangedListener listener, Entry entry) {
        valueSetterOccult.setListener(listener);
        valueSetterOccult.setContentDescription(entry.getKey());
        valueSetterOccult.setCurrentValue(entry);
        valueSetters.add(valueSetterOccult);
        return valueSetterOccult;
    }

    public ExperienceSpender setUpWidgetPolitics(OnTraitChangedListener listener, Entry entry) {
        valueSetterPolitics.setListener(listener);
        valueSetterPolitics.setContentDescription(entry.getKey());
        valueSetterPolitics.setCurrentValue(entry);
        valueSetters.add(valueSetterPolitics);
        return valueSetterPolitics;
    }

    public ExperienceSpender setUpWidgetScience(OnTraitChangedListener listener, Entry entry) {
        valueSetterScience.setListener(listener);
        valueSetterScience.setContentDescription(entry.getKey());
        valueSetterScience.setCurrentValue(entry);
        valueSetters.add(valueSetterScience);
        return valueSetterScience;
    }

    public ExperienceSpender setUpWidgetAthletics(OnTraitChangedListener listener, Entry entry) {
        valueSetterAthletics.setListener(listener);
        valueSetterAthletics.setContentDescription(entry.getKey());
        valueSetterAthletics.setCurrentValue(entry);
        valueSetters.add(valueSetterAthletics);
        return valueSetterAthletics;
    }

    public ExperienceSpender setUpWidgetBrawl(OnTraitChangedListener listener, Entry entry) {
        valueSetterBrawl.setListener(listener);
        valueSetterBrawl.setContentDescription(entry.getKey());
        valueSetterBrawl.setCurrentValue(entry);
        valueSetters.add(valueSetterBrawl);
        return valueSetterBrawl;
    }

    public ExperienceSpender setUpWidgetDrive(OnTraitChangedListener listener, Entry entry) {
        valueSetterDrive.setListener(listener);
        valueSetterDrive.setContentDescription(entry.getKey());
        valueSetterDrive.setCurrentValue(entry);
        valueSetters.add(valueSetterDrive);
        return valueSetterDrive;
    }

    public ExperienceSpender setUpWidgetFirearms(OnTraitChangedListener listener, Entry entry) {
        valueSetterFirearms.setListener(listener);
        valueSetterFirearms.setContentDescription(entry.getKey());
        valueSetterFirearms.setCurrentValue(entry);
        valueSetters.add(valueSetterFirearms);
        return valueSetterFirearms;
    }

    public ExperienceSpender setUpWidgetLarceny(OnTraitChangedListener listener, Entry entry) {
        valueSetterLarceny.setListener(listener);
        valueSetterLarceny.setContentDescription(entry.getKey());
        valueSetterLarceny.setCurrentValue(entry);
        valueSetters.add(valueSetterLarceny);
        return valueSetterLarceny;
    }

    public ExperienceSpender setUpWidgetStealth(OnTraitChangedListener listener, Entry entry) {
        valueSetterStealth.setListener(listener);
        valueSetterStealth.setContentDescription(entry.getKey());
        valueSetterStealth.setCurrentValue(entry);
        valueSetters.add(valueSetterStealth);
        return valueSetterStealth;
    }

    public ExperienceSpender setUpWidgetSurvival(OnTraitChangedListener listener, Entry entry) {
        valueSetterSurvival.setListener(listener);
        valueSetterSurvival.setContentDescription(entry.getKey());
        valueSetterSurvival.setCurrentValue(entry);
        valueSetters.add(valueSetterSurvival);
        return valueSetterSurvival;
    }

    public ExperienceSpender setUpWidgetWeaponry(OnTraitChangedListener listener, Entry entry) {
        valueSetterWeaponry.setListener(listener);
        valueSetterWeaponry.setContentDescription(entry.getKey());
        valueSetterWeaponry.setCurrentValue(entry);
        valueSetters.add(valueSetterWeaponry);
        return valueSetterWeaponry;
    }

    public ExperienceSpender setUpWidgetAnimalKen(OnTraitChangedListener listener, Entry entry) {
        valueSetterAnimalKen.setListener(listener);
        valueSetterAnimalKen.setContentDescription(entry.getKey());
        valueSetterAnimalKen.setCurrentValue(entry);
        valueSetters.add(valueSetterAnimalKen);
        return valueSetterAnimalKen;
    }

    public ExperienceSpender setUpWidgetEmpathy(OnTraitChangedListener listener, Entry entry) {
        valueSetterEmpathy.setListener(listener);
        valueSetterEmpathy.setContentDescription(entry.getKey());
        valueSetterEmpathy.setCurrentValue(entry);
        valueSetters.add(valueSetterEmpathy);
        return valueSetterEmpathy;
    }

    public ExperienceSpender setUpWidgetExpression(OnTraitChangedListener listener, Entry entry) {
        valueSetterExpression.setListener(listener);
        valueSetterExpression.setContentDescription(entry.getKey());
        valueSetterExpression.setCurrentValue(entry);
        valueSetters.add(valueSetterExpression);
        return valueSetterExpression;
    }

    public ExperienceSpender setUpWidgetIntimidation(OnTraitChangedListener listener, Entry entry) {
        valueSetterIntimidation.setListener(listener);
        valueSetterIntimidation.setContentDescription(entry.getKey());
        valueSetterIntimidation.setCurrentValue(entry);
        valueSetters.add(valueSetterIntimidation);
        return valueSetterIntimidation;
    }

    public ExperienceSpender setUpWidgetPersuasion(OnTraitChangedListener listener, Entry entry) {
        valueSetterPersuasion.setListener(listener);
        valueSetterPersuasion.setContentDescription(entry.getKey());
        valueSetterPersuasion.setCurrentValue(entry);
        valueSetters.add(valueSetterPersuasion);
        return valueSetterPersuasion;
    }

    public ExperienceSpender setUpWidgetSocialize(OnTraitChangedListener listener, Entry entry) {
        valueSetterSocialize.setListener(listener);
        valueSetterSocialize.setContentDescription(entry.getKey());
        valueSetterSocialize.setCurrentValue(entry);
        valueSetters.add(valueSetterSocialize);
        return valueSetterSocialize;
    }

    public ExperienceSpender setUpWidgetStreetwise(OnTraitChangedListener listener, Entry entry) {
        valueSetterStreetwise.setListener(listener);
        valueSetterStreetwise.setContentDescription(entry.getKey());
        valueSetterStreetwise.setCurrentValue(entry);
        valueSetters.add(valueSetterStreetwise);
        return valueSetterStreetwise;
    }

    public ExperienceSpender setUpWidgetSubterfuge(OnTraitChangedListener listener, Entry entry) {
        valueSetterSubterfuge.setListener(listener);
        valueSetterSubterfuge.setContentDescription(entry.getKey());
        valueSetterSubterfuge.setCurrentValue(entry);
        valueSetters.add(valueSetterSubterfuge);
        return valueSetterSubterfuge;
    }

    public void setUpWidgetDefense(OnTraitChangedListener listener, Entry entry) {
        valueSetterDefense.setListener(listener);
        valueSetterDefense.setContentDescription(entry.getKey());
        valueSetterDefense.setCurrentValue(entry);
//        valueSetters.add(valueSetterDefense);
//        return valueSetterDefense;
    }

    public void setUpWidgetHealth(OnTraitChangedListener listener, Entry entry) {
        valueSetterHealth.setListener(listener);
        valueSetterHealth.setContentDescription(entry.getKey());
        valueSetterHealth.setCurrentValue(entry);
//        valueSetters.add(valueSetterHealth);
//        return valueSetterHealth;
    }

    public void setUpWidgetInitiative(OnTraitChangedListener listener, Entry entry) {
        valueSetterInitiative.setListener(listener);
        valueSetterInitiative.setContentDescription(entry.getKey());
        valueSetterInitiative.setCurrentValue(entry);
//        valueSetters.add(valueSetterInitiative);
//        return valueSetterInitiative;
    }

    public ExperienceSpender setUpWidgetMorality(OnTraitChangedListener listener, Entry entry) {
        valueSetterMorality.setListener(listener);
        valueSetterMorality.setContentDescription(entry.getKey());
        valueSetterMorality.setCurrentValue(entry);
        valueSetters.add(valueSetterMorality);
        return valueSetterMorality;
    }

    public void setUpWidgetSpeed(OnTraitChangedListener listener, Entry entry) {
        valueSetterSpeed.setListener(listener);
        valueSetterSpeed.setContentDescription(entry.getKey());
        valueSetterSpeed.setCurrentValue(entry);
//        valueSetters.add(valueSetterSpeed);
//        return valueSetterSpeed;
    }

    public ExperienceSpender setUpWidgetWillpower(OnTraitChangedListener listener, Entry entry) {
        valueSetterWillpower.setListener(listener);
        valueSetterWillpower.setContentDescription(entry.getKey());
        valueSetterWillpower.setCurrentValue(entry);
        valueSetters.add(valueSetterWillpower);
        return valueSetterWillpower;
    }

    public void setCharacterName(String characterName) {
        txtCharacterName.setText(characterName);
    }

    public void setCharacterConcept(String concept) {
        txtCharacterConcept.setText(concept);
    }

    public void setCharacterPlayer(String player) {
        txtCharacterPlayer.setText(player);
    }

    public void setCharacterVirtue(String firstVirtue) {
        txtCharacterVirtue.setText(firstVirtue);
    }

    public void setCharacterVice(String firstVice) {
        txtCharacterVice.setText(firstVice);
    }

    public void setCharacterNature(String firstNature) {
        txtCharacterNature.setText(firstNature);
    }

    public void setCharacterDemeanor(String firstDemeanor) {
        txtCharacterDemeanor.setText(firstDemeanor);
    }

    /*** Sets up the components that does the experience spending - in a way similar to but not
     * exactly equal to a ValueSetter (which invites again the question: is it worth handling
     * separately? Just how much experience is a character going to have at any time?)
     */
    public void setUpExperienceSpendingWidget(Entry experience) {
        txtExperience.setTag(experience);
        txtExperience.setText(experience.getValue());
    }

    public void setExperience(String experience) {
        txtExperience.setText(experience);
    }

    public int changeWidgetValue(String constant, int value, int experiencePool, boolean isCheating) {
        for (ValueSetter widget : valueSetters) {
            if (widget.getContentDescription().equals(constant)) {
                if (isCheating) {
                    widget.setCurrentValue(value);
                } else {
                    // Experience pool becomes whatever results from trying to spend experience; whether this
                    // succeeds or fails is processed inside changeValue()
                    // This is rather convoluted, but I cannot think of a better way
                    int experience = widget.changeValue(value, experiencePool, widget.getPointCost());

                    // Refresh text on the experience textView
                    setExperience(String.valueOf(experience));

                    return experience;
                }
            }
        }
        return experiencePool;
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

    public static class ExperiencePoolChangeEvent {
        public boolean isIncrease;

        ExperiencePoolChangeEvent(boolean isIncrease) { this.isIncrease = isIncrease; }
    }
}
