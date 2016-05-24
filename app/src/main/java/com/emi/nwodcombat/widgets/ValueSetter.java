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

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.OnTraitChangedListener;
import com.emi.nwodcombat.interfaces.ExperienceSpender;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class ValueSetter extends LinearLayout implements ExperienceSpender {
    private static SharedPreferences preferences;
    private boolean showEditionPanel;

    @Bind(R.id.lblValue) TextView lblValue;
    @Bind(R.id.panelValue) LinearLayout panelValue;
    @Bind(R.id.panelEdition) LinearLayout panelEdition;
    @Bind(R.id.btnValueDecrease) Button btnValueDecrease;
    @Bind(R.id.btnValueIncrease) Button btnValueIncrease;

    private String valueName;
    private String traitCategory;
    private int defaultValue;
    private int maximumValue;
    private int currentValue;

    private OnTraitChangedListener listener;

    private int pointCost;

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

            if (preferences.getBoolean(Constants.SETTING_IGNORE_STAT_CAPS, false)) {
                setMaximumValue(aAttrs.getInteger(R.styleable.ValueSetter_valueMaximum, 20));
            } else {
                setMaximumValue(aAttrs.getInteger(R.styleable.ValueSetter_valueMaximum, 5));
            }

            setTraitCategory(aAttrs.getString(R.styleable.ValueSetter_traitCategory));
            setPointCost(aAttrs.getInt(R.styleable.ValueSetter_pointCost, 1));

            currentValue = defaultValue;

            aAttrs.recycle();

            inflateLayout();

            btnValueDecrease.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentValue > defaultValue) {
                        listener.onTraitChanged(ValueSetter.this, -1,
                            ValueSetter.this.getContentDescription().toString(),
                            ValueSetter.this.getTraitCategory());
                    }
                }
            });
            btnValueIncrease.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentValue < maximumValue) {
                        listener.onTraitChanged(ValueSetter.this, 1,
                            ValueSetter.this.getContentDescription().toString(),
                            ValueSetter.this.getTraitCategory());
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
        if (currentValue < maximumValue) {
            currentValue++;
        }
        refreshPointsPanel();
        return 1;
    }

    public int decreaseCurrentValue() {
        if (currentValue > defaultValue) {
            currentValue--;
        }
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

    public void setCurrentValue(Entry entry) {
        this.setCurrentValue(Integer.valueOf(entry.getValue()));
        this.setDefaultValue(Integer.valueOf(entry.getValue()));
        this.setTag(entry);
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void changeValue(int value) {
        if (value > 0) {
            increaseCurrentValue();
        } else {
            decreaseCurrentValue();
        }
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

    public int changeValue(int value, int pool, int cost) {
        int result = pool;

        if (value > 0) {
            if (result >= cost) {
                increaseCurrentValue();
                result -= cost;
            }
        } else {
            decreaseCurrentValue();
            result += cost;
        }
        return result;
    }

    public int getPointCost() {
        return pointCost;
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

    @Override
    public void onCharacterExperienceChanged(int experiencePool) {
        showEditionPanel();
        if (experiencePool >= pointCost) {
            btnValueIncrease.setVisibility(VISIBLE);
//            showEditionPanel();
        } else {
            btnValueIncrease.setVisibility(INVISIBLE);
//            hideEditionPanel();
        }

        if (currentValue <= defaultValue) {
            btnValueDecrease.setVisibility(INVISIBLE);
        } else {
            btnValueDecrease.setVisibility(VISIBLE);
        }
    }

    public void setValue(int value) {
        currentValue = value;
        refreshPointsPanel();
    }
}
