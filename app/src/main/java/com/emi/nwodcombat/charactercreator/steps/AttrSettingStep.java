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
import com.emi.nwodcombat.charactercreator.PagerMaster;
import com.emi.nwodcombat.charactercreator.OnTraitChangedListener;
import com.emi.nwodcombat.charactercreator.PagerStep;
import com.emi.nwodcombat.widgets.ValueSetterWidget;

import org.codepond.wizardroid.persistence.ContextVariable;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class AttrSettingStep extends Fragment implements OnTraitChangedListener, PagerStep {

    private int mentalPoints;
    private int currentMentalPool;
    private int physicalPoints;
    private int currentPhysicalPool;
    private int socialPoints;
    private int currentSocialPool;

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

    @Bind(R.id.txtPoolMental) TextView txtPoolMental;
    @Bind(R.id.txtPoolPhysical) TextView txtPoolPhysical;
    @Bind(R.id.txtPoolSocial) TextView txtPoolSocial;

    private SharedPreferences sharedPreferences;

    private PagerMaster pagerMaster;

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

    private void retrieveChoices() {
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

        sharedPreferences = getActivity().getSharedPreferences(Constants.SHAREDPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch(widget.getTraitCategory()) {
            case Constants.CONTENT_DESC_MENTAL: {
                currentMentalPool = changeWidgetValue(value, currentMentalPool, widget);
                setPoolTitle(getString(R.string.cat_mental), currentMentalPool, txtPoolMental);
                editor.putInt(Constants.MENTAL_POOL, currentMentalPool);
                break;
            }
            case Constants.CONTENT_DESC_PHYSICAL: {
                currentPhysicalPool = changeWidgetValue(value, currentPhysicalPool, widget);
                setPoolTitle(getString(R.string.cat_physical), currentPhysicalPool, txtPoolPhysical);
                editor.putInt(Constants.PHYSICAL_POOL, currentPhysicalPool);
                break;
            }
            case Constants.CONTENT_DESC_SOCIAL: {
                currentSocialPool = changeWidgetValue(value, currentSocialPool, widget);
                setPoolTitle(getString(R.string.cat_social), currentSocialPool, txtPoolSocial);
                editor.putInt(Constants.SOCIAL_POOL, currentSocialPool);
                break;
            }
        }
        editor.apply();
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
    public HashMap<String, Object> returnOutput() {
        return null;
    }
}
