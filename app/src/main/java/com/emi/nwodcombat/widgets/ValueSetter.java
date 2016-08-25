package com.emi.nwodcombat.widgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.model.pojos.Trait;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.tools.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Emi on 3/1/16.
 */
public class ValueSetter extends LinearLayout {
    private static SharedPreferences preferences;
    private boolean showEditionPanel;

    @Bind(R.id.lblValue) TextView lblValue;
    @Bind(R.id.panelValue) LinearLayout panelValue;
    @Bind(R.id.panelEdition) LinearLayout panelEdition;
    @Bind(R.id.btnValueDecrease) Button btnValueDecrease;
    @Bind(R.id.btnValueIncrease) Button btnValueIncrease;

    @Bind(R.id.btnSpecialty) Button btnSpecialty;
    @Bind(R.id.chkSpecialty) CheckBox chkSpecialty;
    @Bind(R.id.chkTemplateSpecial) CheckBox chkTemplateSpecial;

    private String valueName;
    private String traitCategory;
    private int defaultValue;
    private int maximumValue;
    private int currentValue;
    private Trait trait;

    private OnTraitChangedListener listener;

    private int pointCost;

    public ValueSetter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ValueSetter(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (ValueSetter.preferences == null) {
            preferences = context.getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
        }

        if (!isInEditMode()) {
            TypedArray aAttrs = context.obtainStyledAttributes(attrs, R.styleable.ValueSetter, 0, 0);

            showEditionPanel = aAttrs.getBoolean(R.styleable.ValueSetter_showEditionPanel, true);
            setValueName(aAttrs.getString(R.styleable.ValueSetter_valueName));
            setDefaultValue(aAttrs.getInteger(R.styleable.ValueSetter_valueDefault, 0));

            int absoluteTraitMaximum;

            if (preferences.getBoolean(Constants.SETTING_IGNORE_STAT_CAPS, false)) {
                absoluteTraitMaximum = 20;
            } else {
                absoluteTraitMaximum = 5;
            }

            setMaximumValue(aAttrs.getInteger(R.styleable.ValueSetter_valueMaximum, absoluteTraitMaximum));

            setTrait(new Trait(aAttrs.getString(R.styleable.ValueSetter_traitKind),
                aAttrs.getString(R.styleable.ValueSetter_traitCategory1),
                aAttrs.getString(R.styleable.ValueSetter_traitCategory2)));

            setPointCost(aAttrs.getInt(R.styleable.ValueSetter_pointCost, 1));

            currentValue = defaultValue;

            aAttrs.recycle();

            inflateLayout();

            btnValueDecrease.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int change = -1;

                    boolean condition = currentValue > defaultValue;

                    sendValueChange(change, condition);
                }
            });

            btnValueIncrease.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int change = 1;

                    boolean condition = currentValue < maximumValue;

                    sendValueChange(change, condition);
                }
            });

            refreshPointsPanel();

            if (lblValue != null) {
                this.lblValue.setText(valueName);
            }
        } else {
            inflateLayout();
        }
    }

    private void sendValueChange(int change, boolean condition) {
        if (condition) {
            listener.onTraitChanged(change,
                ValueSetter.this.getTrait().getName(),
                ValueSetter.this.getTrait().getKind(),
                ValueSetter.this.getTrait().getCategory1());
        }
    }

    private void inflateLayout() {
        View view = inflate(this.getContext(), getLayout(), this);
        ButterKnife.bind(this, view);
        if (showEditionPanel) {
            showEditionPanel();
        } else {
            hideEditionPanel();
        }

    }

    private int getLayout() {
        return R.layout.widget_value_setter;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public void setLabel(String label) {
        this.valueName = label;
        lblValue.setText(label);
    }

    public String getValueName() {
        return valueName;
    }

    public OnTraitChangedListener getListener() {
        return listener;
    }

    public void setListener(OnTraitChangedListener listener) {
        this.listener = listener;
    }

    public void refreshPointsPanel() {
        panelValue.removeAllViews();

        for (int i = 0; i < currentValue; i++) {
            RadioButton rdb = new RadioButton(getContext());

            rdb.setChecked(true);

            rdb.setButtonDrawable(getContext().getResources().getDrawable(R.drawable.selector_points));

            panelValue.addView(rdb);
        }

    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        refreshPointsPanel();
    }

    public void setCurrentValue(Entry entry) {
        this.setCurrentValue(Integer.valueOf(entry.getValue()));
        this.setDefaultValue(Integer.valueOf(entry.getValue()));
        this.setTag(entry);
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setPointCost(int pointCost) {
        this.pointCost = pointCost;
    }

    public void hideEditionPanel() {
        panelEdition.setVisibility(INVISIBLE);
    }

    public void showEditionPanel() {
        panelEdition.setVisibility(VISIBLE);
    }

    public void toggleEditionPanel(boolean isVisible) {
        if (isVisible) {
            showEditionPanel();
            btnValueDecrease.setVisibility(VISIBLE);
            btnValueIncrease.setVisibility(VISIBLE);
        } else {
            hideEditionPanel();
            btnValueDecrease.setVisibility(INVISIBLE);
            btnValueIncrease.setVisibility(INVISIBLE);
        }
    }

    public void onCharacterExperienceChanged(int experiencePool) {
        showEditionPanel();

        enableIncreaseButton(experiencePool >= pointCost);

        enableDecreaseButton(currentValue <= defaultValue);

        /** Pseudocode:
         * Star should be visible if:
         * - Experience is greater than 0
         * - Current score is greater than 0
         * - Value category is a skill
         */
        enableSpecialtyButton(experiencePool > 0
            && currentValue > 0
            && getTrait().getKind().equals(getResources().getString(R.string.kind_skill)));
    }

    private void enableDecreaseButton(boolean enable) {
        if (enable) {
            btnValueDecrease.setVisibility(INVISIBLE);
        } else {
            btnValueDecrease.setVisibility(VISIBLE);
        }
    }

    private void enableIncreaseButton(boolean enable) {
        if (enable) {
            btnValueIncrease.setVisibility(VISIBLE);
        } else {
            btnValueIncrease.setVisibility(INVISIBLE);
        }
    }

    public void setValue(int value) {
        currentValue = value;
        refreshPointsPanel();
    }

    @OnCheckedChanged(R.id.chkSpecialty)
    void onSpecialtyChecked() {
        listener.onSpecialtyTapped(chkSpecialty.isChecked(),
            ValueSetter.this.getTrait().toString(),
            ValueSetter.this.getTrait().getCategory1());
    }

    public void enableSpecialtyCheckbox(boolean isEnabled) {
        if (isEnabled) {
            chkSpecialty.setVisibility(VISIBLE);
        } else {
            chkSpecialty.setVisibility(GONE);
        }
    }

    public boolean isSpecialtyEnabled() {
        return btnSpecialty.isEnabled();
//        return chkSpecialty.isEnabled();
    }

    public boolean hasSpecialtiesLoaded() {
        return btnSpecialty.getContentDescription().toString().equalsIgnoreCase(
            Constants.SKILL_SPECIALTY_LOADED);
//        return chkSpecialty.isChecked();
    }

    public void setSpecialtyChecked(boolean isChecked) {
        chkSpecialty.setChecked(isChecked);
    }

    @OnClick(R.id.btnSpecialty)
    void onSpecialtyClicked() {
        listener.onSpecialtyTapped(buttonHasSpecialties(),
            ValueSetter.this.getTrait().getName(),
            ValueSetter.this.getTrait().getCategory1());
    }

    private boolean buttonHasSpecialties() {
        return btnSpecialty.getContentDescription() != null && btnSpecialty.getContentDescription().equals(
            Constants.SKILL_SPECIALTY_LOADED);
    }

    public void enableSpecialtyButton(boolean isEnabled) {
        if (isEnabled) {
            btnSpecialty.setVisibility(VISIBLE);
        } else {
            btnSpecialty.setVisibility(GONE);
        }
    }

    public void changeSpecialtyButtonBackground(int resId, String contentDescription) {
        btnSpecialty.setBackgroundResource(resId);
    }

    public Trait getTrait() {
        return trait;
    }

    public void setTrait(Trait trait) {
        this.trait = trait;
    }
}
