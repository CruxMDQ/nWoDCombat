package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
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
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.widgets.ValueSetterWidget;
import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerView extends FragmentView implements OnTraitChangedListener {

    private final Bus bus;
    private Character character;
    private Character updatedCharacter = new Character();

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

    private boolean hasChanges = false;

    public CharacterViewerView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
    }

    public void setUpView(Character queriedCharacter) {
        this.character = queriedCharacter;
        updatedCharacter.setId(queriedCharacter.getId());

        txtCharacterName.setText(character.getValue(Constants.CHARACTER_NAME, String.class).toString());
        txtCharacterConcept.setText(character.getValue(Constants.CHARACTER_CONCEPT, String.class).toString());
        txtCharacterPlayer.setText(character.getValue(Constants.CHARACTER_PLAYER, String.class).toString());

        txtCharacterVirtue.setText(character.getVirtues().first().getName());
        txtCharacterVice.setText(character.getVices().first().getName());
        txtCharacterNature.setText(character.getNatures().first().getName());
        txtCharacterDemeanor.setText(character.getDemeanors().first().getName());

        setUpValueSetters();
    }

    private void setUpValueSetters() {
        valueSetterAcademics.setListener(this);
        valueSetterAcademics.setContentDescription(Constants.SKILL_ACADEMICS);
        valueSetterComputer.setListener(this);
        valueSetterComputer.setContentDescription(Constants.SKILL_COMPUTER);
        valueSetterCrafts.setListener(this);
        valueSetterCrafts.setContentDescription(Constants.SKILL_CRAFTS);
        valueSetterInvestigation.setListener(this);
        valueSetterInvestigation.setContentDescription(Constants.SKILL_INVESTIGATION);
        valueSetterMedicine.setListener(this);
        valueSetterMedicine.setContentDescription(Constants.SKILL_MEDICINE);
        valueSetterOccult.setListener(this);
        valueSetterOccult.setContentDescription(Constants.SKILL_OCCULT);
        valueSetterPolitics.setListener(this);
        valueSetterPolitics.setContentDescription(Constants.SKILL_POLITICS);
        valueSetterScience.setListener(this);
        valueSetterScience.setContentDescription(Constants.SKILL_SCIENCE);

        valueSetterAthletics.setListener(this);
        valueSetterAthletics.setContentDescription(Constants.SKILL_ATHLETICS);
        valueSetterBrawl.setListener(this);
        valueSetterBrawl.setContentDescription(Constants.SKILL_BRAWL);
        valueSetterDrive.setListener(this);
        valueSetterDrive.setContentDescription(Constants.SKILL_DRIVE);
        valueSetterFirearms.setListener(this);
        valueSetterFirearms.setContentDescription(Constants.SKILL_FIREARMS);
        valueSetterLarceny.setListener(this);
        valueSetterLarceny.setContentDescription(Constants.SKILL_LARCENY);
        valueSetterStealth.setListener(this);
        valueSetterStealth.setContentDescription(Constants.SKILL_STEALTH);
        valueSetterSurvival.setListener(this);
        valueSetterSurvival.setContentDescription(Constants.SKILL_SURVIVAL);
        valueSetterWeaponry.setListener(this);
        valueSetterWeaponry.setContentDescription(Constants.SKILL_WEAPONRY);

        valueSetterAnimalKen.setListener(this);
        valueSetterAnimalKen.setContentDescription(Constants.SKILL_ANIMAL_KEN);
        valueSetterEmpathy.setListener(this);
        valueSetterEmpathy.setContentDescription(Constants.SKILL_EMPATHY);
        valueSetterExpression.setListener(this);
        valueSetterExpression.setContentDescription(Constants.SKILL_EXPRESSION);
        valueSetterIntimidation.setListener(this);
        valueSetterIntimidation.setContentDescription(Constants.SKILL_INTIMIDATION);
        valueSetterPersuasion.setListener(this);
        valueSetterPersuasion.setContentDescription(Constants.SKILL_PERSUASION);
        valueSetterSocialize.setListener(this);
        valueSetterSocialize.setContentDescription(Constants.SKILL_SOCIALIZE);
        valueSetterStreetwise.setListener(this);
        valueSetterStreetwise.setContentDescription(Constants.SKILL_STREETWISE);
        valueSetterSubterfuge.setListener(this);
        valueSetterSubterfuge.setContentDescription(Constants.SKILL_SUBTERFUGE);

        loadVSValues();
    }

    private void loadVSValues() {
        valueSetterAcademics.setCurrentValue(character.getSkill(Constants.SKILL_ACADEMICS));
        valueSetterAnimalKen.setCurrentValue(character.getSkill(Constants.SKILL_ANIMAL_KEN));
        valueSetterAthletics.setCurrentValue(character.getSkill(Constants.SKILL_ATHLETICS));
        valueSetterBrawl.setCurrentValue(character.getSkill(Constants.SKILL_BRAWL));
        valueSetterComputer.setCurrentValue(character.getSkill(Constants.SKILL_COMPUTER));
        valueSetterCrafts.setCurrentValue(character.getSkill(Constants.SKILL_CRAFTS));
        valueSetterDrive.setCurrentValue(character.getSkill(Constants.SKILL_DRIVE));
        valueSetterEmpathy.setCurrentValue(character.getSkill(Constants.SKILL_EMPATHY));
        valueSetterExpression.setCurrentValue(character.getSkill(Constants.SKILL_EXPRESSION));
        valueSetterFirearms.setCurrentValue(character.getSkill(Constants.SKILL_FIREARMS));
        valueSetterIntimidation.setCurrentValue(character.getSkill(Constants.SKILL_INTIMIDATION));
        valueSetterInvestigation.setCurrentValue(character.getSkill(Constants.SKILL_INVESTIGATION));
        valueSetterLarceny.setCurrentValue(character.getSkill(Constants.SKILL_LARCENY));
        valueSetterMedicine.setCurrentValue(character.getSkill(Constants.SKILL_MEDICINE));
        valueSetterOccult.setCurrentValue(character.getSkill(Constants.SKILL_OCCULT));
        valueSetterPersuasion.setCurrentValue(character.getSkill(Constants.SKILL_PERSUASION));
        valueSetterPolitics.setCurrentValue(character.getSkill(Constants.SKILL_POLITICS));
        valueSetterScience.setCurrentValue(character.getSkill(Constants.SKILL_SCIENCE));
        valueSetterSocialize.setCurrentValue(character.getSkill(Constants.SKILL_SOCIALIZE));
        valueSetterStealth.setCurrentValue(character.getSkill(Constants.SKILL_STEALTH));
        valueSetterStreetwise.setCurrentValue(character.getSkill(Constants.SKILL_STREETWISE));
        valueSetterSubterfuge.setCurrentValue(character.getSkill(Constants.SKILL_SUBTERFUGE));
        valueSetterSurvival.setCurrentValue(character.getSkill(Constants.SKILL_SURVIVAL));
        valueSetterWeaponry.setCurrentValue(character.getSkill(Constants.SKILL_WEAPONRY));
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
            public void onNothingSelected(AdapterView<?> parent) { }
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

    }

    public static class UpdateCharacterEvent {
        public Character characterToUpdate;

        UpdateCharacterEvent(Character character) {
            this.characterToUpdate = character;
        }
    }
}
