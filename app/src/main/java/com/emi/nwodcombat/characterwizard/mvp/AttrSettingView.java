package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.content.res.Resources;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.model.pojos.Trait;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.Map;

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

    Map<String, ValueSetter> valueSetters = new HashMap<>();

    public AttrSettingView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
        setupWidgets();
    }

    private void setupWidgets() {
        String attribute = getString(R.string.kind_attr);

        setUpValueSetter(valueSetterIntelligence, new Trait(attribute, getString(R.string.attr_int),
            getString(R.string.cat_mental), getString(R.string.cat_power)));
        setUpValueSetter(valueSetterWits, new Trait(attribute, getString(R.string.attr_wits),
            getString(R.string.cat_mental), getString(R.string.cat_finesse)));
        setUpValueSetter(valueSetterResolve, new Trait(attribute, getString(R.string.attr_res),
            getString(R.string.cat_mental), getString(R.string.cat_resistance)));

        setUpValueSetter(valueSetterStrength, new Trait(attribute, getString(R.string.attr_str),
            getString(R.string.cat_physical), getString(R.string.cat_power)));
        setUpValueSetter(valueSetterDexterity, new Trait(attribute, getString(R.string.attr_dex),
            getString(R.string.cat_physical), getString(R.string.cat_finesse)));
        setUpValueSetter(valueSetterStamina, new Trait(attribute, getString(R.string.attr_sta), 
            getString(R.string.cat_physical), getString(R.string.cat_resistance)));

        setUpValueSetter(valueSetterPresence, new Trait(attribute, getString(R.string.attr_pre),
            getString(R.string.cat_social), getString(R.string.cat_power)));
        setUpValueSetter(valueSetterManipulation, new Trait(attribute, getString(R.string.attr_man),
            getString(R.string.cat_social), getString(R.string.cat_finesse)));
        setUpValueSetter(valueSetterComposure, new Trait(attribute, getString(R.string.attr_com),
            getString(R.string.cat_social), getString(R.string.cat_resistance)));

        txtPoolMental.setContentDescription(Constants.CONTENT_DESC_ATTR_MENTAL);
        txtPoolPhysical.setContentDescription(Constants.CONTENT_DESC_ATTR_PHYSICAL);
        txtPoolSocial.setContentDescription(Constants.CONTENT_DESC_ATTR_SOCIAL);
    }

    @Override
    public void onTraitChanged(int value, String constant, String kind, String category) {
        bus.post(new Events.AttributeChanged((value > 0), constant, category));
    }

    @Override
    public void onSpecialtyTapped(boolean isChecked, String constant, String category) { }

    void changeWidgetValue(String key, int value) {
        valueSetters.get(key).setValue(value);
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

    public String getAttrsMental() {
        return txtPoolMental.getText().toString();
    }

    public String getAttrsPhysical() {
        return txtPoolPhysical.getText().toString();
    }

    public String getAttrsSocial() {
        return txtPoolSocial.getText().toString();
    }

    public void setUpValueSetterIntelligence(Trait intelligence) {
        setUpValueSetter(valueSetterIntelligence, intelligence);
    }

    public void setUpValueSetterWits(Trait wits) {
        setUpValueSetter(valueSetterWits, wits);
    }

    public void setUpValueSetterResolve(Trait resolve) {
        setUpValueSetter(valueSetterResolve, resolve);
    }

    public void setUpValueSetterStrength(Trait strength) {
        setUpValueSetter(valueSetterStrength, strength);
    }

    public void setUpValueSetterDexterity(Trait dexterity) {
        setUpValueSetter(valueSetterDexterity, dexterity);
    }

    public void setUpValueSetterStamina(Trait stamina) {
        setUpValueSetter(valueSetterStamina, stamina);
    }

    public void setUpValueSetterPresence(Trait presence) {
        setUpValueSetter(valueSetterPresence, presence);
    }

    public void setUpValueSetterManipulation(Trait manipulation) {
        setUpValueSetter(valueSetterManipulation, manipulation);
    }

    public void setUpValueSetterComposure(Trait composure) {
        setUpValueSetter(valueSetterComposure, composure);
    }

    @SuppressWarnings("ConstantConditions")
    public String getString(int resId) {
        Resources resources = getActivity().getResources();

        return resources.getString(resId);
    }
}
