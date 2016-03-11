package com.emi.nwodcombat.widgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class ValueSetterWidget extends LinearLayout {
    private static SharedPreferences preferences;

    @Bind(R.id.lblValue) TextView lblValue;
    @Bind(R.id.panelValue) LinearLayout panelValue;
    @Bind(R.id.btnValueDecrease) Button btnValueDecrease;
    @Bind(R.id.btnValueIncrease) Button btnValueIncrease;

    private String valueName;
    private String traitCategory;
    private int defaultValue;
    private int maximumValue;
    private int currentValue;

    private OnTraitChangedListener listener;

    public ValueSetterWidget(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (ValueSetterWidget.preferences == null) {
            preferences = context.getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
        }

        if (!isInEditMode()) {
            TypedArray aAttrs = context.obtainStyledAttributes(attrs, R.styleable.ValueSetterWidget, 0, 0);

            setValueName(aAttrs.getString(R.styleable.ValueSetterWidget_valueName));
            setDefaultValue(aAttrs.getInteger(R.styleable.ValueSetterWidget_valueDefault, 1));

            if (preferences.getBoolean(Constants.SETTING_IGNORE_STAT_CAPS, false)) {
                setMaximumValue(aAttrs.getInteger(R.styleable.ValueSetterWidget_valueMaximum, 20));
            } else {
                setMaximumValue(aAttrs.getInteger(R.styleable.ValueSetterWidget_valueMaximum, 5));
            }

            setTraitCategory(aAttrs.getString(R.styleable.ValueSetterWidget_traitCategory));

            currentValue = defaultValue;

            aAttrs.recycle();

            inflateLayout();

            btnValueDecrease.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentValue > defaultValue) {
                        listener.onTraitChanged(ValueSetterWidget.this, -1);
                    }
                }
            });
            btnValueIncrease.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentValue < maximumValue) {
                        listener.onTraitChanged(ValueSetterWidget.this, 1);
                    }
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

    private void inflateLayout() {
        View view = inflate(this.getContext(), getLayout(), this);
        ButterKnife.bind(this, view);
    }

    private int getLayout() {
        return R.layout.widget_value_setter;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
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

    public int increaseCurrentValue() {
        currentValue++;
        refreshPointsPanel();
        return 1;
    }

    public int decreaseCurrentValue() {
        currentValue--;
        refreshPointsPanel();
        return -1;
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

    public String getTraitCategory() {
        return traitCategory;
    }

    public void setTraitCategory(String traitCategory) {
        this.traitCategory = traitCategory;
    }

    public void setBtnValueDecreaseEnabled(boolean isEnabled) {
        btnValueDecrease.setEnabled(isEnabled);
    }

    public void setBtnValueIncreaseEnabled(boolean isEnabled) {
        btnValueIncrease.setEnabled(isEnabled);
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

    public int getCurrentValue() {
        return currentValue;
    }

    public int changeValue(int value, int pool) {
        if (value > 0) {
            if (pool > 0) {
                pool -= increaseCurrentValue();
            }
        } else {
            pool -= decreaseCurrentValue();
        }
        return pool;
    }
}
