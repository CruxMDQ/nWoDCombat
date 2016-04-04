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
import com.emi.nwodcombat.charactercreator.interfaces.PagerMaster;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;
import com.emi.nwodcombat.widgets.ValueSetterWidget;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class AttrSettingStep extends WizardStep implements OnTraitChangedListener, PagerStep.ChildStep {

    private SharedPreferences preferences;

    private int mentalPoints;
    private int currentMentalPool;
    private int physicalPoints;
    private int currentPhysicalPool;
    private int socialPoints;
    private int currentSocialPool;

    @Bind(R.id.valueSetterInt) ValueSetterWidget valueSetterIntelligence;
    @Bind(R.id.valueSetterWits) ValueSetterWidget valueSetterWits;
    @Bind(R.id.valueSetterRes) ValueSetterWidget valueSetterResolve;
    @Bind(R.id.valueSetterStr) ValueSetterWidget valueSetterStrength;
    @Bind(R.id.valueSetterDex) ValueSetterWidget valueSetterDexterity;
    @Bind(R.id.valueSetterSta) ValueSetterWidget valueSetterStamina;
    @Bind(R.id.valueSetterPre) ValueSetterWidget valueSetterPresence;
    @Bind(R.id.valueSetterMan) ValueSetterWidget valueSetterManipulation;
    @Bind(R.id.valueSetterCom) ValueSetterWidget valueSetterComposure;

    @Bind(R.id.txtPoolMental) TextView txtPoolMental;
    @Bind(R.id.txtPoolPhysical) TextView txtPoolPhysical;
    @Bind(R.id.txtPoolSocial) TextView txtPoolSocial;
    
    public AttrSettingStep() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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

    protected void setUpUI() {
        valueSetterIntelligence.setListener(this);
        valueSetterIntelligence.setContentDescription(Constants.ATTR_INT);
        valueSetterWits.setListener(this);
        valueSetterWits.setContentDescription(Constants.ATTR_WIT);
        valueSetterResolve.setListener(this);
        valueSetterResolve.setContentDescription(Constants.ATTR_RES);
        valueSetterStrength.setListener(this);
        valueSetterStrength.setContentDescription(Constants.ATTR_STR);
        valueSetterDexterity.setListener(this);
        valueSetterDexterity.setContentDescription(Constants.ATTR_DEX);
        valueSetterStamina.setListener(this);
        valueSetterStamina.setContentDescription(Constants.ATTR_STA);
        valueSetterPresence.setListener(this);
        valueSetterPresence.setContentDescription(Constants.ATTR_PRE);
        valueSetterManipulation.setListener(this);
        valueSetterManipulation.setContentDescription(Constants.ATTR_MAN);
        valueSetterComposure.setListener(this);
        valueSetterComposure.setContentDescription(Constants.ATTR_MAN);
    }

    @Override
    public void onTraitChanged(Object caller, int value) {
        ValueSetterWidget widget = (ValueSetterWidget) caller;

        if (!getPreferences().getBoolean(Constants.SETTING_CHEAT, false)) {
            switch (widget.getTraitCategory()) {
                case Constants.CONTENT_DESC_ATTR_MENTAL: {
                    currentMentalPool = widget.changeValue(value, currentMentalPool);
                    setPoolTitle(getString(R.string.cat_mental), currentMentalPool, txtPoolMental);
                    characterCreatorHelper.putInt(Constants.POOL_ATTR_MENTAL, currentMentalPool);
                    break;
                }
                case Constants.CONTENT_DESC_ATTR_PHYSICAL: {
                    currentPhysicalPool = widget.changeValue(value, currentPhysicalPool);
                    setPoolTitle(getString(R.string.cat_physical), currentPhysicalPool, txtPoolPhysical);
                    characterCreatorHelper.putInt(Constants.POOL_ATTR_PHYSICAL, currentPhysicalPool);
                    break;
                }
                case Constants.CONTENT_DESC_ATTR_SOCIAL: {
                    currentSocialPool = widget.changeValue(value, currentSocialPool);
                    setPoolTitle(getString(R.string.cat_social), currentSocialPool, txtPoolSocial);
                    characterCreatorHelper.putInt(Constants.POOL_ATTR_SOCIAL, currentSocialPool);
                    break;
                }
            }
        } else {
            switch (widget.getTraitCategory()) {
                case Constants.CONTENT_DESC_ATTR_MENTAL: {
                    widget.changeValue(value, currentMentalPool);
                    break;
                }
                case Constants.CONTENT_DESC_ATTR_PHYSICAL: {
                    widget.changeValue(value, currentPhysicalPool);
                    break;
                }
                case Constants.CONTENT_DESC_ATTR_SOCIAL: {
                    widget.changeValue(value, currentSocialPool);
                    break;
                }
            }
        }

        characterCreatorHelper.putInt(widget.getContentDescription().toString(), widget.getCurrentValue());

        checkCompletionConditions();
    }

    public boolean hasLeftoverPoints() {
        return !getPreferences().getBoolean(Constants.SETTING_CHEAT, false) && (currentMentalPool > 0 || currentPhysicalPool > 0 || currentSocialPool > 0);
    }

    @Override
    public HashMap<String, Object> saveChoices() {
        HashMap<String, Object> output = new HashMap<>();

        int valueInt = valueSetterIntelligence.getCurrentValue();
        int valueWit = valueSetterWits.getCurrentValue();
        int valueRes = valueSetterResolve.getCurrentValue();
        int valueStr = valueSetterStrength.getCurrentValue();
        int valueDex = valueSetterDexterity.getCurrentValue();
        int valueSta = valueSetterStamina.getCurrentValue();
        int valuePre = valueSetterPresence.getCurrentValue();
        int valueMan = valueSetterManipulation.getCurrentValue();
        int valueCom = valueSetterComposure.getCurrentValue();

        output.put(Constants.ATTR_INT, valueInt);
        output.put(Constants.ATTR_WIT, valueWit);
        output.put(Constants.ATTR_RES, valueRes);
        output.put(Constants.ATTR_STR, valueStr);
        output.put(Constants.ATTR_DEX, valueDex);
        output.put(Constants.ATTR_STA, valueSta);
        output.put(Constants.ATTR_PRE, valuePre);
        output.put(Constants.ATTR_MAN, valueMan);
        output.put(Constants.ATTR_COM, valueCom);

        return output;
    }

    @Override
    public void retrieveChoices() {
        mentalPoints = characterCreatorHelper.getInt(Constants.CONTENT_DESC_ATTR_MENTAL, 3);
        physicalPoints = characterCreatorHelper.getInt(Constants.CONTENT_DESC_ATTR_PHYSICAL, 3);
        socialPoints = characterCreatorHelper.getInt(Constants.CONTENT_DESC_ATTR_SOCIAL, 3);
        currentMentalPool = characterCreatorHelper.getInt(Constants.POOL_ATTR_MENTAL, mentalPoints);
        currentPhysicalPool = characterCreatorHelper.getInt(Constants.POOL_ATTR_PHYSICAL, physicalPoints);
        currentSocialPool = characterCreatorHelper.getInt(Constants.POOL_ATTR_SOCIAL, socialPoints);

        if (!getPreferences().getBoolean(Constants.SETTING_CHEAT, false)) {
            setPoolTitle(getString(R.string.cat_mental), currentMentalPool, txtPoolMental);
            setPoolTitle(getString(R.string.cat_physical), currentPhysicalPool, txtPoolPhysical);
            setPoolTitle(getString(R.string.cat_social), currentSocialPool, txtPoolSocial);
        } else {
            txtPoolMental.setText(getString(R.string.cat_mental));
            txtPoolPhysical.setText(getString(R.string.cat_physical));
            txtPoolSocial.setText(getString(R.string.cat_social));
        }

        valueSetterIntelligence.setCurrentValue(characterCreatorHelper.getInt(Constants.ATTR_INT, 1));
        valueSetterWits.setCurrentValue(characterCreatorHelper.getInt(Constants.ATTR_WIT, 1));
        valueSetterResolve.setCurrentValue(characterCreatorHelper.getInt(Constants.ATTR_RES, 1));
        valueSetterStrength.setCurrentValue(characterCreatorHelper.getInt(Constants.ATTR_STR, 1));
        valueSetterDexterity.setCurrentValue(characterCreatorHelper.getInt(Constants.ATTR_DEX, 1));
        valueSetterStamina.setCurrentValue(characterCreatorHelper.getInt(Constants.ATTR_STA, 1));
        valueSetterPresence.setCurrentValue(characterCreatorHelper.getInt(Constants.ATTR_PRE, 1));
        valueSetterManipulation.setCurrentValue(characterCreatorHelper.getInt(Constants.ATTR_MAN, 1));
        valueSetterComposure.setCurrentValue(characterCreatorHelper.getInt(Constants.ATTR_COM, 1));
    }

    public void setPagerMaster(PagerMaster pagerMaster) {
        this.pagerMaster = pagerMaster;
    }

    @Override
    public int getLayout() {
        return R.layout.step_attr_set;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_points_set);
    }

    public void checkCompletionConditions() {
        pagerMaster.checkStepIsComplete(!hasLeftoverPoints(), this);
    }

    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = getContext().getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
        }
        return preferences;
    }
}
