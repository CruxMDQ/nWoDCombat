package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.model.pojos.Trait;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 23/05/2016.
 * View for third step of character creator wizard done implementing MVP.
 */
public class SkillSettingView extends FragmentView implements OnTraitChangedListener {
    private final Bus bus;

    @BindView(R.id.titleSkillsMental) TextView titleSkillsMental;
    @BindView(R.id.titleSkillsPhysical) TextView titleSkillsPhysical;
    @BindView(R.id.titleSkillsSocial) TextView titleSkillsSocial;
    
    @BindView(R.id.panelSkillsMental) LinearLayout panelSkillsMental;
    @BindView(R.id.panelSkillsPhysical) LinearLayout panelSkillsPhysical;
    @BindView(R.id.panelSkillsSocial) LinearLayout panelSkillsSocial;

    @BindView(R.id.valueSetterAcademics) ValueSetter valueSetterAcademics;
    @BindView(R.id.valueSetterComputer) ValueSetter valueSetterComputer;
    @BindView(R.id.valueSetterCrafts) ValueSetter valueSetterCrafts;
    @BindView(R.id.valueSetterInvestigation) ValueSetter valueSetterInvestigation;
    @BindView(R.id.valueSetterMedicine) ValueSetter valueSetterMedicine;
    @BindView(R.id.valueSetterOccult) ValueSetter valueSetterOccult;
    @BindView(R.id.valueSetterPolitics) ValueSetter valueSetterPolitics;
    @BindView(R.id.valueSetterScience) ValueSetter valueSetterScience;

    @BindView(R.id.valueSetterAthletics) ValueSetter valueSetterAthletics;
    @BindView(R.id.valueSetterBrawl) ValueSetter valueSetterBrawl;
    @BindView(R.id.valueSetterDrive) ValueSetter valueSetterDrive;
    @BindView(R.id.valueSetterFirearms) ValueSetter valueSetterFirearms;
    @BindView(R.id.valueSetterLarceny) ValueSetter valueSetterLarceny;
    @BindView(R.id.valueSetterStealth) ValueSetter valueSetterStealth;
    @BindView(R.id.valueSetterSurvival) ValueSetter valueSetterSurvival;
    @BindView(R.id.valueSetterWeaponry) ValueSetter valueSetterWeaponry;

    @BindView(R.id.valueSetterAnimalKen) ValueSetter valueSetterAnimalKen;
    @BindView(R.id.valueSetterEmpathy) ValueSetter valueSetterEmpathy;
    @BindView(R.id.valueSetterExpression) ValueSetter valueSetterExpression;
    @BindView(R.id.valueSetterIntimidation) ValueSetter valueSetterIntimidation;
    @BindView(R.id.valueSetterPersuasion) ValueSetter valueSetterPersuasion;
    @BindView(R.id.valueSetterSocialize) ValueSetter valueSetterSocialize;
    @BindView(R.id.valueSetterStreetwise) ValueSetter valueSetterStreetwise;
    @BindView(R.id.valueSetterSubterfuge) ValueSetter valueSetterSubterfuge;

    Map<String, ValueSetter> valueSetters = new HashMap<>();

