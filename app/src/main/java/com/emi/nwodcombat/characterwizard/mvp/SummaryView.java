package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.application.NwodCombatApplication;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.model.pojos.Trait;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.widgets.ValueSetter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by emiliano.desantis on 24/05/2016.
 */
@SuppressWarnings("WeakerAccess")
public class SummaryView extends FragmentView {
    @BindView(R.id.txtSummaryAttrMental) TextView txtSummaryAttrMental;
    @BindView(R.id.txtSummaryAttrPhysical) TextView txtSummaryAttrPhysical;
    @BindView(R.id.txtSummaryAttrSocial) TextView txtSummaryAttrSocial;
    @BindView(R.id.txtSummarySkillsMental) TextView txtSummarySkillsMental;
    @BindView(R.id.txtSummarySkillsPhysical) TextView txtSummarySkillsPhysical;
    @BindView(R.id.txtSummarySkillsSocial) TextView txtSummarySkillsSocial;
    @BindView(R.id.txtSummarySpecialties) TextView txtSummarySpecialties;

    @BindView(R.id.valueSetterDefense) ValueSetter valueSetterDefense;
    @BindView(R.id.valueSetterHealth) ValueSetter valueSetterHealth;
    @BindView(R.id.valueSetterInitiative) ValueSetter valueSetterInitiative;
    @BindView(R.id.valueSetterMorality) ValueSetter valueSetterMorality;
    @BindView(R.id.valueSetterSpeed) ValueSetter valueSetterSpeed;
    @BindView(R.id.valueSetterWillpower) ValueSetter valueSetterWillpower;

    @BindView(R.id.panelSummaryMerits) LinearLayout panelSummaryMerits;

    public SummaryView(Fragment fragment) {
        super(fragment);
//        ButterKnife.bind(this, fragment.getView());
    }

    private void setUpValueSetter(ValueSetter setter, Trait trait) {
        setter.setTrait(trait);
    }

    public void setAttrSummaryMental(String text) {
        txtSummaryAttrMental.setText(text);        
    }

    public void setAttrSummaryPhysical(String text) {
        txtSummaryAttrPhysical.setText(text);
    }

    public void setAttrSummarySocial(String text) {
        txtSummaryAttrSocial.setText(text);
    }

    public void setSkillSummaryMental(String text) {
        txtSummarySkillsMental.setText(text);
    }

    public void setSkillSummaryPhysical(String text) {
        txtSummarySkillsPhysical.setText(text);
    }

    public void setSkillSummarySocial(String text) {
        txtSummarySkillsSocial.setText(text);
    }

    public void setSkillSummarySpecialties(String text) {
        txtSummarySpecialties.setText(text);
    }
    
    public void setDefense(int value) {
        valueSetterDefense.setCurrentValue(value);
    }

    public void setMorality(int value) {
        valueSetterMorality.setCurrentValue(value);
    }

    public void setHealth(int value) {
        valueSetterHealth.setCurrentValue(value);
    }

    public void setInitiative(int value) {
        valueSetterInitiative.setCurrentValue(value);
    }

    public void setSpeed(int value) {
        valueSetterSpeed.setCurrentValue(value);
    }

    public void setWillpower(int value) {
        valueSetterWillpower.setCurrentValue(value);
    }

    public void setUpValueSetterDefense(Trait defense) {
        setUpValueSetter(valueSetterDefense, defense);
    }

    public void setUpValueSetterHealth(Trait health) {
        setUpValueSetter(valueSetterHealth, health);
    }

    public void setUpValueSetterInitiative(Trait initiative) {
        setUpValueSetter(valueSetterInitiative, initiative);
    }

    public void setUpValueSetterMorality(Trait morality) {
        setUpValueSetter(valueSetterMorality, morality);
    }

    public void setUpValueSetterSpeed(Trait speed) {
        setUpValueSetter(valueSetterSpeed, speed);
    }

    public void setUpValueSetterWillpower(Trait willpower) {
        setUpValueSetter(valueSetterWillpower, willpower);
    }

    public void setMeritsSummary(List<Entry> meritsSummary) {
        panelSummaryMerits.removeAllViews();

        for (Entry merit : meritsSummary) {
            // TODO Consider whether this value here should be a constant
            int margin = calculateMargin(5);

            LinearLayout container = createContainer(
                generateParams(margin, margin, margin, margin));

            TextView name = new TextView(NwodCombatApplication.getAppContext());
            name.setText(merit.getKey());
            name.setTextColor(getActivity().getResources().getColor(android.R.color.black));
            name.setLayoutParams(generateParams(0, 0, margin, 0));

            container.addView(name);

            showCurrentValue(LayoutInflater.from(NwodCombatApplication.getAppContext()),
                Integer.valueOf(merit.getValue()), margin, container);

            panelSummaryMerits.addView(container);
        }
    }

    @NonNull
    private LinearLayout createContainer(LinearLayout.LayoutParams params) {
        LinearLayout container = new LinearLayout(NwodCombatApplication.getAppContext());

        container.setLayoutParams(params);

        container.setOrientation(LinearLayout.HORIZONTAL);

        return container;
    }

    private int calculateMargin(int dpValue) {
        float d = NwodCombatApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int)(dpValue * d);
    }

    @NonNull
    private LinearLayout.LayoutParams generateParams(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(left, top, right, bottom);
        return params;
    }

    private void showCurrentValue(LayoutInflater inflater, int currentValue, int margin, LinearLayout container) {
        if (currentValue > 0) {

            LinearLayout subContainer = createContainer(generateParams(margin, 0, margin, 0));

            for (int i = 0; i < currentValue; i++) {
                inflater.inflate(R.layout.dot_solid, subContainer, true);
            }

            container.addView(subContainer);
        }
    }
}
