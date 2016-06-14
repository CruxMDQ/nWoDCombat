package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Activity;
import android.content.Context;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by emiliano.desantis on 19/05/2016.
 * Presenter for second step of character creator done in MVP.
 */
public class AttrSettingPresenter //implements OnSettingChangedListener {
{
    private final Context context;
    private AttrSettingView view;
    private CharacterWizardModel model;
    private Bus bus;

    public AttrSettingPresenter(CharacterWizardModel model, AttrSettingView view, Bus bus) {
        this.model = model;
        this.view = view;
        this.context = view.getContext();
        this.bus = bus;

        // Block required for minimum starting values. Ugly as frak, but simple until a better idea comes up.
        model.addOrUpdateEntry(Constants.ATTR_INT, Constants.ABSOLUTE_MINIMUM_ATTR);
        model.addOrUpdateEntry(Constants.ATTR_WIT, Constants.ABSOLUTE_MINIMUM_ATTR);
        model.addOrUpdateEntry(Constants.ATTR_RES, Constants.ABSOLUTE_MINIMUM_ATTR);
        model.addOrUpdateEntry(Constants.ATTR_STR, Constants.ABSOLUTE_MINIMUM_ATTR);
        model.addOrUpdateEntry(Constants.ATTR_DEX, Constants.ABSOLUTE_MINIMUM_ATTR);
        model.addOrUpdateEntry(Constants.ATTR_STA, Constants.ABSOLUTE_MINIMUM_ATTR);
        model.addOrUpdateEntry(Constants.ATTR_PRE, Constants.ABSOLUTE_MINIMUM_ATTR);
        model.addOrUpdateEntry(Constants.ATTR_MAN, Constants.ABSOLUTE_MINIMUM_ATTR);
        model.addOrUpdateEntry(Constants.ATTR_COM, Constants.ABSOLUTE_MINIMUM_ATTR);
    }

    @Subscribe
    public void onEntryChanged(Events.AttributeChanged event) {
        int spent = 0;
        switch (event.category) {
            case Constants.CONTENT_DESC_ATTR_MENTAL: {
                spent = model.getPointsSpentOnAttrMental();

                break;
            }
            case Constants.CONTENT_DESC_ATTR_PHYSICAL: {
                spent = model.getPointsSpentOnAttrPhysical();

                break;
            }
            case Constants.CONTENT_DESC_ATTR_SOCIAL: {
                spent = model.getPointsSpentOnAttrSocial();

                break;
            }
        }

        changeValue(event.isIncrease, event.key, event.category, spent);

        bus.post(new Events.StepCompletionChecked(model.isCheating() || checkCategoriesAreAllDifferent()));
    }

    private void changeValue(boolean isIncrease, String key, String category, int spent) {
        int delta = isIncrease ? 1 : -1;
        Integer change = delta + model.findEntryValue(key, Constants.ABSOLUTE_MINIMUM_ATTR);

        view.changeWidgetValue(key, Integer.valueOf(model.addOrUpdateEntry(key, change).getValue()));

        // If point allocation is not limited by category, do this instead
        if (!model.isCheating() && ((change > 0 && spent < Constants.ATTR_PTS_PRIMARY) || (change < 0 && spent > 0))) {
            setCategoryTitle(spent + delta, category);
        }
    }

    private void setCategoryTitle(int spent, String category) {
        switch (category) {
            case Constants.CONTENT_DESC_ATTR_MENTAL: {
                view.setMentalCategoryTitle(spent, context.getString(R.string.cat_mental));
                break;
            }
            case Constants.CONTENT_DESC_ATTR_PHYSICAL: {
                view.setPhysicalCategoryTitle(spent, context.getString(R.string.cat_physical));
                break;
            }
            case Constants.CONTENT_DESC_ATTR_SOCIAL: {
                view.setSocialCategoryTitle(spent, context.getString(R.string.cat_social));
                break;
            }
        }
    }

    public void checkSettings() {
        view.toggleEditionPanel(model.isCheating());
    }

    //TODO move to presenter take a look SkillSettingView and SkillSettingPresenter
    private boolean checkCategoriesAreAllDifferent() {
        int mental = getCategoryPriority(view.getAttrsMental());
        int physical = getCategoryPriority(view.getAttrsPhysical());
        int social = getCategoryPriority(view.getAttrsSocial());

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
}