    public SkillSettingView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
//        ButterKnife.bind(this, fragment.getView());
        setupWidgets();
    }

    @SuppressWarnings("ConstantConditions")
    private void setupWidgets() {
        String skill = getString(R.string.kind_skill);

        setUpValueSetter(valueSetterAcademics, new Trait(
            skill, getString(R.string.skill_academics), getString(R.string.cat_mental), null));
        setUpValueSetter(valueSetterComputer, new Trait(
            skill, getString(R.string.skill_computer), getString(R.string.cat_mental), null));
        setUpValueSetter(valueSetterCrafts, new Trait(
            skill, getString(R.string.skill_crafts), getString(R.string.cat_mental), null));
        setUpValueSetter(valueSetterInvestigation, new Trait(
            skill, getString(R.string.skill_investigation), getString(R.string.cat_mental), null));
        setUpValueSetter(valueSetterMedicine, new Trait(
            skill, getString(R.string.skill_medicine), getString(R.string.cat_mental), null));
        setUpValueSetter(valueSetterOccult, new Trait(
            skill, getString(R.string.skill_occult), getString(R.string.cat_mental), null));
        setUpValueSetter(valueSetterPolitics, new Trait(
            skill, getString(R.string.skill_politics), getString(R.string.cat_mental), null));
        setUpValueSetter(valueSetterScience, new Trait(
            skill, getString(R.string.skill_science), getString(R.string.cat_mental), null));

        setUpValueSetter(valueSetterAthletics, new Trait(
            skill, getString(R.string.skill_athletics), getString(R.string.cat_physical), null));
        setUpValueSetter(valueSetterBrawl, new Trait(
            skill, getString(R.string.skill_brawl), getString(R.string.cat_physical), null));
        setUpValueSetter(valueSetterDrive, new Trait(
            skill, getString(R.string.skill_drive), getString(R.string.cat_physical), null));
        setUpValueSetter(valueSetterFirearms, new Trait(
            skill, getString(R.string.skill_firearms), getString(R.string.cat_physical), null));
        setUpValueSetter(valueSetterLarceny, new Trait(
            skill, getString(R.string.skill_larceny), getString(R.string.cat_physical), null));
        setUpValueSetter(valueSetterStealth, new Trait(
            skill, getString(R.string.skill_stealth), getString(R.string.cat_physical), null));
        setUpValueSetter(valueSetterSurvival, new Trait(
            skill, getString(R.string.skill_survival), getString(R.string.cat_physical), null));
        setUpValueSetter(valueSetterWeaponry, new Trait(
            skill, getString(R.string.skill_weaponry), getString(R.string.cat_physical), null));

        setUpValueSetter(valueSetterAnimalKen, new Trait(
            skill, getString(R.string.skill_animal_ken), getString(R.string.cat_social), null));
        setUpValueSetter(valueSetterEmpathy, new Trait(
            skill, getString(R.string.skill_empathy), getString(R.string.cat_social), null));
        setUpValueSetter(valueSetterExpression, new Trait(
            skill, getString(R.string.skill_expression), getString(R.string.cat_social), null));
        setUpValueSetter(valueSetterIntimidation, new Trait(
            skill, getString(R.string.skill_intimidation), getString(R.string.cat_social), null));
        setUpValueSetter(valueSetterPersuasion, new Trait(
            skill, getString(R.string.skill_persuasion), getString(R.string.cat_social), null));
        setUpValueSetter(valueSetterSocialize, new Trait(
            skill, getString(R.string.skill_socialize), getString(R.string.cat_social), null));
        setUpValueSetter(valueSetterStreetwise, new Trait(
            skill, getString(R.string.skill_streetwise), getString(R.string.cat_social), null));
        setUpValueSetter(valueSetterSubterfuge, new Trait(
            skill, getString(R.string.skill_subterfuge), getString(R.string.cat_social), null));

        titleSkillsMental.setContentDescription(Constants.CONTENT_DESC_SKILL_MENTAL);
        titleSkillsPhysical.setContentDescription(Constants.CONTENT_DESC_SKILL_PHYSICAL);
        titleSkillsSocial.setContentDescription(Constants.CONTENT_DESC_SKILL_SOCIAL);
    }

    @Override
    public void onTraitChanged(int value, String constant, String kind, String category) {
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

    private void setUpValueSetter(ValueSetter setter, Trait trait) {
        setter.setListener(this);
        setter.setTrait(trait);
        valueSetters.put(trait.getName(), setter);
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
            if (setter.getTrait().getName().equalsIgnoreCase(key)) {
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
                if (setter.getTrait().getName().equalsIgnoreCase(key)) {
                setter.enableSpecialtyButton(activate);
                break;
            }
        }
    }

    public void updateStarButton(String key, boolean isChecked) {
        for (ValueSetter setter : valueSetters.values()) {
            if (setter.getTrait().getName().equalsIgnoreCase(key)) {
                if (isChecked) {
                    setter.changeSpecialtyButtonBackground(R.drawable.star, Constants.SKILL_SPECIALTY_LOADED);
                } else {
                    setter.changeSpecialtyButtonBackground(R.drawable.star_outline, Constants.SKILL_SPECIALTY_EMPTY);
                }
                break;
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public String getString(int resId) {
        Resources resources = getActivity().getResources();

        return resources.getString(resId);
    }

    public void setUpValueSetterAcademics(Trait academics) {
        setUpValueSetter(valueSetterAcademics, academics);
    }

    public void setUpValueSetterComputer(Trait computer) {
        setUpValueSetter(valueSetterComputer, computer);
    }

    public void setUpValueSetterCrafts(Trait crafts) {
        setUpValueSetter(valueSetterCrafts, crafts);
    }

    public void setUpValueSetterInvestigation(Trait investigation) {
        setUpValueSetter(valueSetterInvestigation, investigation);
    }

    public void setUpValueSetterMedicine(Trait medicine) {
        setUpValueSetter(valueSetterMedicine, medicine);
    }

    public void setUpValueSetterOccult(Trait occult) {
        setUpValueSetter(valueSetterOccult, occult);
    }

    public void setUpValueSetterPolitics(Trait politics) {
        setUpValueSetter(valueSetterPolitics, politics);
    }

    public void setUpValueSetterScience(Trait science) {
        setUpValueSetter(valueSetterScience, science);
    }

    public void setUpValueSetterAthletics(Trait athletics) {
        setUpValueSetter(valueSetterAthletics, athletics);
    }

    public void setUpValueSetterBrawl(Trait brawl) {
        setUpValueSetter(valueSetterBrawl, brawl);
    }

    public void setUpValueSetterDrive(Trait drive) {
        setUpValueSetter(valueSetterDrive, drive);
    }

    public void setUpValueSetterLarceny(Trait larceny) {
        setUpValueSetter(valueSetterLarceny, larceny);
    }

    public void setUpValueSetterFirearms(Trait firearms) {
        setUpValueSetter(valueSetterFirearms, firearms);
    }

    public void setUpValueSetterStealth(Trait stealth) {
        setUpValueSetter(valueSetterStealth, stealth);
    }

    public void setUpValueSetterSurvival(Trait survival) {
        setUpValueSetter(valueSetterSurvival, survival);
    }

    public void setUpValueSetterWeaponry(Trait weaponry) {
        setUpValueSetter(valueSetterWeaponry, weaponry);
    }

    public void setUpValueSetterAnimalKen(Trait animalken) {
        setUpValueSetter(valueSetterAnimalKen, animalken);
    }

    public void setUpValueSetterEmpathy(Trait empathy) {
        setUpValueSetter(valueSetterEmpathy, empathy);
    }

    public void setUpValueSetterExpression(Trait expression) {
        setUpValueSetter(valueSetterExpression, expression);
    }

    public void setUpValueSetterIntimidation(Trait intimidation) {
        setUpValueSetter(valueSetterIntimidation, intimidation);
    }

    public void setUpValueSetterPersuasion(Trait persuasion) {
        setUpValueSetter(valueSetterPersuasion, persuasion);
    }

    public void setUpValueSetterSocialize(Trait socialize) {
        setUpValueSetter(valueSetterSocialize, socialize);
    }

    public void setUpValueSetterStreetwise(Trait streetwise) {
        setUpValueSetter(valueSetterStreetwise, streetwise);
    }

    public void setUpValueSetterSubterfuge(Trait subterfuge) {
        setUpValueSetter(valueSetterSubterfuge, subterfuge);
    }

    public void setValues(RealmList<Entry> entries) {
        for (Entry entry : entries) {
            for (ValueSetter setter : valueSetters.values()) {
                try {
                    if (entry.getKey()
                        .equalsIgnoreCase(setter.getTrait().getName())) {
                        setter.setCurrentValue(entry);
                    }
                } catch (NullPointerException e) {
                    Log.e(this.getClass().toString(), "" + e.getMessage());
                }
            }
        }
    }
}
