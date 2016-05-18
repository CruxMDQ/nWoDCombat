package com.emi.nwodcombat.characterwizard.steps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.utils.BusProvider;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.utils.Events;
import com.emi.nwodcombat.widgets.ValueSetter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AttrSettingFragment extends PagerFragment implements OnTraitChangedListener {

    private SharedPreferences preferences;

    @Bind(R.id.valueSetterInt) ValueSetter valueSetterIntelligence;
    @Bind(R.id.valueSetterWits) ValueSetter valueSetterWits;
    @Bind(R.id.valueSetterRes) ValueSetter valueSetterResolve;
    @Bind(R.id.valueSetterStr) ValueSetter valueSetterStrength;
    @Bind(R.id.valueSetterDex) ValueSetter valueSetterDexterity;
    @Bind(R.id.valueSetterSta) ValueSetter valueSetterStamina;
    @Bind(R.id.valueSetterPre) ValueSetter valueSetterPresence;
    @Bind(R.id.valueSetterMan) ValueSetter valueSetterManipulation;
    @Bind(R.id.valueSetterCom) ValueSetter valueSetterComposure;

    @Bind(R.id.txtPoolMental) TextView txtPoolMental;
    @Bind(R.id.txtPoolPhysical) TextView txtPoolPhysical;
    @Bind(R.id.txtPoolSocial) TextView txtPoolSocial;

    public AttrSettingFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);

        ButterKnife.bind(this, view);

        bus = BusProvider.getInstance();

        bus.post(new Events.StepCompletionChecked(false));

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpUI();
    }

    protected void setUpUI() {
        valueSetterIntelligence.setListener(this);
        valueSetterIntelligence.setContentDescription(Constants.ATTR_INT);
        valueSetterWits.setListener(this);
        valueSetterWits.setContentDescription(Constants.ATTR_WIT);
        valueSetterResolve.setListener(this);
        valueSetterResolve.setContentDescription(Constants.ATTR_RES);
        valueSetterStrength.setListener(this);
        valueSetterStrength.setContentDescription(Constants.ATTR_STR);
        valueSetterDexterity.setListener(this);
        valueSetterDexterity.setContentDescription(Constants.ATTR_DEX);
        valueSetterStamina.setListener(this);
        valueSetterStamina.setContentDescription(Constants.ATTR_STA);
        valueSetterPresence.setListener(this);
        valueSetterPresence.setContentDescription(Constants.ATTR_PRE);
        valueSetterManipulation.setListener(this);
        valueSetterManipulation.setContentDescription(Constants.ATTR_MAN);
        valueSetterComposure.setListener(this);
        valueSetterComposure.setContentDescription(Constants.ATTR_MAN);
    }

    @Override
    public void onTraitChanged(Object caller, int value, String constant) {
        ValueSetter widget = (ValueSetter) caller;

        switch (widget.getTraitCategory()) {
            case Constants.CONTENT_DESC_ATTR_MENTAL: {
                int spent = getPointsSpentOnMental();

                spent = changeWidgetValue(widget, spent, value);

                setCategoryTitle(txtPoolMental, spent, getString(R.string.cat_mental));

                break;
            }
            case Constants.CONTENT_DESC_ATTR_PHYSICAL: {
                int spent = getPointsSpentOnPhysical();

                spent = changeWidgetValue(widget, spent, value);

                setCategoryTitle(txtPoolPhysical, spent, getString(R.string.cat_physical));

                break;
            }
            case Constants.CONTENT_DESC_ATTR_SOCIAL: {
                int spent = getPointsSpentOnSocial();

                spent = changeWidgetValue(widget, spent, value);

                setCategoryTitle(txtPoolSocial, spent, getString(R.string.cat_social));

                break;
            }
        }

        boolean isStepComplete = checkCompletionConditions();

        bus.post(new Events.StepCompletionChecked(isStepComplete));
    }

    private int changeWidgetValue(ValueSetter widget, int spent, int value) {
        // If point allocation is limited by category (usually during player creation), do this
        if (!getPreferences().getBoolean(Constants.SETTING_CHEAT, false)) {
            if (value > 0) {
                if (spent < Constants.ATTR_PTS_PRIMARY) {
                    widget.changeValue(value);
                    spent += value;
                    // TODO Write call to model to save value
                }
            } else {
                if (spent > 0) {
                    widget.changeValue(value);
                    spent += value;
                    // TODO Write call to model to save value
                }
            }
        }
        else    // If point allocation is not limited by category, do this instead
        {
            if (value > 0) {
                widget.changeValue(value);
                // TODO Write call to model to save value
            } else {
                widget.changeValue(value);
                // TODO Write call to model to save value
            }

        }
        return spent;
    }

    private void setCategoryTitle(TextView textView, int spent, String category) {
        switch (spent) {
            case Constants.ATTR_PTS_PRIMARY:
                textView.setText(
                    String.format("%s (%s)", category, getString(R.string.cat_primary_suffix)));
                break;
            case Constants.ATTR_PTS_SECONDARY:
                textView.setText(
                    String.format("%s (%s)", category, getString(R.string.cat_secondary_suffix)));
                break;
            case Constants.ATTR_PTS_TERTIARY:
                textView.setText(
                    String.format("%s (%s)", category, getString(R.string.cat_tertiary_suffix)));
                break;
            default:
                textView.setText(category);
        }
    }

    private int getPointsSpentOnSocial() {
        return valueSetterPresence.getCurrentValue() +
            valueSetterManipulation.getCurrentValue() +
            valueSetterComposure.getCurrentValue() -
            Constants.ATTR_PTS_TERTIARY;    // By default, each category has 3 points
    }

    private int getPointsSpentOnPhysical() {
        return valueSetterStrength.getCurrentValue() +
            valueSetterDexterity.getCurrentValue() +
            valueSetterStamina.getCurrentValue() -
            Constants.ATTR_PTS_TERTIARY;    // By default, each category has 3 points
    }

    private int getPointsSpentOnMental() {
        return valueSetterIntelligence.getCurrentValue() +
            valueSetterWits.getCurrentValue() +
            valueSetterResolve.getCurrentValue() -
            Constants.ATTR_PTS_TERTIARY;    // By default, each category has 3 points
    }

    @Override
    public int getLayout() {
        return R.layout.step_attr_set;
    }

    @Override
    public String getToolbarTitle() {
        return Constants.TITLE_STEP_POINTS_SET;
    }

    public boolean checkCompletionConditions() {
        return checkCategoriesAreAllDifferent();
    }

    private boolean checkCategoriesAreAllDifferent() {
        int mental = getCategoryPriority(txtPoolMental.getText().toString());
        int physical = getCategoryPriority(txtPoolPhysical.getText().toString());
        int social = getCategoryPriority(txtPoolSocial.getText().toString());

        if (mental != 0 && physical != 0 && social != 0) {
            boolean mentalSocial = mental == social;
            boolean mentalPhysical = mental == physical;
            boolean physicalSocial = physical == social;

            return !(mentalSocial || mentalPhysical || physicalSocial);
        } else {
            return false;
        }
    }
    
    private int getCategoryPriority(String title) {
        if (title.toLowerCase().contains(getString(R.string.cat_primary_suffix).toLowerCase())) {
            return 1;
        } else if (title.toLowerCase().contains(getString(R.string.cat_secondary_suffix).toLowerCase())) {
            return 2;
        } else if (title.toLowerCase().contains(getString(R.string.cat_tertiary_suffix).toLowerCase())) {
            return 3;
        }
        return 0;
    }

    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = getActivity().getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
        }
        return preferences;
    }
}
