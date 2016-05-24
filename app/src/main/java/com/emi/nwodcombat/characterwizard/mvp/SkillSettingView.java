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
        valueSetterAcademics.setListener(this);
        valueSetterAcademics.setContentDescription(Constants.SKILL_ACADEMICS);
        valueSetterComputer.setListener(this);
        valueSetterComputer.setContentDescription(Constants.SKILL_COMPUTER);
        valueSetterCrafts.setListener(this);
        valueSetterCrafts.setContentDescription(Constants.SKILL_CRAFTS);
        valueSetterInvestigation.setListener(this);
        valueSetterInvestigation.setContentDescription(Constants.SKILL_INVESTIGATION);
        valueSetterMedicine.setListener(this);
        valueSetterMedicine.setContentDescription(Constants.SKILL_MEDICINE);
        valueSetterOccult.setListener(this);
        valueSetterOccult.setContentDescription(Constants.SKILL_OCCULT);
        valueSetterPolitics.setListener(this);
        valueSetterPolitics.setContentDescription(Constants.SKILL_POLITICS);
        valueSetterScience.setListener(this);
        valueSetterScience.setContentDescription(Constants.SKILL_SCIENCE);

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

        valueSetterAnimalKen.setListener(this);
        valueSetterAnimalKen.setContentDescription(Constants.SKILL_ANIMAL_KEN);
        valueSetterEmpathy.setListener(this);
        valueSetterEmpathy.setContentDescription(Constants.SKILL_EMPATHY);
        valueSetterExpression.setListener(this);
        valueSetterExpression.setContentDescription(Constants.SKILL_EXPRESSION);
        valueSetterIntimidation.setListener(this);
        valueSetterIntimidation.setContentDescription(Constants.SKILL_INTIMIDATION);
        valueSetterPersuasion.setListener(this);
        valueSetterPersuasion.setContentDescription(Constants.SKILL_PERSUASION);
        valueSetterSocialize.setListener(this);
        valueSetterSocialize.setContentDescription(Constants.SKILL_SOCIALIZE);
        valueSetterStreetwise.setListener(this);
        valueSetterStreetwise.setContentDescription(Constants.SKILL_STREETWISE);
        valueSetterSubterfuge.setListener(this);
        valueSetterSubterfuge.setContentDescription(Constants.SKILL_SUBTERFUGE);

        titleSkillsMental.setContentDescription(Constants.CONTENT_DESC_SKILL_MENTAL);
        titleSkillsPhysical.setContentDescription(Constants.CONTENT_DESC_SKILL_PHYSICAL);
        titleSkillsSocial.setContentDescription(Constants.CONTENT_DESC_SKILL_SOCIAL);

        valueSetters.add(valueSetterAcademics);
        valueSetters.add(valueSetterAnimalKen);
        valueSetters.add(valueSetterAthletics);
        valueSetters.add(valueSetterBrawl);
        valueSetters.add(valueSetterComputer);
        valueSetters.add(valueSetterCrafts);
        valueSetters.add(valueSetterDrive);
        valueSetters.add(valueSetterEmpathy);
        valueSetters.add(valueSetterExpression);
        valueSetters.add(valueSetterFirearms);
        valueSetters.add(valueSetterIntimidation);
        valueSetters.add(valueSetterInvestigation);
        valueSetters.add(valueSetterLarceny);
        valueSetters.add(valueSetterMedicine);
        valueSetters.add(valueSetterOccult);
        valueSetters.add(valueSetterPersuasion);
        valueSetters.add(valueSetterPolitics);
        valueSetters.add(valueSetterScience);
        valueSetters.add(valueSetterSocialize);
        valueSetters.add(valueSetterStealth);
        valueSetters.add(valueSetterStreetwise);
        valueSetters.add(valueSetterSubterfuge);
        valueSetters.add(valueSetterSurvival);
        valueSetters.add(valueSetterWeaponry);
    }

    @Override
    public void onTraitChanged(Object caller, int value, String constant) {
        String traitCategory = ((ValueSetter) caller).getTraitCategory();

        bus.post(new Events.SkillChanged((value > 0), constant, traitCategory));
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

    void checkCompletionConditions() {
        bus.post(new Events.StepCompletionChecked(checkCategoriesAreAllDifferent()));
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
//        switch (spent) {
//            case Constants.SKILL_PTS_PRIMARY:
//                textView.setText(
//                    String.format("%s (%s)", category, getActivity()
//                        .getString(R.string.cat_primary_suffix)));
//                break;
//            case Constants.SKILL_PTS_SECONDARY:
//                textView.setText(
//                    String.format("%s (%s)", category, getActivity().getString(
//                        R.string.cat_secondary_suffix)));
//                break;
//            case Constants.SKILL_PTS_TERTIARY:
//                textView.setText(
//                    String.format("%s (%s)", category, getActivity()
//                        .getString(R.string.cat_tertiary_suffix)));
//                break;
//            default:
//                textView.setText(category);
//        }
    }
}
