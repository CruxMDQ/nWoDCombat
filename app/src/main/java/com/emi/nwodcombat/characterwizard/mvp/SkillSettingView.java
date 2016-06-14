package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.Map;

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

    Map<String, ValueSetter> valueSetters = new HashMap<>();

    public SkillSettingView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
        setupWidgets();
    }

    private void setupWidgets() {
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

    @Override
    public void onSpecialtyTapped(boolean isChecked, String constant, String category) {
        // TODO Code dialog for capturing specialty name here
        String specialtyName = "some name";

        bus.post(new Events.SpecialtyClicked(isChecked, constant, category, specialtyName));
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
        valueSetters.get(key).setValue(value);
    }

    public String getSkillsMental() {
        return titleSkillsMental.getText().toString();
    }

    public String getSkillsPhysical() {
        return titleSkillsPhysical.getText().toString();
    }

    public String getSkillsSocial() {
        return titleSkillsSocial.getText().toString();
    }

    void setSkillsMental(int spent, String category) {
        setCategoryTitle(titleSkillsMental, spent, category);
    }

    void setSkillsPhysical(int spent, String category) {
        setCategoryTitle(titleSkillsPhysical, spent, category);
    }

    void setSkillsSocial(int spent, String category) {
        setCategoryTitle(titleSkillsSocial, spent, category);
    }

    //TODO VSM move this to the presenter and model, too much logic for a view.
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
//        setter.enableSpecialtyCheckbox(true);
        setter.setListener(this);
        setter.setContentDescription(skillName);
        setter.setTraitCategory(skillCategory);
        valueSetters.put(skillName, setter);
    }

    public void toggleEditionPanel(boolean isActive) {
        if (isActive) {
            for (ValueSetter setter : valueSetters.values()) {
                setter.toggleEditionPanel(true);
            }
        }
    }

    public void setSkillText(String key, @Nullable String specialtyName) {
        for (ValueSetter setter : valueSetters.values()) {
            if (setter.getContentDescription().toString().equalsIgnoreCase(key)) {
                if (specialtyName != null) {
                    setter.setLabel(key + "\n(" + specialtyName + ")");
                } else {
                    setter.setLabel(key);
                }

                break;
            }
        }
    }

    public void toggleSpecialty(String key, boolean activate) {
        for (ValueSetter setter : valueSetters.values()) {
            if (setter.getContentDescription().toString().equalsIgnoreCase(key)) {
                setter.enableSpecialtyButton(activate);
                break;
            }
        }
    }

    public void updateStarButton(String key, boolean isChecked) {
        for (ValueSetter setter : valueSetters.values()) {
            if (setter.getContentDescription().toString().equalsIgnoreCase(key)) {
                if (isChecked) {
                    setter.changeSpecialtyButtonBackground(R.drawable.star, Constants.SKILL_SPECIALTY_LOADED);
                } else {
                    setter.changeSpecialtyButtonBackground(R.drawable.star_outline, Constants.SKILL_SPECIALTY_EMPTY);
                }
                break;
            }
        }
    }
}
