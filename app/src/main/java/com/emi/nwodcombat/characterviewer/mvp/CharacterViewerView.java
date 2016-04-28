package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
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

    private SharedPreferences preferences;

    private final Bus bus;
    private Character character;
    private Character updatedCharacter = new Character();

    private ArrayList<ExperienceSpender> experienceSpenders = new ArrayList<>();

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

    private boolean hasChanges = false;

    private int experiencePool = 0;

    public CharacterViewerView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
    }

    public void setUpView(Character queriedCharacter) {
        this.character = queriedCharacter;
        updatedCharacter.setId(queriedCharacter.getId());

        txtCharacterName.setText(
            character.getValue(Constants.CHARACTER_NAME, String.class).toString());
        txtCharacterConcept.setText(character.getValue(Constants.CHARACTER_CONCEPT, String.class).toString());
        txtCharacterPlayer.setText(character.getValue(Constants.CHARACTER_PLAYER, String.class).toString());

        txtCharacterVirtue.setText(character.getVirtues().first().getName());
        txtCharacterVice.setText(character.getVices().first().getName());
        txtCharacterNature.setText(character.getNatures().first().getName());
        txtCharacterDemeanor.setText(character.getDemeanors().first().getName());

        txtExperience.setText(character.getValue(Constants.CHARACTER_EXPERIENCE, Integer.class).toString());

        setUpValueSetters();
    }

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

        loadVSValues();
    }

    private void setUpValueSetter(ValueSetterWidget valueSetter, String valueConstant, boolean spendsExperience) {
        valueSetter.setListener(this);
        valueSetter.setContentDescription(valueConstant);
        if (spendsExperience) {
            experienceSpenders.add(valueSetter);
        }
    }

    private void loadVSValues() {
        valueSetterAcademics.setCurrentValue(character.getEntry(Constants.SKILL_ACADEMICS));
        valueSetterAnimalKen.setCurrentValue(character.getEntry(Constants.SKILL_ANIMAL_KEN));
        valueSetterAthletics.setCurrentValue(character.getEntry(Constants.SKILL_ATHLETICS));
        valueSetterBrawl.setCurrentValue(character.getEntry(Constants.SKILL_BRAWL));
        valueSetterComputer.setCurrentValue(character.getEntry(Constants.SKILL_COMPUTER));
        valueSetterCrafts.setCurrentValue(character.getEntry(Constants.SKILL_CRAFTS));
        valueSetterDrive.setCurrentValue(character.getEntry(Constants.SKILL_DRIVE));
        valueSetterEmpathy.setCurrentValue(character.getEntry(Constants.SKILL_EMPATHY));
        valueSetterExpression.setCurrentValue(character.getEntry(Constants.SKILL_EXPRESSION));
        valueSetterFirearms.setCurrentValue(character.getEntry(Constants.SKILL_FIREARMS));
        valueSetterIntimidation.setCurrentValue(character.getEntry(Constants.SKILL_INTIMIDATION));
        valueSetterInvestigation.setCurrentValue(character.getEntry(Constants.SKILL_INVESTIGATION));
        valueSetterLarceny.setCurrentValue(character.getEntry(Constants.SKILL_LARCENY));
        valueSetterMedicine.setCurrentValue(character.getEntry(Constants.SKILL_MEDICINE));
        valueSetterOccult.setCurrentValue(character.getEntry(Constants.SKILL_OCCULT));
        valueSetterPersuasion.setCurrentValue(character.getEntry(Constants.SKILL_PERSUASION));
        valueSetterPolitics.setCurrentValue(character.getEntry(Constants.SKILL_POLITICS));
        valueSetterScience.setCurrentValue(character.getEntry(Constants.SKILL_SCIENCE));
        valueSetterSocialize.setCurrentValue(character.getEntry(Constants.SKILL_SOCIALIZE));
        valueSetterStealth.setCurrentValue(character.getEntry(Constants.SKILL_STEALTH));
        valueSetterStreetwise.setCurrentValue(character.getEntry(Constants.SKILL_STREETWISE));
        valueSetterSubterfuge.setCurrentValue(character.getEntry(Constants.SKILL_SUBTERFUGE));
        valueSetterSurvival.setCurrentValue(character.getEntry(Constants.SKILL_SURVIVAL));
        valueSetterWeaponry.setCurrentValue(character.getEntry(Constants.SKILL_WEAPONRY));

        valueSetterIntelligence.setCurrentValue(character.getEntry(Constants.ATTR_INT));
        valueSetterWits.setCurrentValue(character.getEntry(Constants.ATTR_WIT));
        valueSetterResolve.setCurrentValue(character.getEntry(Constants.ATTR_RES));
        valueSetterStrength.setCurrentValue(character.getEntry(Constants.ATTR_STR));
        valueSetterDexterity.setCurrentValue(character.getEntry(Constants.ATTR_DEX));
        valueSetterStamina.setCurrentValue(character.getEntry(Constants.ATTR_STA));
        valueSetterPresence.setCurrentValue(character.getEntry(Constants.ATTR_PRE));
        valueSetterManipulation.setCurrentValue(character.getEntry(Constants.ATTR_MAN));
        valueSetterComposure.setCurrentValue(character.getEntry(Constants.ATTR_COM));
    }

    @OnClick(R.id.txtCharacterNature)
    public void onNatureClicked() {
        txtCharacterNature.setVisibility(View.GONE);
        spinnerNature.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txtCharacterDemeanor)
    public void onDemeanorClicked() {
        txtCharacterDemeanor.setVisibility(View.GONE);
        spinnerDemeanor.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txtCharacterVice)
    public void onViceClicked() {
        txtCharacterVice.setVisibility(View.GONE);
        spinnerVice.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txtCharacterVirtue)
    public void onVirtueClicked() {
        txtCharacterVirtue.setVisibility(View.GONE);
        spinnerVirtue.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnAddExp)
    public void onExperienceAdded() {
        experiencePool++;
        txtExperience.setText(String.valueOf(experiencePool));
        notifyExperienceSpenders();
    }

    @OnClick(R.id.btnRemoveExp)
    public void onExperienceRemoved() {
        if (experiencePool > 0) {
            experiencePool--;
            txtExperience.setText(String.valueOf(experiencePool));
            notifyExperienceSpenders();
        }
    }

    public void setUpDemeanorsSpinner(RealmResults<Demeanor> traits) {
        NaturesAdapter demeanors;

        demeanors = new NaturesAdapter(getActivity(), traits, true);

        spinnerDemeanor.setAdapter(demeanors);

        for (int i = 0; i < traits.size(); i++) {
            Demeanor demeanor = traits.get(i);
            if (demeanor.getName().equals(character.getDemeanors().get(0).getName())) {
                spinnerDemeanor.setSelection(i);
                break;
            }
        }

        spinnerDemeanor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Demeanor demeanor = ((Demeanor) spinnerDemeanor.getItemAtPosition(position));

                txtCharacterDemeanor.setText(demeanor.getName());
                txtCharacterDemeanor.setVisibility(View.VISIBLE);
                spinnerDemeanor.setVisibility(View.GONE);

                updatedCharacter.getDemeanors().clear();
                updatedCharacter.getDemeanors().add(demeanor);

                hasChanges = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

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

    public void onPause() {
        if (hasChanges) {
            bus.post(new UpdateCharacterEvent(updatedCharacter));
        }
    }

    @Override
    public void onTraitChanged(Object caller, int value) {
        hasChanges = true;

        ValueSetterWidget widget = (ValueSetterWidget) caller;

        if (getPreferences().getBoolean(Constants.SETTING_CHEAT, false)) {
            widget.changeValue(value, experiencePool);
        } else {
            experiencePool = widget.changeValue(value, experiencePool, widget.getPointCost());

            txtExperience.setText(String.valueOf(experiencePool));

            try {
                ArrayHelper.findEntry(updatedCharacter.getEntries(), Constants.FIELD_ID)
                    .setValue(String.valueOf(widget.getCurrentValue()));
            } catch (NoSuchElementException e) {
                updatedCharacter.getEntries().add(new Entry()
                    .setKey(widget.getContentDescription().toString())
                    .setType(Constants.FIELD_TYPE_INTEGER)
                    .setValue(String.valueOf(widget.getCurrentValue()))
                );
            }
//            updatedCharacter.getEntries().where().equalTo(Constants.FIELD_ID, ((Entry) widget.getTag()).getId()).findAll().first()
//                .setKey(widget.getContentDescription().toString())
//                .setType(Constants.FIELD_TYPE_INTEGER)
//                .setValue(String.valueOf(widget.getCurrentValue()));
            notifyExperienceSpenders();
        }
    }

    private void notifyExperienceSpenders() {
        for (ExperienceSpender experienceSpender : experienceSpenders) {
            experienceSpender.onCharacterExperienceChanged(experiencePool);
        }
    }

    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = getActivity().getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public static class UpdateCharacterEvent {
        public Character characterToUpdate;

        UpdateCharacterEvent(Character character) {
            this.characterToUpdate = character;
        }
    }
}
