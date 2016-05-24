package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 24/05/2016.
 */
public class SummaryView extends FragmentView {
    private Bus bus;

    @Bind(R.id.txtSummaryAttrMental) TextView txtSummaryAttrMental;
    @Bind(R.id.txtSummaryAttrPhysical) TextView txtSummaryAttrPhysical;
    @Bind(R.id.txtSummaryAttrSocial) TextView txtSummaryAttrSocial;
    @Bind(R.id.txtSummarySkillsMental) TextView txtSummarySkillsMental;
    @Bind(R.id.txtSummarySkillsPhysical) TextView txtSummarySkillsPhysical;
    @Bind(R.id.txtSummarySkillsSocial) TextView txtSummarySkillsSocial;

    @Bind(R.id.valueSetterDefense) ValueSetter valueSetterDefense;
    @Bind(R.id.valueSetterHealth) ValueSetter valueSetterHealth;
    @Bind(R.id.valueSetterInitiative) ValueSetter valueSetterInitiative;
    @Bind(R.id.valueSetterMorality) ValueSetter valueSetterMorality;
    @Bind(R.id.valueSetterSpeed) ValueSetter valueSetterSpeed;
    @Bind(R.id.valueSetterWillpower) ValueSetter valueSetterWillpower;

    public SummaryView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
        setUpUI();
    }

    protected void setUpUI() {

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
}
