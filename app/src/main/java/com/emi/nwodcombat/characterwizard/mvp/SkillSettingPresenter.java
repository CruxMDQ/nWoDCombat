package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.dialogs.AddSpecialtyDialog;
import com.emi.nwodcombat.model.pojos.Trait;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.tools.BusProvider;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Iterator;

import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 23/05/2016.
 * Presenter for third step of character creator done in MVP.
 */
public class SkillSettingPresenter {
    private final Context context;
    private final Bus bus;
    private SkillSettingView view;
    private CharacterWizardModel model;

    public SkillSettingPresenter(CharacterWizardModel model, SkillSettingView view) {
        this.model = model;
        this.view = view;
        this.context = view.getContext();
        this.bus = BusProvider.getInstance();
        setUpUI();
    }

    private void setUpUI() {
        String skill = getString(R.string.kind_skill);

        view.setUpValueSetterAcademics(new Trait(
            skill, getString(R.string.skill_academics), getString(R.string.cat_mental), null));
        view.setUpValueSetterComputer(new Trait(
            skill, getString(R.string.skill_computer), getString(R.string.cat_mental), null));
        view.setUpValueSetterCrafts(new Trait(
            skill, getString(R.string.skill_crafts), getString(R.string.cat_mental), null));
        view.setUpValueSetterInvestigation(new Trait(
            skill, getString(R.string.skill_investigation), getString(R.string.cat_mental), null));
        view.setUpValueSetterMedicine(new Trait(
            skill, getString(R.string.skill_medicine), getString(R.string.cat_mental), null));
        view.setUpValueSetterOccult(new Trait(
            skill, getString(R.string.skill_occult), getString(R.string.cat_mental), null));
        view.setUpValueSetterPolitics(new Trait(
            skill, getString(R.string.skill_politics), getString(R.string.cat_mental), null));
        view.setUpValueSetterScience(new Trait(
            skill, getString(R.string.skill_science), getString(R.string.cat_mental), null));

        view.setUpValueSetterAthletics(new Trait(
            skill, getString(R.string.skill_athletics), getString(R.string.cat_physical), null));
        view.setUpValueSetterBrawl(new Trait(
            skill, getString(R.string.skill_brawl), getString(R.string.cat_physical), null));
        view.setUpValueSetterDrive(new Trait(
            skill, getString(R.string.skill_drive), getString(R.string.cat_physical), null));
        view.setUpValueSetterFirearms(new Trait(
            skill, getString(R.string.skill_firearms), getString(R.string.cat_physical), null));
        view.setUpValueSetterLarceny(new Trait(
            skill, getString(R.string.skill_larceny), getString(R.string.cat_physical), null));
        view.setUpValueSetterStealth(new Trait(
            skill, getString(R.string.skill_stealth), getString(R.string.cat_physical), null));
        view.setUpValueSetterSurvival(new Trait(
            skill, getString(R.string.skill_survival), getString(R.string.cat_physical), null));
        view.setUpValueSetterWeaponry(new Trait(
            skill, getString(R.string.skill_weaponry), getString(R.string.cat_physical), null));

        view.setUpValueSetterAnimalKen(new Trait(
            skill, getString(R.string.skill_animal_ken), getString(R.string.cat_social), null));
        view.setUpValueSetterEmpathy(new Trait(
            skill, getString(R.string.skill_empathy), getString(R.string.cat_social), null));
        view.setUpValueSetterExpression(new Trait(
            skill, getString(R.string.skill_expression), getString(R.string.cat_social), null));
        view.setUpValueSetterIntimidation(new Trait(
            skill, getString(R.string.skill_intimidation), getString(R.string.cat_social), null));
        view.setUpValueSetterPersuasion(new Trait(
            skill, getString(R.string.skill_persuasion), getString(R.string.cat_social), null));
        view.setUpValueSetterSocialize(new Trait(
            skill, getString(R.string.skill_socialize), getString(R.string.cat_social), null));
        view.setUpValueSetterStreetwise(new Trait(
            skill, getString(R.string.skill_streetwise), getString(R.string.cat_social), null));
        view.setUpValueSetterSubterfuge(new Trait(
            skill, getString(R.string.skill_subterfuge), getString(R.string.cat_social), null));

    }

