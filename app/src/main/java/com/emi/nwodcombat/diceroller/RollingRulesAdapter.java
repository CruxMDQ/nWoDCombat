package com.emi.nwodcombat.diceroller;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;

import java.util.ArrayList;

/**
 * Created by Emi on 2/19/16.
 */
public class RollingRulesAdapter extends RecyclerView.Adapter<RollingRulesAdapter.ChoiceRowHolder> {
    private ArrayList<String> rules;
    private OnChoicePickedListener listener;

    public RollingRulesAdapter(ArrayList<String> rules, OnChoicePickedListener listener) {
        this.rules = rules;
        this.listener = listener;
    }

    @Override
    public ChoiceRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_choice, null);
        return new ChoiceRowHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(ChoiceRowHolder holder, int position) {
        holder.rule = rules.get(position);
        holder.checkBox.setText(Html.fromHtml(rules.get(position)));
        if (rules.get(position).equals(Constants.DICE_RULE_10_AGAIN)) {
            holder.checkBox.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return rules.size();
    }

    public class ChoiceRowHolder extends RecyclerView.ViewHolder {

        String rule;
        CheckBox checkBox;
        OnChoicePickedListener listener;

        public ChoiceRowHolder(View view, final OnChoicePickedListener listener) {
            super(view);
            this.listener = listener;
            this.checkBox = (CheckBox) view.findViewById(R.id.chkFeatureChoice);
            this.checkBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        listener.onChoicePicked(new Pair(rule, isChecked));
                    }
                });
        }
    }
}
