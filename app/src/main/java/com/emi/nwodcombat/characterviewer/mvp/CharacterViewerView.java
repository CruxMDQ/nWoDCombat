package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.adapters.DemeanorsAdapter;
import com.emi.nwodcombat.adapters.NaturesAdapter;
import com.emi.nwodcombat.adapters.VicesAdapter;
import com.emi.nwodcombat.adapters.VirtuesAdapter;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.interfaces.ExperienceSpender;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.utils.Events;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    // Otto bus is used to forward actions to the model
    private final Bus bus;

    // This stores all the components that will increase or decrease the experience score
    private ArrayList<ValueSetter> valueSetters = new ArrayList<>();

    @Bind(R.id.txtCharacterName) TextView txtCharacterName;
    @Bind(R.id.txtCharacterConcept) TextView txtCharacterConcept;
    @Bind(R.id.txtCharacterPlayer) TextView txtCharacterPlayer;

    @Bind(R.id.spinnerVirtue) Spinner spinnerVirtue;
    @Bind(R.id.spinnerVice) Spinner spinnerVice;
    @Bind(R.id.spinnerNature) Spinner spinnerNature;
    @Bind(R.id.spinnerDemeanor) Spinner spinnerDemeanor;

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
     *
     * @param fragment Fragment to bind
     * @param bus      Bus object to manage events
     */
    public CharacterViewerView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
    }

    // Triggered when experience increases via tapping of the 'plus' button on the view
    @OnClick(R.id.btnAddExp)
    public void onExperienceAdded() {
        bus.post(new Events.ExperiencePoolChanged(true));
    }

    // Triggered when experience increases via tapping of the 'minus' button on the view
    @OnClick(R.id.btnRemoveExp)
    public void onExperienceRemoved() {
        bus.post(new Events.ExperiencePoolChanged(false));
    }

    /**
     * Callback from presenter; handles what happens when the 'Delete' button is tapped
     *
     * @param id
     */
    public void showDeleteSnackbar(final long id) {
        // Just your run-of-the-mill Snackbar instantiation - nothing to see here
        final Snackbar snackbar = Snackbar.make(scrollCharView,
                getActivity().getString(R.string.alert_character_delete), Snackbar.LENGTH_SHORT);

        // Setting the action for the Snackbar
        snackbar.setAction("Delete", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create event for character deletion, to be digested by model class
                bus.post(new Events.CharacterDeleted(id));
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

    /***
     * Sets up the components that does the experience spending - in a way similar to but not
     * exactly equal to a ValueSetter (which invites again the question: is it worth handling
     * separately? Just how much experience is a character going to have at any time?)
     */
    public void setupExperienceSpendingWidget(Entry experience) {
        txtExperience.setTag(experience);
        txtExperience.setText(experience.getValue());
    }

    public void setExperience(String experience) {
        txtExperience.setText(experience);
    }

    // TODO VSM too much complex for a view.
    public int changeWidgetValue(String constant, int value, int experiencePool) {
        for (ValueSetter widget : valueSetters) {
            if (widget.getContentDescription().equals(constant)) {
                // Experience pool becomes whatever results from trying to spend experience; whether this
                // succeeds or fails is processed inside changeValue()
                // This is rather convoluted, but I cannot think of a better way
                // TODO VSM move this logic to the presenter
                int experience = widget.changeValue(value, experiencePool, widget.getPointCost());

                // Refresh text on the experience textView
                setExperience(String.valueOf(experience));

                return experience;
            }
        }
        return experiencePool;
    }

    public void cheatingWidgetValue(String constant, int value) {
        for (ValueSetter widget : valueSetters) {
            if (widget.getContentDescription().equals(constant)) {
                widget.setCurrentValue(value);
            }
        }
    }

    public void setDemeanorsSpinnerAdapter(DemeanorsAdapter demeanors) {
        spinnerDemeanor.setAdapter(demeanors);

        // Vanilla listener setting - could have been managed as a parameter, except for one of one
        // of the variables requiring that it be final, don't recall which now
        spinnerDemeanor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bus.post(new Events.DemeanorTraitChanged(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void setDemeanorsSpinnerSelection(int index) {
        spinnerDemeanor.setSelection(index);
    }

    public void setNaturesSpinnerAdapter(NaturesAdapter natures) {
        spinnerNature.setAdapter(natures);

        spinnerNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bus.post(new Events.NatureTraitChanged(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setNaturesSpinnerSelection(int index) {
        spinnerNature.setSelection(index);
    }

    public void setVicesSpinnerAdapter(VicesAdapter vices) {
        spinnerVice.setAdapter(vices);

        spinnerVice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bus.post(new Events.ViceTraitChanged(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setVicesSpinnerSelection(int index) {
        spinnerVice.setSelection(index);
    }

    public void setVirtuesSpinnerAdapter(VirtuesAdapter virtues) {
        spinnerVirtue.setAdapter(virtues);

        spinnerVirtue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bus.post(new Events.VirtueTraitChanged(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setVirtuesSpinnerSelection(int index) {
        spinnerVirtue.setSelection(index);
    }

}
