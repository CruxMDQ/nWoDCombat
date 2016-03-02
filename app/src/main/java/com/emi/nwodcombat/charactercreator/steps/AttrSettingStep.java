package com.emi.nwodcombat.charactercreator.steps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.OnTraitChangedListener;
import com.emi.nwodcombat.widgets.ValueSetterWidget;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class AttrSettingStep extends WizardStep implements OnTraitChangedListener {

    @ContextVariable private int mentalPoints;
    @ContextVariable private int physicalPoints;
    @ContextVariable private int socialPoints;

    @ContextVariable private int intelligence;
    @ContextVariable private int wits;
    @ContextVariable private int resolve;

    @ContextVariable private int strength;
    @ContextVariable private int dexterity;
    @ContextVariable private int stamina;

    @ContextVariable private int presence;
    @ContextVariable private int manipulation;
    @ContextVariable private int composure;

    @Bind(R.id.valueSetterInt) ValueSetterWidget valueSetterIntelligence;
    @Bind(R.id.valueSetterWits) ValueSetterWidget valueSetterWits;
    @Bind(R.id.valueSetterRes) ValueSetterWidget valueSetterResolve;
    @Bind(R.id.valueSetterStr) ValueSetterWidget valueSetterStrength;
    @Bind(R.id.valueSetterDex) ValueSetterWidget valueSetterDexterity;
    @Bind(R.id.valueSetterSta) ValueSetterWidget valueSetterStamina;
    @Bind(R.id.valueSetterPre) ValueSetterWidget valueSetterPresence;
    @Bind(R.id.valueSetterMan) ValueSetterWidget valueSetterManipulation;
    @Bind(R.id.valueSetterCom) ValueSetterWidget valueSetterComposure;

    private SharedPreferences sharedPreferences;

    public AttrSettingStep() {
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpUI();
        
        retrieveChoices();
    }

    private void retrieveChoices() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHAREDPREFS, Context.MODE_PRIVATE);
        mentalPoints = sharedPreferences.getInt(Constants.CONTENT_DESC_MENTAL, 3);
        physicalPoints = sharedPreferences.getInt(Constants.CONTENT_DESC_PHYSICAL, 3);
        socialPoints = sharedPreferences.getInt(Constants.CONTENT_DESC_SOCIAL, 3);
    }

    private void setUpUI() {
        valueSetterIntelligence.setListener(this);
        valueSetterWits.setListener(this);
        valueSetterResolve.setListener(this);
        valueSetterStrength.setListener(this);
        valueSetterDexterity.setListener(this);
        valueSetterStamina.setListener(this);
        valueSetterPresence.setListener(this);
        valueSetterManipulation.setListener(this);
        valueSetterComposure.setListener(this);
    }

    @Override
    public void onTraitChanged(Object caller, int value) {
        ValueSetterWidget widget = (ValueSetterWidget) caller;

        switch(widget.getTraitCategory()) {
            case Constants.CONTENT_DESC_MENTAL: {
                mentalPoints = changeWidgetValue(value, mentalPoints, widget);
                break;
            }
            case Constants.CONTENT_DESC_PHYSICAL: {
                physicalPoints = changeWidgetValue(value, physicalPoints, widget);
                break;
            }
            case Constants.CONTENT_DESC_SOCIAL: {
                socialPoints = changeWidgetValue(value, socialPoints, widget);
                break;
            }
        }
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

}
