package com.emi.nwodcombat.characterwizard.mvp;

import android.content.Context;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.utils.Events;
import com.squareup.otto.Subscribe;

/**
 * Created by emiliano.desantis on 19/05/2016.
 */
public class AttrSettingPresenter {
    private final Context context;
    private AttrSettingView view;
    private CharacterWizardModel model;

    public AttrSettingPresenter(CharacterWizardModel model, AttrSettingView view) {
        this.model = model;
        this.view = view;
        this.context = view.getContext();
        setupWidgets();
    }

    private void setupWidgets() {
        view.setUpUI();
    }

    @Subscribe
    public void onEntryChanged(Events.EntryChanged event) {
        Entry entry = event.entry;
        String traitCategory = event.traitCategory;

        if (entry.getType().equals(Constants.FIELD_TYPE_INTEGER)) {
            switch (traitCategory) {
                case Constants.CONTENT_DESC_ATTR_MENTAL: {
                    int spent = model.getPointsSpentOnMental();

                    changeValue(entry, spent);

//                spent = changeWidgetValue(entryKey, spent, entryValue, model.isCheating());

//                view.setMentalCategoryTitle(spent, context.getString(R.string.cat_mental));

                    break;
                }
                case Constants.CONTENT_DESC_ATTR_PHYSICAL: {
                    int spent = model.getPointsSpentOnPhysical();

                    changeValue(entry, spent);
//                spent = changeWidgetValue(entryKey, spent, entryValue, model.isCheating());
//
//                view.setPhysicalCategoryTitle(spent, context.getString(R.string.cat_physical));

                    break;
                }
                case Constants.CONTENT_DESC_ATTR_SOCIAL: {
                    int spent = model.getPointsSpentOnSocial();

                    changeValue(entry, spent);

//                spent = changeWidgetValue(entryKey, spent, entryValue, model.isCheating());
//
//                view.setSocialCategoryTitle(spent, context.getString(R.string.cat_social));

                    break;
                }

            }
            view.checkCompletionConditions();
        }
//        entry.setValue(model.findEntryValue(entryKey, 0) + entry.getValue());
//
//        model.addOrUpdateEntry(entry);

    }

    private void changeValue(Entry entry, int spent) {
        Integer value = Integer.valueOf(entry.getValue());
        String key = entry.getKey();

        if (!model.isCheating()) {

            int modelEntryValue = model.findEntryValue(key, 1);

            if (value > 0) {
                if (spent < Constants.ATTR_PTS_PRIMARY) {
                    entry.setValue(Integer.valueOf(modelEntryValue + value));
                    view.changeWidgetValue(key, Integer.valueOf(model.addOrUpdateEntry(entry).getValue()));
                    spent += value;

                    view.setMentalCategoryTitle(spent, context.getString(R.string.cat_mental));

//                    entry.setValue(Integer.valueOf(model.findEntryValue(key, 1) + value));

//                    model.addOrUpdateEntry(entry);
                }
            } else {
                if (spent > 0) {
                    entry.setValue(Integer.valueOf(modelEntryValue + value));
                    view.changeWidgetValue(key, Integer.valueOf(model.addOrUpdateEntry(entry).getValue()));
                    spent += value;

                    view.setMentalCategoryTitle(spent, context.getString(R.string.cat_mental));
//                    view.changeWidgetValue(key, value);
//                    spent += value;
//
//                    view.setMentalCategoryTitle(spent, context.getString(R.string.cat_mental));
//
//                    entry.setValue(Integer.valueOf(model.findEntryValue(key, 1) + value));
//
//                    model.addOrUpdateEntry(entry);
                }
            }
        }
        else    // If point allocation is not limited by category, do this instead
        {
            view.changeWidgetValue(key, value);
        }
    }

//    @Subscribe
//    public void onTraitChanged(Events.TraitChanged event) {
//        ValueSetter widget = (ValueSetter) event.caller;
//
//        switch (widget.getTraitCategory()) {
//            case Constants.CONTENT_DESC_ATTR_MENTAL: {
//                int spent = model.getPointsSpentOnMental();
//
//                spent = changeWidgetValue(widget, spent, event.value, model.isCheating());
//
//                view.setMentalCategoryTitle(spent, context.getString(R.string.cat_mental));
//
//                break;
//            }
//            case Constants.CONTENT_DESC_ATTR_PHYSICAL: {
//                int spent = model.getPointsSpentOnPhysical();
//
//                spent = changeWidgetValue(widget, spent, event.value, model.isCheating());
//
//                view.setPhysicalCategoryTitle(spent, context.getString(R.string.cat_physical));
//
//                break;
//            }
//            case Constants.CONTENT_DESC_ATTR_SOCIAL: {
//                int spent = model.getPointsSpentOnSocial();
//
//                spent = changeWidgetValue(widget, spent, event.value, model.isCheating());
//
//                view.setSocialCategoryTitle(spent, context.getString(R.string.cat_social));
//
//                break;
//            }
//        }
//
//        view.checkCompletionConditions();
//    }

//    private int changeWidgetValue(String key, int spent, int value, boolean cheating) {
//        if (!cheating) {
//            if (value > 0) {
//                if (spent < Constants.ATTR_PTS_PRIMARY) {
//                    view.changeWidgetValue(key, value);
//                    spent += value;
//                }
//            } else {
//                if (spent > 0) {
//                    view.changeWidgetValue(key, value);
//                    spent += value;
//                }
//            }
//        }
//        else    // If point allocation is not limited by category, do this instead
//        {
//            if (value > 0) {
//                view.changeWidgetValue(key, value);
//            } else {
//                view.changeWidgetValue(key, value);
//            }
//        }
//
//        return spent;
//    }
}
