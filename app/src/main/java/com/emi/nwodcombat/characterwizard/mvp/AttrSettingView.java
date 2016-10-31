package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.content.res.Resources;
import android.util.Log;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.model.pojos.Trait;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.emi.nwodcombat.widgets.ValueSetter;
import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 19/05/2016.
 * View for second step of character creator wizard done in MVP.
 */
public class AttrSettingView extends FragmentView implements OnTraitChangedListener {

    private final Bus bus;

    @BindView(R.id.valueSetterInt) ValueSetter valueSetterIntelligence;
    @BindView(R.id.valueSetterWits) ValueSetter valueSetterWits;
    @BindView(R.id.valueSetterRes) ValueSetter valueSetterResolve;
    @BindView(R.id.valueSetterStr) ValueSetter valueSetterStrength;
    @BindView(R.id.valueSetterDex) ValueSetter valueSetterDexterity;
    @BindView(R.id.valueSetterSta) ValueSetter valueSetterStamina;
    @BindView(R.id.valueSetterPre) ValueSetter valueSetterPresence;
    @BindView(R.id.valueSetterMan) ValueSetter valueSetterManipulation;
    @BindView(R.id.valueSetterCom) ValueSetter valueSetterComposure;

    @BindView(R.id.txtPoolMental) TextView txtPoolMental;
    @BindView(R.id.txtPoolPhysical) TextView txtPoolPhysical;
    @BindView(R.id.txtPoolSocial) TextView txtPoolSocial;

    private Map<String, ValueSetter> valueSetters = new HashMap<>();

    public AttrSettingView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
//        ButterKnife.bind(this, fragment.getView());
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

    void toggleEditionPanel(boolean isActive) {
        if (isActive) {
            for (ValueSetter setter : valueSetters.values()) {
                setter.toggleEditionPanel(true);
            }
        }
    }

    String getAttrsMental() {
        return txtPoolMental.getText().toString();
    }

    String getAttrsPhysical() {
        return txtPoolPhysical.getText().toString();
    }

    String getAttrsSocial() {
        return txtPoolSocial.getText().toString();
    }

    void setUpValueSetterIntelligence(Trait intelligence) {
        setUpValueSetter(valueSetterIntelligence, intelligence);
    }

    void setUpValueSetterWits(Trait wits) {
        setUpValueSetter(valueSetterWits, wits);
    }

    void setUpValueSetterResolve(Trait resolve) {
        setUpValueSetter(valueSetterResolve, resolve);
    }

    void setUpValueSetterStrength(Trait strength) {
        setUpValueSetter(valueSetterStrength, strength);
    }

    void setUpValueSetterDexterity(Trait dexterity) {
        setUpValueSetter(valueSetterDexterity, dexterity);
    }

    void setUpValueSetterStamina(Trait stamina) {
        setUpValueSetter(valueSetterStamina, stamina);
    }

    void setUpValueSetterPresence(Trait presence) {
        setUpValueSetter(valueSetterPresence, presence);
    }

    void setUpValueSetterManipulation(Trait manipulation) {
        setUpValueSetter(valueSetterManipulation, manipulation);
    }

    void setUpValueSetterComposure(Trait composure) {
        setUpValueSetter(valueSetterComposure, composure);
    }

    @SuppressWarnings("ConstantConditions")
    public String getString(int resId) {
        Resources resources = getActivity().getResources();

        return resources.getString(resId);
    }

    public void setValues(RealmList<Entry> entries) {
        for (Entry entry : entries) {
            for (ValueSetter setter : valueSetters.values()) {
                try {
                    if (entry.getKey()
                        .equalsIgnoreCase(setter.getTrait().getName())) {
                        setter.setCurrentValue(entry);
                    }
                } catch (NullPointerException e) {
                    Log.e(this.getClass().toString(), "" + e.getMessage());
                }
            }
        }
    }
}
