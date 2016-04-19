package com.emi.nwodcombat.charactercreator.steps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;
import com.emi.nwodcombat.widgets.ValueSetterWidget;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/9/16.
 */
public class SkillsSetSocialStep extends WizardStep implements PagerStep.ChildStep, OnTraitChangedListener{
    private SharedPreferences preferences;

    private int socialPoints;
    private int currentSocialPool;

    @Bind(R.id.valueSetterAnimalKen) ValueSetterWidget valueSetterAnimalKen;
    @Bind(R.id.valueSetterEmpathy) ValueSetterWidget valueSetterEmpathy;
    @Bind(R.id.valueSetterExpression) ValueSetterWidget valueSetterExpression;
    @Bind(R.id.valueSetterIntimidation) ValueSetterWidget valueSetterIntimidation;
    @Bind(R.id.valueSetterPersuasion) ValueSetterWidget valueSetterPersuasion;
    @Bind(R.id.valueSetterSocialize) ValueSetterWidget valueSetterSocialize;
    @Bind(R.id.valueSetterStreetwise) ValueSetterWidget valueSetterStreetwise;
    @Bind(R.id.valueSetterSubterfuge) ValueSetterWidget valueSetterSubterfuge;

    @Bind(R.id.txtSocialSkillsTitle) TextView txtSocialSkillsTitle;

    public SkillsSetSocialStep() { super(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
            getLayout(), container, false);

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
    public void retrieveChoices() {
        socialPoints = characterCreatorHelper.getInt(Constants.CONTENT_DESC_SKILL_SOCIAL, 0);

        currentSocialPool = characterCreatorHelper.getInt(Constants.POOL_SKILL_SOCIAL, socialPoints);

        if (!getPreferences().getBoolean(Constants.SETTING_CHEAT, false)) {
            setPoolTitle(getString(R.string.cat_social), currentSocialPool, txtSocialSkillsTitle);
        } else {
            txtSocialSkillsTitle.setText(getString(R.string.cat_social));
        }

        valueSetterAnimalKen.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_ANIMAL_KEN, 0));
        valueSetterEmpathy.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_EMPATHY, 0));
        valueSetterExpression.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_EXPRESSION, 0));
        valueSetterIntimidation.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_INTIMIDATION, 0));
        valueSetterPersuasion.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_PERSUASION, 0));
        valueSetterSocialize.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_SOCIALIZE, 0));
        valueSetterStreetwise.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_STREETWISE, 0));
        valueSetterSubterfuge.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_SUBTERFUGE, 0));

    }

    @Override
    public boolean hasLeftoverPoints() {
        return !getPreferences().getBoolean(Constants.SETTING_CHEAT, false) && currentSocialPool > 0;
    }

    @Override
    public void onTraitChanged(Object caller, int value) {
        ValueSetterWidget widget = (ValueSetterWidget) caller;

        if (!getPreferences().getBoolean(Constants.SETTING_CHEAT, false)) {
            currentSocialPool = widget.changeValue(value, currentSocialPool);
            setPoolTitle(getString(R.string.cat_physical), currentSocialPool, txtSocialSkillsTitle);
            characterCreatorHelper.putInt(Constants.POOL_SKILL_MENTAL, currentSocialPool);
        } else {
            widget.changeValue(value, currentSocialPool);
        }

        checkCompletionConditions();
    }

    @Override
    protected void setUpUI() {
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
    }

    @Override
    public void checkCompletionConditions() {
        pagerMaster.checkStepIsComplete(!hasLeftoverPoints(), this);
    }

    @Override
    public HashMap<String, Object> saveChoices() {
        HashMap<String, Object> output = new HashMap<>();

        int valueAnimalKen = valueSetterAnimalKen.getCurrentValue();
        int valueEmpathy = valueSetterEmpathy.getCurrentValue();
        int valueExpression = valueSetterExpression.getCurrentValue();
        int valueIntimidation = valueSetterIntimidation.getCurrentValue();
        int valuePersuasion = valueSetterPersuasion.getCurrentValue();
        int valueSocialize = valueSetterSocialize.getCurrentValue();
        int valueStreetwise = valueSetterStreetwise.getCurrentValue();
        int valueSubterfuge = valueSetterSubterfuge.getCurrentValue();

        output.put(Constants.SKILL_ANIMAL_KEN, valueAnimalKen);
        output.put(Constants.SKILL_EMPATHY, valueEmpathy);
        output.put(Constants.SKILL_EXPRESSION, valueExpression);
        output.put(Constants.SKILL_INTIMIDATION, valueIntimidation);
        output.put(Constants.SKILL_PERSUASION, valuePersuasion);
        output.put(Constants.SKILL_SOCIALIZE, valueSocialize);
        output.put(Constants.SKILL_STREETWISE, valueStreetwise);
        output.put(Constants.SKILL_SUBTERFUGE, valueSubterfuge);

        return output;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_points_set);
    }

    @Override
    public int getLayout() {
        return R.layout.step_skill_set_social;
    }

    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = getActivity().getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
        }
        return preferences;
    }
}
