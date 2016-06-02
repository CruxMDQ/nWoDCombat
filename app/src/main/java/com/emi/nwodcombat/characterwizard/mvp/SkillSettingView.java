package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.utils.Events;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by emiliano.desantis on 23/05/2016.
 * View for third step of character creator wizard done implementing MVP.
 */
public class SkillSettingView extends FragmentView implements OnTraitChangedListener {
    private final Bus bus;

    @Bind(R.id.titleSkillsMental) TextView titleSkillsMental;
    @Bind(R.id.titleSkillsPhysical) TextView titleSkillsPhysical;
    @Bind(R.id.titleSkillsSocial) TextView titleSkillsSocial;
    
    @Bind(R.id.panelSkillsMental) LinearLayout panelSkillsMental;
    @Bind(R.id.panelSkillsPhysical) LinearLayout panelSkillsPhysical;
    @Bind(R.id.panelSkillsSocial) LinearLayout panelSkillsSocial;

    @Bind(R.id.valueSetterAcademics) ValueSetter valueSetterAcademics;
    @Bind(R.id.valueSetterComputer) ValueSetter valueSetterComputer;
    @Bind(R.id.valueSetterCrafts) ValueSetter valueSetterCrafts;
    @Bind(R.id.valueSetterInvestigation) ValueSetter valueSetterInvestigation;
    @Bind(R.id.valueSetterMedicine) ValueSetter valueSetterMedicine;
    @Bind(R.id.valueSetterOccult) ValueSetter valueSetterOccult;
    @Bind(R.id.valueSetterPolitics) ValueSetter valueSetterPolitics;
    @Bind(R.id.valueSetterScience) ValueSetter valueSetterScience;

    @Bind(R.id.valueSetterAthletics) ValueSetter valueSetterAthletics;
    @Bind(R.id.valueSetterBrawl) ValueSetter valueSetterBrawl;
    @Bind(R.id.valueSetterDrive) ValueSetter valueSetterDrive;
    @Bind(R.id.valueSetterFirearms) ValueSetter valueSetterFirearms;
    @Bind(R.id.valueSetterLarceny) ValueSetter valueSetterLarceny;
    @Bind(R.id.valueSetterStealth) ValueSetter valueSetterStealth;
    @Bind(R.id.valueSetterSurvival) ValueSetter valueSetterSurvival;
    @Bind(R.id.valueSetterWeaponry) ValueSetter valueSetterWeaponry;

    @Bind(R.id.valueSetterAnimalKen) ValueSetter valueSetterAnimalKen;
    @Bind(R.id.valueSetterEmpathy) ValueSetter valueSetterEmpathy;
    @Bind(R.id.valueSetterExpression) ValueSetter valueSetterExpression;
    @Bind(R.id.valueSetterIntimidation) ValueSetter valueSetterIntimidation;
    @Bind(R.id.valueSetterPersuasion) ValueSetter valueSetterPersuasion;
    @Bind(R.id.valueSetterSocialize) ValueSetter valueSetterSocialize;
    @Bind(R.id.valueSetterStreetwise) ValueSetter valueSetterStreetwise;
    @Bind(R.id.valueSetterSubterfuge) ValueSetter valueSetterSubterfuge;

    ArrayList<ValueSetter> valueSetters = new ArrayList<>();

