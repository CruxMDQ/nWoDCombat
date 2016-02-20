package com.emi.nwodcombat.diceroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;

import java.util.ArrayList;
import java.util.Iterator;

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

    private String actorTag;
    private ArrayList<android.support.v4.util.Pair<String, Boolean>> rules = new ArrayList<>();

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
        rules.add(new Pair<>(Constants.DICE_RULE_10_AGAIN, true));

        txtSpecialRules.setText(Constants.DICE_RULE_10_AGAIN);
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
        panelDice.removeAllViews();
        for (int i = 0; i < number; i++) {
            RadioButton rdb = new RadioButton(getContext());

            rdb.setChecked(true);

            rdb.setButtonDrawable(getResources().getDrawable(R.drawable.selector_points));

            panelDice.addView(rdb);
        }
    }

    @Override
    public void afterSettingRules(String tag, ArrayList<android.support.v4.util.Pair<String, Boolean>> rules) {
        this.rules = rules;

        String ruleset = "";

        for (android.support.v4.util.Pair<String, Boolean> rule : this.rules) {
            if (rule.second) {
                ruleset = ruleset + rule.first;
            }
        }

        txtSpecialRules.setText(ruleset);

        ArrayList<Pair<String, Boolean>> trueRules = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();

        for (Pair<String, Boolean> rule : this.rules) {
            if (rule.second) {
                trueRules.add(rule);
            }
        }

        if (trueRules.size() > 0) {
            if (trueRules.size() == 1) {
                stringBuilder.append(trueRules.get(0).first);
            } else if (trueRules.size() == 2) {
                stringBuilder.append(getString(R.string.rules_template_two, trueRules.get(0).first, trueRules.get(1).first));
            } else if (trueRules.size() >= 3) {
                Iterator iterator = trueRules.iterator();

                while (iterator.hasNext()) {
                    Pair<String, Boolean> rule = (Pair<String, Boolean>) iterator.next();

                    stringBuilder.append(rule.first);

                    if (iterator.hasNext()) {
                        stringBuilder.append(", ");
                    }
                }
            }
            txtSpecialRules.setText(stringBuilder.toString());
        }
    }

    @Override
    public void setInputListener(CompositeDiceRollerContract.InputListener listener) {
        mActionListener = listener;
    }

    public String getActorTag() {
        return actorTag;
    }

    public void setActorTag(String actorTag) {
        this.actorTag = actorTag;
    }
}
