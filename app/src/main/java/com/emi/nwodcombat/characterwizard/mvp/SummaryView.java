package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.model.pojos.Trait;
import com.emi.nwodcombat.widgets.ValueSetter;

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
}
