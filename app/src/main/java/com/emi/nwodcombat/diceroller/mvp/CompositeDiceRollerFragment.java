package com.emi.nwodcombat.diceroller.mvp;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/19/16.
 */
public class CompositeDiceRollerFragment extends Fragment implements CompositeDiceRollerContract.View {
    @Bind(R.id.card) android.support.v7.widget.CardView card;
    @Bind(R.id.panel) LinearLayout panel;
    @Bind(R.id.panelDice) LinearLayout panelDice;
    @Bind(R.id.txtDice) TextView txtDice;
    @Bind(R.id.lblPickDice) TextView lblPickDice;
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
        diceNumber = aAttrs.getInteger(R.styleable.CompositeDiceRollerFragment_numberOfDice, 0);

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        afterChoosingDice(actorTag, diceNumber);
    }

    private void setDefaultRules() {
        // TODO This is a crude mechanic, needs replacement
        rule = new Rule(Constants.DICE_RULE_10_AGAIN, 10);

        txtSpecialRules.setText(rule.getName());
    }

    private void setUpUI() {
        txtDice.setText(WordUtils.capitalize(actorTag));
        lblPickDice.setText(getString(R.string.card_pick_dice_notice, actorTag));
        card.setOnClickListener(new View.OnClickListener() {
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
        lblSpecialRules.setContentDescription(Constants.CONTENT_DESC_SPECIAL_RULES + actorTag);
    }

    @Override
    public void afterChoosingDice(String tag, int number) {
        if (number != 0) {
            this.diceNumber = number;
            card.setVisibility(View.GONE);
            panel.setVisibility(View.VISIBLE);

            panelDice.removeAllViews();

            for (int i = 0; i < number; i++) {
                RadioButton rdb = new RadioButton(getContext());

                rdb.setChecked(true);

                rdb.setButtonDrawable(getResources().getDrawable(R.drawable.selector_points));

                panelDice.addView(rdb);
            }
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
