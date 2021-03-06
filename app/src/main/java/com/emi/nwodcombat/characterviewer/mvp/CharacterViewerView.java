package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.model.pojos.Trait;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 12/04/2016.
 * Corrections to apply:
 * - One method per component to update
 * - The only operations performed here are component value changes
 * - Consider that the view is not testable
 * - Any 'new' operation done within a method is not testable
 * - Only public, package or protected methods are testable
 */
public class CharacterViewerView extends FragmentView implements OnTraitChangedListener
{
    // Otto bus is used to forward actions to the model
    private final Bus bus;

    // This stores all the components that will increase or decrease the experience score
    private Map<String, ValueSetter> valueSetters = new HashMap<>();

    @BindView(R.id.txtCharacterName) TextView txtCharacterName;
    @BindView(R.id.txtCharacterConcept) TextView txtCharacterConcept;
    @BindView(R.id.txtCharacterPlayer) TextView txtCharacterPlayer;

    @BindView(R.id.spinnerVirtue) Spinner spinnerVirtue;
    @BindView(R.id.spinnerVice) Spinner spinnerVice;
    @BindView(R.id.spinnerNature) Spinner spinnerNature;
    @BindView(R.id.spinnerDemeanor) Spinner spinnerDemeanor;

    @BindView(R.id.txtMentalAttrsTitle) TextView txtMentalAttrsTitle;
    @BindView(R.id.txtPhysicalAttrsTitle) TextView txtPhysicalAttrsTitle;
    @BindView(R.id.txtSocialAttrsTitle) TextView txtSocialAttrsTitle;

    @BindView(R.id.valueSetterInt) ValueSetter valueSetterIntelligence;
    @BindView(R.id.valueSetterWits) ValueSetter valueSetterWits;
    @BindView(R.id.valueSetterRes) ValueSetter valueSetterResolve;
    @BindView(R.id.valueSetterStr) ValueSetter valueSetterStrength;
    @BindView(R.id.valueSetterDex) ValueSetter valueSetterDexterity;
    @BindView(R.id.valueSetterSta) ValueSetter valueSetterStamina;
    @BindView(R.id.valueSetterPre) ValueSetter valueSetterPresence;
    @BindView(R.id.valueSetterMan) ValueSetter valueSetterManipulation;
    @BindView(R.id.valueSetterCom) ValueSetter valueSetterComposure;

    @BindView(R.id.txtMentalSkillsTitle) TextView txtMentalSkillsTitle;
    @BindView(R.id.txtPhysicalSkillsTitle) TextView txtPhysicalSkillsTitle;
    @BindView(R.id.txtSocialSkillsTitle) TextView txtSocialSkillsTitle;

    @BindView(R.id.valueSetterAcademics) ValueSetter valueSetterAcademics;
    @BindView(R.id.valueSetterComputer) ValueSetter valueSetterComputer;
    @BindView(R.id.valueSetterCrafts) ValueSetter valueSetterCrafts;
    @BindView(R.id.valueSetterInvestigation) ValueSetter valueSetterInvestigation;
    @BindView(R.id.valueSetterMedicine) ValueSetter valueSetterMedicine;
    @BindView(R.id.valueSetterOccult) ValueSetter valueSetterOccult;
    @BindView(R.id.valueSetterPolitics) ValueSetter valueSetterPolitics;
    @BindView(R.id.valueSetterScience) ValueSetter valueSetterScience;

    @BindView(R.id.valueSetterAthletics) ValueSetter valueSetterAthletics;
    @BindView(R.id.valueSetterBrawl) ValueSetter valueSetterBrawl;
    @BindView(R.id.valueSetterDrive) ValueSetter valueSetterDrive;
    @BindView(R.id.valueSetterFirearms) ValueSetter valueSetterFirearms;
    @BindView(R.id.valueSetterLarceny) ValueSetter valueSetterLarceny;
    @BindView(R.id.valueSetterStealth) ValueSetter valueSetterStealth;
    @BindView(R.id.valueSetterSurvival) ValueSetter valueSetterSurvival;
    @BindView(R.id.valueSetterWeaponry) ValueSetter valueSetterWeaponry;

    @BindView(R.id.valueSetterAnimalKen) ValueSetter valueSetterAnimalKen;
    @BindView(R.id.valueSetterEmpathy) ValueSetter valueSetterEmpathy;
    @BindView(R.id.valueSetterExpression) ValueSetter valueSetterExpression;
    @BindView(R.id.valueSetterIntimidation) ValueSetter valueSetterIntimidation;
    @BindView(R.id.valueSetterPersuasion) ValueSetter valueSetterPersuasion;
    @BindView(R.id.valueSetterSocialize) ValueSetter valueSetterSocialize;
    @BindView(R.id.valueSetterStreetwise) ValueSetter valueSetterStreetwise;
    @BindView(R.id.valueSetterSubterfuge) ValueSetter valueSetterSubterfuge;

