package com.emi.nwodcombat.charactercreator.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.CharacterCreatorHelper;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;
import com.emi.nwodcombat.widgets.ValueSetterWidget;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/8/16.
 */
public class SetMentalSkillsStep extends WizardStep implements PagerStep.ChildStep, OnTraitChangedListener {
    private int mentalPoints;
    private int currentMentalPool;

    @Bind(R.id.valueSetterAcademics) ValueSetterWidget valueSetterAcademics;
    @Bind(R.id.valueSetterComputer) ValueSetterWidget valueSetterComputer;
    @Bind(R.id.valueSetterCrafts) ValueSetterWidget valueSetterCrafts;
    @Bind(R.id.valueSetterInvestigation) ValueSetterWidget valueSetterInvestigation;
    @Bind(R.id.valueSetterMedicine) ValueSetterWidget valueSetterMedicine;
    @Bind(R.id.valueSetterOccult) ValueSetterWidget valueSetterOccult;
    @Bind(R.id.valueSetterPolitics) ValueSetterWidget valueSetterPolitics;
    @Bind(R.id.valueSetterScience) ValueSetterWidget valueSetterScience;

    @Bind(R.id.txtMentalSkillsTitle) TextView txtMentalSkillsTitle;

    public SetMentalSkillsStep() { characterCreatorHelper = CharacterCreatorHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
            getLayout(), container, false);

        setToolbarTitle(container, getToolbarTitle());

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);

        if (isVisible) {
            retrieveChoices();
            pagerMaster.checkStepIsComplete(false, this);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpUI();
    }

    @Override
    protected void setUpUI() {
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
    }

    @Override
    public void checkCompletionConditions() {
        pagerMaster.checkStepIsComplete(!hasLeftoverPoints(), this);
    }

    @Override
    public HashMap<String, Object> saveChoices() {
        HashMap<String, Object> output = new HashMap<>();

        int valueAcademics = valueSetterAcademics.getCurrentValue();
        int valueComputer = valueSetterComputer.getCurrentValue();
        int valueCrafts = valueSetterCrafts.getCurrentValue();
        int valueInvestigation = valueSetterInvestigation.getCurrentValue();
        int valueMedicine = valueSetterMedicine.getCurrentValue();
        int valueOccult = valueSetterOccult.getCurrentValue();
        int valuePolitics = valueSetterPolitics.getCurrentValue();
        int valueScience = valueSetterScience.getCurrentValue();

        output.put(Constants.SKILL_ACADEMICS, valueAcademics);
        output.put(Constants.SKILL_COMPUTER, valueComputer);
        output.put(Constants.SKILL_CRAFTS, valueCrafts);
        output.put(Constants.SKILL_INVESTIGATION, valueInvestigation);
        output.put(Constants.SKILL_MEDICINE, valueMedicine);
        output.put(Constants.SKILL_OCCULT, valueOccult);
        output.put(Constants.SKILL_POLITICS, valuePolitics);
        output.put(Constants.SKILL_SCIENCE, valueScience);

        return output;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_cat_skills_set);
    }

    @Override
    public int getLayout() {
        return R.layout.step_skill_set_mental;
    }

    @Override
    public void retrieveChoices() {
        mentalPoints = characterCreatorHelper.getInt(Constants.CONTENT_DESC_SKILL_MENTAL, 0);
        currentMentalPool = characterCreatorHelper.getInt(Constants.POOL_SKILL_MENTAL, mentalPoints);

        setPoolTitle(getString(R.string.cat_mental), currentMentalPool, txtMentalSkillsTitle);

        valueSetterAcademics.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_ACADEMICS, 0));
        valueSetterComputer.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_COMPUTER, 0));
        valueSetterCrafts.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_CRAFTS, 0));
        valueSetterInvestigation.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_INVESTIGATION, 0));
        valueSetterMedicine.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_MEDICINE, 0));
        valueSetterOccult.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_OCCULT, 0));
        valueSetterPolitics.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_POLITICS, 0));
        valueSetterScience.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_SCIENCE, 0));
    }

    @Override
    public void onTraitChanged(Object caller, int value) {
        ValueSetterWidget widget = (ValueSetterWidget) caller;

        currentMentalPool = widget.changeValue(value, currentMentalPool);
        setPoolTitle(getString(R.string.cat_mental), currentMentalPool, txtMentalSkillsTitle);
        characterCreatorHelper.putInt(Constants.POOL_SKILL_MENTAL, currentMentalPool);

        checkCompletionConditions();
    }

    private void setPoolTitle(String titleString, int pool, TextView textView) {
        String text;
        if (pool > 0) {
            text = titleString +
                " (" +
                pool +
                " points remaining)";
        } else {
            text = titleString + " (no points remaining)";
        }
        textView.setText(text);
    }

    public boolean hasLeftoverPoints() {
        return currentMentalPool > 0;
    }
}
