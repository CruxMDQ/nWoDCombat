package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
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
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.utils.Events;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    protected void setUpUI() {
        setUpValueSetter(valueSetterIntelligence, Constants.ATTR_INT, Constants.CONTENT_DESC_ATTR_MENTAL);
        setUpValueSetter(valueSetterWits, Constants.ATTR_WIT, Constants.CONTENT_DESC_ATTR_MENTAL);
        setUpValueSetter(valueSetterResolve, Constants.ATTR_RES, Constants.CONTENT_DESC_ATTR_MENTAL);

        setUpValueSetter(valueSetterStrength, Constants.ATTR_STR, Constants.CONTENT_DESC_ATTR_PHYSICAL);
        setUpValueSetter(valueSetterDexterity, Constants.ATTR_DEX, Constants.CONTENT_DESC_ATTR_PHYSICAL);
        setUpValueSetter(valueSetterStamina, Constants.ATTR_STA, Constants.CONTENT_DESC_ATTR_PHYSICAL);

        setUpValueSetter(valueSetterPresence, Constants.ATTR_PRE, Constants.CONTENT_DESC_ATTR_SOCIAL);
        setUpValueSetter(valueSetterManipulation, Constants.ATTR_MAN, Constants.CONTENT_DESC_ATTR_SOCIAL);
        setUpValueSetter(valueSetterComposure, Constants.ATTR_COM, Constants.CONTENT_DESC_ATTR_SOCIAL);

        setUpValueSetter(valueSetterAcademics, Constants.SKILL_ACADEMICS, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterComputer, Constants.SKILL_COMPUTER, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterCrafts, Constants.SKILL_CRAFTS, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterInvestigation, Constants.SKILL_INVESTIGATION, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterMedicine, Constants.SKILL_MEDICINE, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterOccult, Constants.SKILL_OCCULT, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterPolitics, Constants.SKILL_POLITICS, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterScience, Constants.SKILL_SCIENCE, Constants.CONTENT_DESC_SKILL_MENTAL);

        setUpValueSetter(valueSetterAthletics, Constants.SKILL_ATHLETICS, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterBrawl, Constants.SKILL_BRAWL, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterDrive, Constants.SKILL_DRIVE, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterFirearms, Constants.SKILL_FIREARMS, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterLarceny, Constants.SKILL_LARCENY, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterStealth, Constants.SKILL_STEALTH, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterSurvival, Constants.SKILL_SURVIVAL, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterWeaponry, Constants.SKILL_WEAPONRY, Constants.CONTENT_DESC_SKILL_PHYSICAL);

        setUpValueSetter(valueSetterAnimalKen, Constants.SKILL_ANIMAL_KEN, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterEmpathy, Constants.SKILL_EMPATHY, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterExpression, Constants.SKILL_EXPRESSION, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterIntimidation, Constants.SKILL_INTIMIDATION, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterPersuasion, Constants.SKILL_PERSUASION, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterSocialize, Constants.SKILL_SOCIALIZE, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterStreetwise, Constants.SKILL_STREETWISE, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterSubterfuge, Constants.SKILL_SUBTERFUGE, Constants.CONTENT_DESC_SKILL_SOCIAL);

        valueSetters.add(valueSetterIntelligence);
        valueSetters.add(valueSetterWits);
        valueSetters.add(valueSetterResolve);
        valueSetters.add(valueSetterStrength);
        valueSetters.add(valueSetterDexterity);
        valueSetters.add(valueSetterStamina);
        valueSetters.add(valueSetterPresence);
        valueSetters.add(valueSetterManipulation);
        valueSetters.add(valueSetterComposure);

        valueSetters.add(valueSetterAcademics);
        valueSetters.add(valueSetterAnimalKen);
        valueSetters.add(valueSetterAthletics);
        valueSetters.add(valueSetterBrawl);
        valueSetters.add(valueSetterComputer);
        valueSetters.add(valueSetterCrafts);
        valueSetters.add(valueSetterDrive);
        valueSetters.add(valueSetterEmpathy);
        valueSetters.add(valueSetterExpression);
        valueSetters.add(valueSetterFirearms);
        valueSetters.add(valueSetterIntimidation);
        valueSetters.add(valueSetterInvestigation);
        valueSetters.add(valueSetterLarceny);
        valueSetters.add(valueSetterMedicine);
        valueSetters.add(valueSetterOccult);
        valueSetters.add(valueSetterPersuasion);
        valueSetters.add(valueSetterPolitics);
        valueSetters.add(valueSetterScience);
        valueSetters.add(valueSetterSocialize);
        valueSetters.add(valueSetterStealth);
        valueSetters.add(valueSetterStreetwise);
        valueSetters.add(valueSetterSubterfuge);
        valueSetters.add(valueSetterSurvival);
        valueSetters.add(valueSetterWeaponry);

        valueSetters.add(valueSetterDefense);
        valueSetters.add(valueSetterHealth);
        valueSetters.add(valueSetterInitiative);
        valueSetters.add(valueSetterMorality);
        valueSetters.add(valueSetterSpeed);
        valueSetters.add(valueSetterWillpower);
    }

    @Override
    public void onTraitChanged(int value, String constant, String category) {
        bus.post(new Events.ValueChanged((value > 0), constant, category));
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

    void changeWidgetValue(String key, int value, int xpPool) {
        for (ValueSetter vs : valueSetters) {
            if (vs.getContentDescription().equals(key)) {
//                vs.changeValue(value);
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
        for (ValueSetter experienceSpender : valueSetters) {
            // What happens, so far, depends on what is coded on ValueSetterWidget (just why did
            // I code this on an interface as opposed to simply adding a method to the widget?)
            experienceSpender.onCharacterExperienceChanged(experiencePool);
        }
    }

    public void setValues(RealmList<Entry> entries) {
        for (Entry entry : entries) {
            for (ValueSetter setter : valueSetters) {
                try {
                    if (entry.getKey()
                        .equalsIgnoreCase(setter.getContentDescription().toString())) {
                        setter.setCurrentValue(entry);
                    }
                } catch (NullPointerException e) {
                    Log.e(this.getClass().toString(), "" + e.getMessage());
                }
            }
        }
    }

    private void setUpValueSetter(ValueSetter setter, String skillName, String skillCategory) {
        setter.setListener(this);
        setter.setContentDescription(skillName);
        setter.setTraitCategory(skillCategory);
    }
}
