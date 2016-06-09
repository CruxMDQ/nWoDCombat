package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Activity;
import android.content.Context;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.dialogs.AddSpecialtyDialog;
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
    }

    @Subscribe
    public void onEntryChanged(Events.SkillChanged event) {
        int spent = 0;
        switch (event.category) {
            case Constants.CONTENT_DESC_SKILL_MENTAL: {
                spent = model.getPointsSpentOnMentalSkills();

                break;
            }
            case Constants.CONTENT_DESC_SKILL_PHYSICAL: {
                spent = model.getPointsSpentOnPhysicalSkills();

                break;
            }
            case Constants.CONTENT_DESC_SKILL_SOCIAL: {
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
    public void onSpecialtyTapped(Events.SpecialtyTapped event) {
        AddSpecialtyDialog dialog = AddSpecialtyDialog.newInstance(
            context.getString(R.string.dialog_specialty_title), event.key, model);
        dialog.show(view.getFragmentManager(), dialog.getClass().toString());

//        if (event.isChecked) {
//            /**
//             * Pseudocode for adding a specialty:
//             * - count how many specialties the character has already picked
//             * - if they are less than 3
//             * ---> add the entry on the model
//             * ---> check the checkbox on the view
//             * ---> set specialty name on view
//             * ---> increase specialty count by 1
//             * ---> if specialty count is now 3, disable all unchecked checkboxes on view
//             */
//            if (specialtyCount < Constants.SKILL_SPECIALTIES_STARTING) {
//
//                model.addSpecialty(event.key, event.specialtyName);
//
//                specialtyCount++;
//
//                view.setSkillText(event.key, event.specialtyName);
//
//                if (specialtyCount == Constants.SKILL_SPECIALTIES_STARTING) {
//                    view.toggleSpecialties(false);
//                } else if (specialtyCount < Constants.SKILL_SPECIALTIES_STARTING) {
//                    view.toggleSpecialties(true);
//                }
//            } else {
//                view.updateStarButton(event.key, false);
//            }
//        } else {
//            /**
//             * Pseudocode for removing a specialty:
//             * - uncheck the checkbox on the view
//             * - remove the entry on the model (funny thing, that)
//             * - decrease specialty count by 1
//             */
////            model.removeSpecialty(event.key);
//            model.removeSpecialty(event.key, event.specialtyName);
//
//            view.toggleSpecialties(true);
//
//            view.updateStarButton(event.key, false);
//
//            view.setSkillText(event.key, null);
//        }
    }

    @Subscribe
    public void onSpecialtyDialogClosing(Events.SpecialtyDialogClosing event) {
        RealmList<Entry> specialties = model.getSpecialties(event.key);

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
}
