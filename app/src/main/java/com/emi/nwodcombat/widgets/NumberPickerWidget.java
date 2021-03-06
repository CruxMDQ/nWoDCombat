package com.emi.nwodcombat.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.interfaces.OnValueChangedListener;
import com.emi.nwodcombat.tools.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Emi on 2/19/16.
 */
public class NumberPickerWidget extends LinearLayout {

    @BindView(R.id.txtTitle) TextView txtTitle;
    @BindView(R.id.txtNumber) TextView txtNumber;
    @BindView(R.id.btnDecrease) Button btnDecrease;
    @BindView(R.id.btnIncrease) Button btnIncrease;

    private String title;
    private int number;
    private int minimum;
    private int maximum;

    private OnValueChangedListener listener;

    private Unbinder unbinder;

    public NumberPickerWidget(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray aAttrs = context
            .obtainStyledAttributes(attrs, R.styleable.NumberPickerWidget, 0, 0);

        setTitle(aAttrs.getString(R.styleable.NumberPickerWidget_widgetTitle));
        setMinimum(aAttrs.getInt(R.styleable.NumberPickerWidget_minimum, 1));
        setMaximum(aAttrs.getInt(R.styleable.NumberPickerWidget_maximum, 20));

        aAttrs.recycle();

        inflateLayout();

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number > minimum) {
                    number--;
                    if (listener != null) {
                        listener.onValueChanged(-1);
                    }
                }
                txtNumber.setText(String.valueOf(number));
            }
        });
        btnDecrease.setContentDescription(Constants.CONTENT_DESC_NBPK_BTN_MINUS + title);

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number < maximum) {
                    number++;
                    if (listener != null) {
                        listener.onValueChanged(1);
                    }
                }
                txtNumber.setText(String.valueOf(number));
            }
        });
        btnIncrease.setContentDescription(Constants.CONTENT_DESC_NBPK_BTN_PLUS + title);

        txtTitle.setText(title);

        number = minimum;
        txtNumber.setText(String.valueOf(number));
    }

    private void inflateLayout() {
        View view = inflate(this.getContext(), getLayout(), this);
        unbinder = ButterKnife.bind(this, view);
    }

    private int getLayout() {
        return R.layout.widget_number_picker;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(TextView txtTitle) {
        this.txtTitle = txtTitle;
    }

    public TextView getTxtNumber() {
        return txtNumber;
    }

    public void setTxtNumber(TextView txtNumber) {
        this.txtNumber = txtNumber;
    }

    public Button getBtnDecrease() {
        return btnDecrease;
    }

    public void setBtnDecrease(Button btnDecrease) {
        this.btnDecrease = btnDecrease;
    }

    public Button getBtnIncrease() {
        return btnIncrease;
    }

    public void setBtnIncrease(Button btnIncrease) {
        this.btnIncrease = btnIncrease;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (txtTitle != null) {
            txtTitle.setText(title);
        }
    }

    public int getMinimum() {
        return minimum;
    }

    private void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    private void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        if (txtNumber != null) {
            txtNumber.setText(String.valueOf(number));
        }
    }

    public OnValueChangedListener getListener() {
        return listener;
    }

    public void setListener(OnValueChangedListener listener) {
        this.listener = listener;
    }
}
