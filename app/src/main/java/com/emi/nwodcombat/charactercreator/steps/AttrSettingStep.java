package com.emi.nwodcombat.charactercreator.steps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.CharacterCreatorHelper;
import com.emi.nwodcombat.charactercreator.OnTraitChangedListener;
import com.emi.nwodcombat.charactercreator.PagerMaster;
import com.emi.nwodcombat.charactercreator.PagerStep;
import com.emi.nwodcombat.widgets.ValueSetterWidget;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class AttrSettingStep extends Fragment implements OnTraitChangedListener, PagerStep, PagerStep.ChildStep {

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

    private PagerMaster pagerMaster;

    private CharacterCreatorHelper characterCreatorHelper;

    public AttrSettingStep() {
        characterCreatorHelper = CharacterCreatorHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_attr_set, container, false);

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
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpUI();
    }

    public void retrieveChoices(SharedPreferences sharedPreferences) {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHAREDPREFS, Context.MODE_PRIVATE);

        mentalPoints = sharedPreferences.getInt(Constants.CONTENT_DESC_MENTAL, 3);
        physicalPoints = sharedPreferences.getInt(Constants.CONTENT_DESC_PHYSICAL, 3);
        socialPoints = sharedPreferences.getInt(Constants.CONTENT_DESC_SOCIAL, 3);
        currentMentalPool = sharedPreferences.getInt(Constants.MENTAL_POOL, mentalPoints);
        currentPhysicalPool = sharedPreferences.getInt(Constants.PHYSICAL_POOL, physicalPoints);
        currentSocialPool = sharedPreferences.getInt(Constants.SOCIAL_POOL, socialPoints);

        setPoolTitle(getString(R.string.cat_mental), currentMentalPool, txtPoolMental);
        setPoolTitle(getString(R.string.cat_physical), currentPhysicalPool, txtPoolPhysical);
        setPoolTitle(getString(R.string.cat_social), currentSocialPool, txtPoolSocial);
    }

    private void setUpUI() {
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

        switch(widget.getTraitCategory()) {
            case Constants.CONTENT_DESC_MENTAL: {
                currentMentalPool = changeWidgetValue(value, currentMentalPool, widget);
                setPoolTitle(getString(R.string.cat_mental), currentMentalPool, txtPoolMental);
                characterCreatorHelper.putInt(Constants.MENTAL_POOL, currentMentalPool);
                break;
            }
            case Constants.CONTENT_DESC_PHYSICAL: {
                currentPhysicalPool = changeWidgetValue(value, currentPhysicalPool, widget);
                setPoolTitle(getString(R.string.cat_physical), currentPhysicalPool, txtPoolPhysical);
                characterCreatorHelper.putInt(Constants.PHYSICAL_POOL, currentPhysicalPool);
                break;
            }
            case Constants.CONTENT_DESC_SOCIAL: {
                currentSocialPool = changeWidgetValue(value, currentSocialPool, widget);
                setPoolTitle(getString(R.string.cat_social), currentSocialPool, txtPoolSocial);
                characterCreatorHelper.putInt(Constants.SOCIAL_POOL, currentSocialPool);
                break;
            }
        }
        characterCreatorHelper.putInt(widget.getContentDescription().toString(), widget.getCurrentValue());

        if (!hasLeftoverPoints()) {
            pagerMaster.onStepCompleted(true, this);
        }
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

    private int changeWidgetValue(int value, int pool, ValueSetterWidget widget) {
        if (value > 0) {
            if (pool > 0) {
                pool -= widget.increaseCurrentValue();
            }
        } else {
            pool -= widget.decreaseCurrentValue();
        }
        return pool;
    }

    public boolean hasLeftoverPoints() {
        return (mentalPoints > 0 || physicalPoints > 0 || socialPoints > 0);
    }

    @Override
    public HashMap<String, Object> saveChoices() {
        HashMap<String, Object> output = new HashMap<>();

        output.put(Constants.ATTR_INT, valueSetterIntelligence.getCurrentValue());
        output.put(Constants.ATTR_WIT, valueSetterWits.getCurrentValue());
        output.put(Constants.ATTR_RES, valueSetterResolve.getCurrentValue());
        output.put(Constants.ATTR_STR, valueSetterStrength.getCurrentValue());
        output.put(Constants.ATTR_DEX, valueSetterDexterity.getCurrentValue());
        output.put(Constants.ATTR_STA, valueSetterStamina.getCurrentValue());
        output.put(Constants.ATTR_PRE, valueSetterPresence.getCurrentValue());
        output.put(Constants.ATTR_MAN, valueSetterManipulation.getCurrentValue());
        output.put(Constants.ATTR_COM, valueSetterComposure.getCurrentValue());

        return output;
    }

    @Override
    public void retrieveChoices() {
        mentalPoints = characterCreatorHelper.getInt(Constants.CONTENT_DESC_MENTAL, 3);
        physicalPoints = characterCreatorHelper.getInt(Constants.CONTENT_DESC_PHYSICAL, 3);
        socialPoints = characterCreatorHelper.getInt(Constants.CONTENT_DESC_SOCIAL, 3);
        currentMentalPool = characterCreatorHelper.getInt(Constants.MENTAL_POOL, mentalPoints);
        currentPhysicalPool = characterCreatorHelper.getInt(Constants.PHYSICAL_POOL, physicalPoints);
        currentSocialPool = characterCreatorHelper.getInt(Constants.SOCIAL_POOL, socialPoints);

        setPoolTitle(getString(R.string.cat_mental), currentMentalPool, txtPoolMental);
        setPoolTitle(getString(R.string.cat_physical), currentPhysicalPool, txtPoolPhysical);
        setPoolTitle(getString(R.string.cat_social), currentSocialPool, txtPoolSocial);

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
}