    @Subscribe
    public void onEntryChanged(Events.SkillChanged event) {
        int spent = 0;
        switch (event.category) {
            case Constants.MENTAL: {
                spent = model.getPointsSpentOnMentalSkills();

                break;
            }
            case Constants.PHYSICAL: {
                spent = model.getPointsSpentOnPhysicalSkills();

                break;
            }
            case Constants.SOCIAL: {
                spent = model.getPointsSpentOnSocialSkills();

                break;
            }
        }

        changeValue(event.isIncrease, event.key, event.category, spent);

        bus.post(new Events.StepCompletionChecked(model.isCheating() || checkCategoriesAreAllDifferent()));
    }

    private boolean checkCategoriesAreAllDifferent() {
        int mental = getCategoryPriority(view.getSkillsMental());
        int physical = getCategoryPriority(view.getSkillsPhysical());
        int social = getCategoryPriority(view.getSkillsSocial());

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
        Activity activity = view.getActivity();
        if (activity == null) {
            return 0;
        }

        if (title.toLowerCase().contains(activity.getString(R.string.cat_primary_suffix).toLowerCase())) {
            return 1;
        } else if (title.toLowerCase().contains(activity.getString(
                R.string.cat_secondary_suffix).toLowerCase())) {
            return 2;
        } else if (title.toLowerCase().contains(activity.getString(R.string.cat_tertiary_suffix).toLowerCase())) {
            return 3;
        }
        return 0;
    }

    @Subscribe
    public void onSpecialtyTapped(Events.SpecialtyClicked event) {
        AddSpecialtyDialog dialog = AddSpecialtyDialog.newInstance(
            context.getString(R.string.dialog_specialty_title), event.key, model);
        dialog.show(view.getFragmentManager(), dialog.getClass().toString());
    }

    @Subscribe
    public void onSpecialtyDialogClosing(Events.SpecialtyDialogClosing event) {
        RealmList<Entry> specialties = model.getSpecialties(event.key);

        if (specialties.size() > 0) {
            StringBuilder builder = new StringBuilder();

            Iterator iterator = specialties.iterator();

            while (iterator.hasNext()) {
                Entry specialty = (Entry) iterator.next();

                builder.append(specialty.getValue());

                if (iterator.hasNext()) {
                    builder.append(", ");
                }
            }

            view.setSkillText(event.key, builder.toString());
        } else {
            view.setSkillText(event.key, null);
        }
        view.updateStarButton(event.key, specialties.size() > 0);
    }

    private void changeValue(boolean isIncrease, String key, String category, int spent) {
        Integer change = isIncrease ? 1 : -1;

        int modelEntryValue = model.findEntryValue(key, Constants.ABSOLUTE_MINIMUM_SKILL);

        int newValue = change + modelEntryValue;

        if (!model.isCheating()) {

            if (newValue >= 0 && spent < Constants.SKILL_PTS_PRIMARY || spent > 0) {
                Integer skillValue = Integer.valueOf(model.addOrUpdateEntry(key, newValue).getValue());

                view.changeWidgetValue(key, skillValue);
                spent += isIncrease ? 1 : -1;

                setCategoryTitle(spent, category);
            }
        }
        else    // If point allocation is not limited by category, do this instead
        {
            Integer skillValue = Integer.valueOf(model.addOrUpdateEntry(key, newValue).getValue());

            view.changeWidgetValue(key, skillValue);
        }

        int specialties = model.countSpecialties();

        if (specialties < Constants.SKILL_SPECIALTIES_STARTING) {
            if (newValue > 0) {
                view.toggleSpecialty(key, true);
            } else if (newValue == 0) {
                view.toggleSpecialty(key, false);
            }
        }
    }

    private void setCategoryTitle(int spent, String category) {
        switch (category) {
            case Constants.CONTENT_DESC_SKILL_MENTAL: {

                view.setSkillsMental(spent, context.getString(R.string.cat_mental));

                break;
            }
            case Constants.CONTENT_DESC_SKILL_PHYSICAL: {

                view.setSkillsPhysical(spent, context.getString(R.string.cat_physical));

                break;
            }
            case Constants.CONTENT_DESC_SKILL_SOCIAL: {

                view.setSkillsSocial(spent, context.getString(R.string.cat_social));

                break;
            }
        }
    }

    public void checkSettings() {
        view.toggleEditionPanel(model.isCheating());
    }

    @SuppressWarnings("ConstantConditions")
    public String getString(int resId) {
        Resources resources = context.getResources();

        return resources.getString(resId);
    }
}
