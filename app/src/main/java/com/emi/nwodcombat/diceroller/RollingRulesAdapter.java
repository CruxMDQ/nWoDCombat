package com.emi.nwodcombat.diceroller;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.diceroller.interfaces.OnChoicePickedListener;
import com.emi.nwodcombat.diceroller.pojos.Rule;

import java.util.ArrayList;

/**
 * Created by Emi on 2/19/16.
 */
public class RollingRulesAdapter extends RecyclerView.Adapter<RollingRulesAdapter.ChoiceRowHolder> {
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;

    private ArrayList<Rule> rules;
    private OnChoicePickedListener listener;

    public RollingRulesAdapter(ArrayList<Rule> rules, OnChoicePickedListener listener) {
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
        holder.checkBox.setText(Html.fromHtml(rules.get(position).getName()));
        if (rules.get(position).equals(Constants.DICE_RULE_10_AGAIN)) {
            holder.checkBox.setChecked(true);
        }

        holder.checkBox.setChecked(rules.get(position).isSelected());
        holder.checkBox.setTag(new Integer(position));

        //for default check in first item
        if(position == 0 && rules.get(0).isSelected() && holder.checkBox.isChecked())
        {
            lastChecked = holder.checkBox;
            lastCheckedPos = 0;
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckBox cb = (CheckBox)v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        rules.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

                rules.get(clickedPos).setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rules.size();
    }

    public class ChoiceRowHolder extends RecyclerView.ViewHolder {

        Rule rule;
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
                        listener.onChoicePicked(!rule.isSelected() ? rule : null);
                    }
                });
        }
    }
}
