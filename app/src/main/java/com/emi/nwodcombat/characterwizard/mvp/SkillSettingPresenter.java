package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Activity;
import android.content.Context;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.utils.BusProvider;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.utils.Events;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

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


    private void changeValue(boolean isIncrease, String key, String category, int spent) {
        Integer change = isIncrease ? 1 : -1;

        int modelEntryValue = model.findEntryValue(key, Constants.ABSOLUTE_MINIMUM_SKILL);

        change += modelEntryValue;

        if (!model.isCheating()) {

            if ((change > 0 && spent < Constants.SKILL_PTS_PRIMARY) || (change < 0 && spent > 0)) {
                view.changeWidgetValue(key, Integer.valueOf(model.addOrUpdateEntry(key, change).getValue()));
                spent += isIncrease ? 1 : -1;

                setCategoryTitle(spent, category);
            }
        }
        else    // If point allocation is not limited by category, do this instead
        {
            view.changeWidgetValue(key, Integer.valueOf(model.addOrUpdateEntry(key, change).getValue()));
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
