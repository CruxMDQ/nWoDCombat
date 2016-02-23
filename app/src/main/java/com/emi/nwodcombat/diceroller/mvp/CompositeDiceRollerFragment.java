package com.emi.nwodcombat.diceroller.mvp;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.diceroller.pojos.Rule;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/19/16.
 */
public class CompositeDiceRollerFragment extends Fragment implements CompositeDiceRollerContract.View {
    @Bind(R.id.panelDice) LinearLayout panelDice;
    @Bind(R.id.txtDice) TextView txtDice;
    @Bind(R.id.lblSpecialRules) TextView lblSpecialRules;
    @Bind(R.id.txtSpecialRules) TextView txtSpecialRules;

    private int diceNumber;
    private String actorTag;
    private Rule rule;

    private CompositeDiceRollerContract.InputListener mActionListener;

    public CompositeDiceRollerFragment() {
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        TypedArray aAttrs = context
            .obtainStyledAttributes(attrs, R.styleable.CompositeDiceRollerFragment, 0, 0);

        actorTag = aAttrs.getString(R.styleable.CompositeDiceRollerFragment_actorTag);

        aAttrs.recycle();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionListener = new CompositeDiceRollerPresenter(this, getActivity().getFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dice_roller_composite, container, false);

        ButterKnife.bind(this, view);

        setUpUI();

        setDefaultRules();

        return view;
    }

    private void setDefaultRules() {
        // TODO This is a crude mechanic, needs replacement
        rule = new Rule(Constants.DICE_RULE_10_AGAIN, 10);

        txtSpecialRules.setText(rule.getName());
    }

    private void setUpUI() {
        String tag = actorTag + ":";

        txtDice.setText(tag);
        txtDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.setDice(actorTag);
            }
        });

        lblSpecialRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.setSpecialRules(actorTag);
            }
        });
    }

    @Override
    public void afterChoosingDice(String tag, int number) {
        this.diceNumber = number;

        panelDice.removeAllViews();
        for (int i = 0; i < number; i++) {
            RadioButton rdb = new RadioButton(getContext());

            rdb.setChecked(true);

            rdb.setButtonDrawable(getResources().getDrawable(R.drawable.selector_points));

            panelDice.addView(rdb);
        }
    }

    @Override
    public void afterSettingRules(String tag, Rule rule) {
        this.rule = rule;
        if (rule != null) {
            txtSpecialRules.setText(rule.getName());
        } else txtSpecialRules.setText(R.string.rule_reroll_none);
    }

    @Override
    public void setInputListener(CompositeDiceRollerContract.InputListener listener) {
        mActionListener = listener;
    }

    public ArrayList<Integer> rollDice() {
        return mActionListener.rollDice(diceNumber, rule.getValue());
    }

    public String getActorTag() {
        return actorTag;
    }

    public void setActorTag(String actorTag) {
        this.actorTag = actorTag;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }
}
