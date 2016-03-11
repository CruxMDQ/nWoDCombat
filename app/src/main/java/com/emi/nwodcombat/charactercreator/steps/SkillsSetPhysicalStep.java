package com.emi.nwodcombat.charactercreator.steps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
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
public class SkillsSetPhysicalStep extends WizardStep implements PagerStep.ChildStep, OnTraitChangedListener {
    private SharedPreferences preferences;

    private int physicalPoints;
    private int currentPhysicalPool;

    @Bind(R.id.valueSetterAthletics) ValueSetterWidget valueSetterAthletics;
    @Bind(R.id.valueSetterBrawl) ValueSetterWidget valueSetterBrawl;
    @Bind(R.id.valueSetterDrive) ValueSetterWidget valueSetterDrive;
    @Bind(R.id.valueSetterFirearms) ValueSetterWidget valueSetterFirearms;
    @Bind(R.id.valueSetterLarceny) ValueSetterWidget valueSetterLarceny;
    @Bind(R.id.valueSetterStealth) ValueSetterWidget valueSetterStealth;
    @Bind(R.id.valueSetterSurvival) ValueSetterWidget valueSetterSurvival;
    @Bind(R.id.valueSetterWeaponry) ValueSetterWidget valueSetterWeaponry;

    @Bind(R.id.txtPhysicalSkillsTitle) TextView txtPhysicalSkillsTitle;

    public SkillsSetPhysicalStep() { super();
    }

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
        physicalPoints = characterCreatorHelper.getInt(Constants.CONTENT_DESC_SKILL_PHYSICAL, 0);

        currentPhysicalPool = characterCreatorHelper.getInt(Constants.POOL_SKILL_PHYSICAL, physicalPoints);

        if (!getPreferences().getBoolean(Constants.SETTING_CHEAT, false)) {
            setPoolTitle(getString(R.string.cat_physical), currentPhysicalPool, txtPhysicalSkillsTitle);
        } else {
            txtPhysicalSkillsTitle.setText(getString(R.string.cat_physical));
        }

        valueSetterAthletics.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_ATHLETICS, 0));
        valueSetterBrawl.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_BRAWL, 0));
        valueSetterDrive.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_DRIVE, 0));
        valueSetterFirearms.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_FIREARMS, 0));
        valueSetterLarceny.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_LARCENY, 0));
        valueSetterStealth.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_STEALTH, 0));
        valueSetterSurvival.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_SURVIVAL, 0));
        valueSetterWeaponry.setCurrentValue(characterCreatorHelper.getInt(Constants.SKILL_WEAPONRY, 0));
    }

    @Override
    public void onTraitChanged(Object caller, int value) {
        ValueSetterWidget widget = (ValueSetterWidget) caller;

        if (!getPreferences().getBoolean(Constants.SETTING_CHEAT, false)) {
            currentPhysicalPool = widget.changeValue(value, currentPhysicalPool);
            setPoolTitle(getString(R.string.cat_physical), currentPhysicalPool, txtPhysicalSkillsTitle);
            characterCreatorHelper.putInt(Constants.POOL_SKILL_MENTAL, currentPhysicalPool);
        } else {
            widget.changeValue(value, currentPhysicalPool);
        }

        checkCompletionConditions();

    }

    @Override
    protected void setUpUI() {
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
    }

    @Override
    public void checkCompletionConditions() {
        pagerMaster.checkStepIsComplete(!hasLeftoverPoints(), this);
    }

    @Override
    public HashMap<String, Object> saveChoices() {
        HashMap<String, Object> output = new HashMap<>();

        int valueAthletics = valueSetterAthletics.getCurrentValue();
        int valueBrawl = valueSetterBrawl.getCurrentValue();
        int valueDrive = valueSetterDrive.getCurrentValue();
        int valueFirearms = valueSetterFirearms.getCurrentValue();
        int valueLarceny = valueSetterLarceny.getCurrentValue();
        int valueStealth = valueSetterStealth.getCurrentValue();
        int valueSurvival = valueSetterSurvival.getCurrentValue();
        int valueWeaponry = valueSetterWeaponry.getCurrentValue();

        output.put(Constants.SKILL_ATHLETICS, valueAthletics);
        output.put(Constants.SKILL_BRAWL, valueBrawl);
        output.put(Constants.SKILL_DRIVE, valueDrive);
        output.put(Constants.SKILL_FIREARMS, valueFirearms);
        output.put(Constants.SKILL_LARCENY, valueLarceny);
        output.put(Constants.SKILL_STEALTH, valueStealth);
        output.put(Constants.SKILL_SURVIVAL, valueSurvival);
        output.put(Constants.SKILL_WEAPONRY, valueWeaponry);

        return output;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_points_set);
    }

    @Override
    public int getLayout() {
        return R.layout.step_skill_set_physical;
    }

    public boolean hasLeftoverPoints() {
        return !getPreferences().getBoolean(Constants.SETTING_CHEAT, false) && currentPhysicalPool > 0;
    }

    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = getContext().getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
        }
        return preferences;
    }
}
