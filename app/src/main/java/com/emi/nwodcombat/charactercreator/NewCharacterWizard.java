package com.emi.nwodcombat.charactercreator;

import android.view.View;
import android.widget.Toast;

import com.emi.nwodcombat.charactercreator.steps.AttrCategoriesStep;
import com.emi.nwodcombat.charactercreator.steps.AttrSettingStep;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.layouts.BasicWizardLayout;
import org.codepond.wizardroid.persistence.ContextVariable;

/**
 * Created by Emi on 2/27/16.
 */
public class NewCharacterWizard extends BasicWizardLayout {
    @ContextVariable private String name;
    @ContextVariable private String player;
    @ContextVariable private String concept;

//    @ContextVariable private int mentalPoints;
//    @ContextVariable private int physicalPoints;
//    @ContextVariable private int socialPoints;

    @ContextVariable private int intelligence;
    @ContextVariable private int wits;
    @ContextVariable private int resolve;

    @ContextVariable private int strength;
    @ContextVariable private int dexterity;
    @ContextVariable private int stamina;

    @ContextVariable private int presence;
    @ContextVariable private int manipulation;
    @ContextVariable private int composure;

    @ContextVariable private int academics;
    @ContextVariable private int computer;
    @ContextVariable private int crafts;
    @ContextVariable private int investigation;
    @ContextVariable private int medicine;
    @ContextVariable private int occult;
    @ContextVariable private int politics;
    @ContextVariable private int science;

    @ContextVariable private int athletics;
    @ContextVariable private int brawl;
    @ContextVariable private int drive;
    @ContextVariable private int firearms;
    @ContextVariable private int larceny;
    @ContextVariable private int stealth;
    @ContextVariable private int survival;
    @ContextVariable private int weaponry;

    @ContextVariable private int animalKen;
    @ContextVariable private int empathy;
    @ContextVariable private int expression;
    @ContextVariable private int intimidation;
    @ContextVariable private int persuasion;
    @ContextVariable private int socialize;
    @ContextVariable private int streetwise;
    @ContextVariable private int subterfuge;

    @ContextVariable private int health;
    @ContextVariable private int morality;
    @ContextVariable private int potency;
    @ContextVariable private int willpower;
    @ContextVariable private int willpowerReserve;

    @Override
    public WizardFlow onSetup() {
        setNextButtonText("Next");
        setBackButtonText("Previous");
        setFinishButtonText("Finish");

        return new WizardFlow.Builder()
            .addStep(AttrCategoriesStep.class)
            .addStep(AttrSettingStep.class)
            .create();
    }

    @Override
    public void onClick(View view) {
        WizardStep step = wizard.getCurrentStep();

        if(view.getId() == org.codepond.wizardroid.R.id.wizard_next_button) {
            if (step instanceof AttrCategoriesStep) {
                if (!((AttrCategoriesStep) step).hasDuplicateValues()) {
                    ((AttrCategoriesStep) step).saveChoices();
                    this.wizard.goNext();
                } else {
                    Toast.makeText(getContext(), "You have duplicate categories!", Toast.LENGTH_SHORT).show();
                }
            } else if (step instanceof AttrSettingStep) {
                if (!((AttrSettingStep) step).hasLeftoverPoints()) {
                    this.wizard.goNext();
                } else {
                    Toast.makeText(getContext(), "You have leftover points!", Toast.LENGTH_SHORT).show();
                }
            } else {
                this.wizard.goNext();
            }
        } else if (view.getId() == org.codepond.wizardroid.R.id.wizard_previous_button) {
            this.wizard.goBack();
        }
    }
}