    public SkillSettingView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
        setUpUI();
    }

    protected void setUpUI() {
        setUpValueSetter(valueSetterAcademics, Constants.SKILL_ACADEMICS, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterComputer, Constants.SKILL_COMPUTER, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterCrafts, Constants.SKILL_CRAFTS, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterInvestigation, Constants.SKILL_INVESTIGATION, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterMedicine, Constants.SKILL_MEDICINE, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterOccult, Constants.SKILL_OCCULT, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterPolitics, Constants.SKILL_POLITICS, Constants.CONTENT_DESC_SKILL_MENTAL);
        setUpValueSetter(valueSetterScience, Constants.SKILL_SCIENCE, Constants.CONTENT_DESC_SKILL_MENTAL);

        setUpValueSetter(valueSetterAthletics, Constants.SKILL_ATHLETICS, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterBrawl, Constants.SKILL_BRAWL, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterDrive, Constants.SKILL_DRIVE, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterFirearms, Constants.SKILL_FIREARMS, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterLarceny, Constants.SKILL_LARCENY, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterStealth, Constants.SKILL_STEALTH, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterSurvival, Constants.SKILL_SURVIVAL, Constants.CONTENT_DESC_SKILL_PHYSICAL);
        setUpValueSetter(valueSetterWeaponry, Constants.SKILL_WEAPONRY, Constants.CONTENT_DESC_SKILL_PHYSICAL);

        setUpValueSetter(valueSetterAnimalKen, Constants.SKILL_ANIMAL_KEN, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterEmpathy, Constants.SKILL_EMPATHY, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterExpression, Constants.SKILL_EXPRESSION, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterIntimidation, Constants.SKILL_INTIMIDATION, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterPersuasion, Constants.SKILL_PERSUASION, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterSocialize, Constants.SKILL_SOCIALIZE, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterStreetwise, Constants.SKILL_STREETWISE, Constants.CONTENT_DESC_SKILL_SOCIAL);
        setUpValueSetter(valueSetterSubterfuge, Constants.SKILL_SUBTERFUGE, Constants.CONTENT_DESC_SKILL_SOCIAL);

        titleSkillsMental.setContentDescription(Constants.CONTENT_DESC_SKILL_MENTAL);
        titleSkillsPhysical.setContentDescription(Constants.CONTENT_DESC_SKILL_PHYSICAL);
        titleSkillsSocial.setContentDescription(Constants.CONTENT_DESC_SKILL_SOCIAL);
    }

    @Override
    public void onTraitChanged(int value, String constant, String category) {
        bus.post(new Events.SkillChanged((value > 0), constant, category));
    }
    
    @OnClick(R.id.titleSkillsMental)
    public void onTitleClickedMental() {
        panelSkillsMental.setVisibility(View.VISIBLE);
        panelSkillsPhysical.setVisibility(View.GONE);
        panelSkillsSocial.setVisibility(View.GONE);
    }

    @OnClick(R.id.titleSkillsPhysical)
    public void onTitleClickedPhysical() {
        panelSkillsMental.setVisibility(View.GONE);
        panelSkillsPhysical.setVisibility(View.VISIBLE);
        panelSkillsSocial.setVisibility(View.GONE);
    }

    @OnClick(R.id.titleSkillsSocial)
    public void onTitleClickedSocial() {
        panelSkillsMental.setVisibility(View.GONE);
        panelSkillsPhysical.setVisibility(View.GONE);
        panelSkillsSocial.setVisibility(View.VISIBLE);
    }

    void changeWidgetValue(String key, int value) {
        for (ValueSetter vs : valueSetters) {
            if (vs.getContentDescription().equals(key)) {
                vs.setValue(value);
                break;
            }
        }
    }

    void checkCompletionConditions(boolean cheating) {
        bus.post(new Events.StepCompletionChecked(cheating || checkCategoriesAreAllDifferent()));
    }

    private boolean checkCategoriesAreAllDifferent() {
        int mental = getCategoryPriority(titleSkillsMental.getText().toString());
        int physical = getCategoryPriority(titleSkillsPhysical.getText().toString());
        int social = getCategoryPriority(titleSkillsSocial.getText().toString());

        if (mental != 0 && physical != 0 && social != 0) {
            boolean mentalSocial = mental == social;
            boolean mentalPhysical = mental == physical;
            boolean physicalSocial = physical == social;

            return !(mentalSocial || mentalPhysical || physicalSocial);
        } else {
            return false;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private int getCategoryPriority(String title) {
        if (title.toLowerCase().contains(getActivity().getString(R.string.cat_primary_suffix).toLowerCase())) {
            return 1;
        } else if (title.toLowerCase().contains(getActivity().getString(
            R.string.cat_secondary_suffix).toLowerCase())) {
            return 2;
        } else if (title.toLowerCase().contains(getActivity().getString(R.string.cat_tertiary_suffix).toLowerCase())) {
            return 3;
        }
        return 0;
    }

    void setMentalCategoryTitle(int spent, String category) {
        setCategoryTitle(titleSkillsMental, spent, category);
    }

    void setPhysicalCategoryTitle(int spent, String category) {
        setCategoryTitle(titleSkillsPhysical, spent, category);
    }

    void setSocialCategoryTitle(int spent, String category) {
        setCategoryTitle(titleSkillsSocial, spent, category);
    }

    @SuppressWarnings("ConstantConditions")
    private void setCategoryTitle(TextView textView, int spent, String category) {
        if (spent == Constants.SKILL_PTS_PRIMARY || spent > Constants.SKILL_PTS_SECONDARY) {
            textView.setText(
                    String.format("%s (%s)", category, getActivity()
                            .getString(R.string.cat_primary_suffix)));
        } else if (spent == Constants.SKILL_PTS_SECONDARY || spent > Constants.SKILL_PTS_TERTIARY) {
            textView.setText(
                    String.format("%s (%s)", category, getActivity().getString(
                            R.string.cat_secondary_suffix)));
        } else if (spent == Constants.SKILL_PTS_TERTIARY) {
            textView.setText(
                    String.format("%s (%s)", category, getActivity()
                            .getString(R.string.cat_tertiary_suffix)));
        } else if (spent < Constants.SKILL_PTS_TERTIARY) {
            textView.setText(category);
        }
    }

    private void setUpValueSetter(ValueSetter setter, String skillName, String skillCategory) {
        setter.setListener(this);
        setter.setContentDescription(skillName);
        setter.setTraitCategory(skillCategory);
        valueSetters.add(setter);
    }

    public void toggleEditionPanel(boolean isActive) {
        if (isActive) {
            for (ValueSetter setter : valueSetters) {
                setter.toggleEditionPanel(isActive);
            }
        }
    }
}