    @BindView(R.id.valueSetterDefense) ValueSetter valueSetterDefense;
    @BindView(R.id.valueSetterHealth) ValueSetter valueSetterHealth;
    @BindView(R.id.valueSetterInitiative) ValueSetter valueSetterInitiative;
    @BindView(R.id.valueSetterMorality) ValueSetter valueSetterMorality;
    @BindView(R.id.valueSetterSpeed) ValueSetter valueSetterSpeed;
    @BindView(R.id.valueSetterWillpower) ValueSetter valueSetterWillpower;

    @BindView(R.id.txtExperience) TextView txtExperience;

    @BindView(R.id.scrollCharView) ScrollView scrollCharView;

    /**
     * Default constructor
     *
     * @param fragment Fragment to bind
     * @param bus      Bus object to manage events
     */
    public CharacterViewerView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
//        ButterKnife.bind(this, fragment.getView());
    }

    @Override
    public void onTraitChanged(int value, String constant, String kind, String category) {
        bus.post(new Events.ValueChanged((value > 0), constant, kind, category));
    }

    @Override
    public void onSpecialtyTapped(boolean isChecked, String constant, String category) {

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
    @SuppressWarnings("ConstantConditions")
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

    void changeWidgetValue(String key, int value) {
        for (ValueSetter vs : valueSetters.values()) {
            if (vs.getContentDescription().equals(key)) {
                vs.setValue(value);

                break;
            }
        }
    }

    void changeWidgetValue(String key, int value, int xpPool) {
        for (ValueSetter vs : valueSetters.values()) {
            if (vs.getContentDescription().equals(key)) {
                vs.setValue(value);

                txtExperience.setText(String.valueOf(xpPool));

                notifyExperienceSpenders(xpPool);

                break;
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
                bus.post(new Events.DemeanorChanged(position));
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
                bus.post(new Events.NatureChanged(position));
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
                bus.post(new Events.ViceChanged(position));
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
                bus.post(new Events.VirtueChanged(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setVirtuesSpinnerSelection(int index) {
        spinnerVirtue.setSelection(index);
    }

    /**
     * Method for triggering actions on all objects on the experienceSpenders watch list
     */
    public void notifyExperienceSpenders(int experiencePool) {
        for (ValueSetter experienceSpender : valueSetters.values()) {
            // What happens, so far, depends on what is coded on ValueSetterWidget (just why did
            // I code this on an interface as opposed to simply adding a method to the widget?)
            experienceSpender.onCharacterExperienceChanged(experiencePool);
        }
    }

    public void setValues(RealmList<Entry> entries) {
        for (Entry entry : entries) {
            for (ValueSetter setter : valueSetters.values()) {
                try {
                    if (entry.getKey()
                        .equalsIgnoreCase(setter.getTrait().getName())) {
                        setter.setCurrentValue(entry);
                    }
                } catch (NullPointerException e) {
                    Log.e(this.getClass().toString(), "" + e.getMessage());
                }
            }
        }
    }

    private void setUpValueSetter(ValueSetter setter, Trait trait) {
        setter.setListener(this);
        setter.setTrait(trait);
        valueSetters.put(trait.getName(), setter);
    }

    public void toggleEditionPanel(boolean isActive) {
        for (ValueSetter setter : valueSetters.values()) {
            if (setter.getListener() != null) {
                setter.toggleEditionPanel(isActive);
            }
        }
    }

    public void setValueLabel(String key, @Nullable String specialtyName) {
        ValueSetter setter = valueSetters.get(key);

        if (specialtyName != null) {
            setter.setLabel(key + "\n(" + specialtyName + ")");
        } else {
            setter.setLabel(key);
        }
    }

    public void updateStarButton(String key, boolean isChecked) {
        ValueSetter setter = valueSetters.get(key);

        setter.enableSpecialtyButton(isChecked);

        if (isChecked) {
            setter.changeSpecialtyButtonBackground(R.drawable.star, Constants.SKILL_SPECIALTY_LOADED);
        } else {
            setter.changeSpecialtyButtonBackground(R.drawable.star_outline, Constants.SKILL_SPECIALTY_EMPTY);
        }
    }

    public void setUpValueSetterIntelligence(Trait intelligence) {
        setUpValueSetter(valueSetterIntelligence, intelligence);
    }
    
    public void setUpValueSetterWits(Trait wits) {
        setUpValueSetter(valueSetterWits, wits);
    }
    
    public void setUpValueSetterResolve(Trait resolve) {
        setUpValueSetter(valueSetterResolve, resolve);
    }

    public void setUpValueSetterStrength(Trait strength) {
        setUpValueSetter(valueSetterStrength, strength);
    }

    public void setUpValueSetterDexterity(Trait dexterity) {
        setUpValueSetter(valueSetterDexterity, dexterity);
    }

    public void setUpValueSetterStamina(Trait stamina) {
        setUpValueSetter(valueSetterStamina, stamina);
    }

    public void setUpValueSetterPresence(Trait presence) {
        setUpValueSetter(valueSetterPresence, presence);
    }

    public void setUpValueSetterManipulation(Trait manipulation) {
        setUpValueSetter(valueSetterManipulation, manipulation);
    }

    public void setUpValueSetterComposure(Trait composure) {
        setUpValueSetter(valueSetterComposure, composure);
    }

    public void setUpValueSetterAcademics(Trait academics) {
        setUpValueSetter(valueSetterAcademics, academics);
    }

    public void setUpValueSetterComputer(Trait computer) {
        setUpValueSetter(valueSetterComputer, computer);
    }

    public void setUpValueSetterCrafts(Trait crafts) {
        setUpValueSetter(valueSetterCrafts, crafts);
    }

    public void setUpValueSetterInvestigation(Trait investigation) {
        setUpValueSetter(valueSetterInvestigation, investigation);
    }

    public void setUpValueSetterMedicine(Trait medicine) {
        setUpValueSetter(valueSetterMedicine, medicine);
    }

    public void setUpValueSetterOccult(Trait occult) {
        setUpValueSetter(valueSetterOccult, occult);
    }

    public void setUpValueSetterPolitics(Trait politics) {
        setUpValueSetter(valueSetterPolitics, politics);
    }

    public void setUpValueSetterScience(Trait science) {
        setUpValueSetter(valueSetterScience, science);
    }

    public void setUpValueSetterAthletics(Trait athletics) {
        setUpValueSetter(valueSetterAthletics, athletics);
    }

    public void setUpValueSetterBrawl(Trait brawl) {
        setUpValueSetter(valueSetterBrawl, brawl);
    }

    public void setUpValueSetterDrive(Trait drive) {
        setUpValueSetter(valueSetterDrive, drive);
    }

    public void setUpValueSetterLarceny(Trait larceny) {
        setUpValueSetter(valueSetterLarceny, larceny);
    }

    public void setUpValueSetterFirearms(Trait firearms) {
        setUpValueSetter(valueSetterFirearms, firearms);
    }

    public void setUpValueSetterStealth(Trait stealth) {
        setUpValueSetter(valueSetterStealth, stealth);
    }

    public void setUpValueSetterSurvival(Trait survival) {
        setUpValueSetter(valueSetterSurvival, survival);
    }

    public void setUpValueSetterWeaponry(Trait weaponry) {
        setUpValueSetter(valueSetterWeaponry, weaponry);
    }

    public void setUpValueSetterAnimalKen(Trait animalken) {
        setUpValueSetter(valueSetterAnimalKen, animalken);
    }

    public void setUpValueSetterEmpathy(Trait empathy) {
        setUpValueSetter(valueSetterEmpathy, empathy);
    }

    public void setUpValueSetterExpression(Trait expression) {
        setUpValueSetter(valueSetterExpression, expression);
    }

    public void setUpValueSetterIntimidation(Trait intimidation) {
        setUpValueSetter(valueSetterIntimidation, intimidation);
    }

    public void setUpValueSetterPersuasion(Trait persuasion) {
        setUpValueSetter(valueSetterPersuasion, persuasion);
    }

    public void setUpValueSetterSocialize(Trait socialize) {
        setUpValueSetter(valueSetterSocialize, socialize);
    }

    public void setUpValueSetterStreetwise(Trait streetwise) {
        setUpValueSetter(valueSetterStreetwise, streetwise);
    }

    public void setUpValueSetterSubterfuge(Trait subterfuge) {
        setUpValueSetter(valueSetterSubterfuge, subterfuge);
    }

    public void setUpValueSetterDefense(Trait defense) {
        setUpValueSetter(valueSetterDefense, defense);
    }

    public void setUpValueSetterHealth(Trait health) {
        setUpValueSetter(valueSetterHealth, health);
    }

    public void setUpValueSetterInitiative(Trait initiative) {
        setUpValueSetter(valueSetterInitiative, initiative);
    }

    public void setUpValueSetterMorality(Trait morality) {
        setUpValueSetter(valueSetterMorality, morality);
    }

    public void setUpValueSetterSpeed(Trait speed) {
        setUpValueSetter(valueSetterSpeed, speed);
    }

    public void setUpValueSetterWillpower(Trait willpower) {
        setUpValueSetter(valueSetterWillpower, willpower);
    }
}
