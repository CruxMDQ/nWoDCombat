package com.emi.nwodcombat.characterwizard.mvp;

import android.content.Context;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.utils.Events;
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

    public AttrSettingPresenter(CharacterWizardModel model, AttrSettingView view) {
        this.model = model;
        this.view = view;
        this.context = view.getContext();
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

        view.checkCompletionConditions(model.isCheating());
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
}
