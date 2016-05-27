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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 19/05/2016.
 * View for second step of character creator wizard done in MVP.
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

    ArrayList<ValueSetter> valueSetters = new ArrayList<>();

    public AttrSettingView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
//        setUpUI();
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
        valueSetterComposure.setContentDescription(Constants.ATTR_COM);

        txtPoolMental.setContentDescription(Constants.CONTENT_DESC_ATTR_MENTAL);
        txtPoolPhysical.setContentDescription(Constants.CONTENT_DESC_ATTR_PHYSICAL);
        txtPoolSocial.setContentDescription(Constants.CONTENT_DESC_ATTR_SOCIAL);

        valueSetters.add(valueSetterIntelligence);
        valueSetters.add(valueSetterWits);
        valueSetters.add(valueSetterResolve);
        valueSetters.add(valueSetterStrength);
        valueSetters.add(valueSetterDexterity);
        valueSetters.add(valueSetterStamina);
        valueSetters.add(valueSetterPresence);
        valueSetters.add(valueSetterManipulation);
        valueSetters.add(valueSetterComposure);
    }

    @Override
    public void onTraitChanged(Object caller, int value, String constant, String category) {
        bus.post(new Events.AttributeChanged((value > 0), constant, category));
    }

    void changeWidgetValue(String key, int value) {
        for (ValueSetter vs : valueSetters) {
            if (vs.getContentDescription().equals(key)) {
//                vs.changeValue(value);
                vs.setValue(value);
                break;
            }
        }
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
