package com.emi.nwodcombat.combat.mvp;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.combat.adapters.ValuesAdapter;
import com.emi.nwodcombat.model.pojos.Rule;
import com.emi.nwodcombat.model.pojos.Value;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class CombatantInfoFragment extends Fragment implements CombatantInfoContract.View {
    @Bind(R.id.rvCombatantValues) RecyclerView rvCombatantValues;
    @Bind(R.id.btnSetCombatantThreshold) Button btnSetCombatantThreshold;
    @Bind(R.id.lblCombatant) TextView lblCombatant;

    private String combatantTag;
    private Rule rule;
    private ArrayList<Value> combatantValues;

    private CombatantInfoContract.InputListener actionListener;

    public CombatantInfoFragment() {
    }

    public static CombatantInfoFragment newInstance(ArrayList<Value> combatantModifiers) {
        CombatantInfoFragment fragment = new CombatantInfoFragment();
        fragment.combatantValues = combatantModifiers;
        return fragment;
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        TypedArray aAttrs = context.obtainStyledAttributes(attrs, R.styleable.CombatantInfoFragment, 0, 0);

        combatantTag = aAttrs.getString(R.styleable.CombatantInfoFragment_combatantTag);

        aAttrs.recycle();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionListener = new CombatantInfoPresenter(this, getActivity().getFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_combatant, container, false);

        ButterKnife.bind(this, view);

        setUpUI();

        setDefaultRules();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setDefaultRules() {
        rule = new Rule(Constants.DICE_RULE_10_AGAIN, 10);

        btnSetCombatantThreshold.setText(rule.getName());
    }

    private void setUpUI() {
        final ValuesAdapter valuesAdapter = new ValuesAdapter<>(this.combatantValues, getActivity());

        rvCombatantValues.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCombatantValues.setAdapter(valuesAdapter);

        btnSetCombatantThreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.setSpecialRules(combatantTag);
            }
        });

        lblCombatant.setText(WordUtils.capitalize(combatantTag));
    }

    @Override
    public void afterSettingRules(Rule rule) {
        this.rule = rule;
        btnSetCombatantThreshold.setText(rule.getName());
    }

    @Override
    public void setInputListener(CombatantInfoContract.InputListener listener) {
        actionListener = listener;
    }

    public int getCombatantTotal() {
        return ((ValuesAdapter) rvCombatantValues.getAdapter()).getSum();
    }

    public int getCombatantThreshold() {
        return rule.getValue();
    }

    public String getCombatantTag() {
        return combatantTag;
    }

    public void setCombatantTag(String combatantTag) {
        this.combatantTag = combatantTag;
        if (lblCombatant != null) {
            lblCombatant.setText(combatantTag);
        }
    }
}
