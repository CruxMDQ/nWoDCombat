package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.utils.Events;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 19/05/2016.
 */
public class AttrSettingView extends FragmentView implements OnTraitChangedListener {

    private final Bus bus;

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

    public AttrSettingView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
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

        txtPoolMental.setContentDescription(Constants.CONTENT_DESC_ATTR_MENTAL);
        txtPoolPhysical.setContentDescription(Constants.CONTENT_DESC_ATTR_PHYSICAL);
        txtPoolSocial.setContentDescription(Constants.CONTENT_DESC_ATTR_SOCIAL);
    }

    @Override
    public void onTraitChanged(Object caller, int value, String constant) {
        bus.post(new Events.TraitChanged(caller, new Object[] { txtPoolMental, txtPoolPhysical, txtPoolSocial }, value, constant));
    }

    int getPointsSpentOnSocial() {
        return valueSetterPresence.getCurrentValue() +
            valueSetterManipulation.getCurrentValue() +
            valueSetterComposure.getCurrentValue() -
            Constants.ATTR_PTS_TERTIARY;    // By default, each category has 3 points
    }

    int getPointsSpentOnPhysical() {
        return valueSetterStrength.getCurrentValue() +
            valueSetterDexterity.getCurrentValue() +
            valueSetterStamina.getCurrentValue() -
            Constants.ATTR_PTS_TERTIARY;    // By default, each category has 3 points
    }

    int getPointsSpentOnMental() {
        return valueSetterIntelligence.getCurrentValue() +
            valueSetterWits.getCurrentValue() +
            valueSetterResolve.getCurrentValue() -
            Constants.ATTR_PTS_TERTIARY;    // By default, each category has 3 points
    }

    void changeWidgetValue(ValueSetter widget, int value) {
        widget.changeValue(value);
    }

    void checkCompletionConditions() {
        bus.post(new Events.StepCompletionChecked(checkCategoriesAreAllDifferent()));
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

    void setMentalCategoryTitle(String title) {
        txtPoolMental.setText(title);
    }

    void setPhysicalCategoryTitle(String title) {
        txtPoolPhysical.setText(title);
    }

    void setSocialCategoryTitle(String title) {
        txtPoolSocial.setText(title);
    }

    void setMentalCategoryTitle(int spent, String category) {
        setCategoryTitle(txtPoolMental, spent, category);
    }

    void setPhysicalCategoryTitle(int spent, String category) {
        setCategoryTitle(txtPoolPhysical, spent, category);
    }

    void setSocialCategoryTitle(int spent, String category) {
        setCategoryTitle(txtPoolSocial, spent, category);
    }

    @SuppressWarnings("ConstantConditions")
    private void setCategoryTitle(TextView textView, int spent, String category) {
        switch (spent) {
            case Constants.ATTR_PTS_PRIMARY:
                textView.setText(
                    String.format("%s (%s)", category, getActivity()
                        .getString(R.string.cat_primary_suffix)));
                break;
            case Constants.ATTR_PTS_SECONDARY:
                textView.setText(
                    String.format("%s (%s)", category, getActivity().getString(
                        R.string.cat_secondary_suffix)));
                break;
            case Constants.ATTR_PTS_TERTIARY:
                textView.setText(
                    String.format("%s (%s)", category, getActivity()
                        .getString(R.string.cat_tertiary_suffix)));
                break;
            default:
                textView.setText(category);
        }
    }
}
