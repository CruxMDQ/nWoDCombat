package com.emi.nwodcombat.diceroller.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.diceroller.RollingRulesAdapter;
import com.emi.nwodcombat.diceroller.interfaces.AfterSettingRulesListener;
import com.emi.nwodcombat.diceroller.interfaces.OnChoicePickedListener;
import com.emi.nwodcombat.diceroller.pojos.Rule;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/19/16.
 */
public class SpecialRollRulesDialog extends DialogFragment implements OnChoicePickedListener {

    @Bind(R.id.rvOptions) RecyclerView rvOptions;

    String tag;
    String title;
    AfterSettingRulesListener listener;

    AlertDialog dialog;

    Rule rule;
    ArrayList<Rule> rules = new ArrayList<>();

    public static SpecialRollRulesDialog newInstance (String title, String tag, AfterSettingRulesListener listener) {
        SpecialRollRulesDialog fragment = new SpecialRollRulesDialog();
        fragment.listener = listener;
        fragment.tag = tag;
        fragment.title = title;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.dialog_special_rules, null);
        ButterKnife.bind(this, root);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(root);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.afterSettingRules(tag, rule);
            }
        });

        alertDialogBuilder.setTitle(title);

        rvOptions.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOptions.setAdapter(new RollingRulesAdapter(generateRules(), this));

        dialog = alertDialogBuilder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setEnabled(false);
            }
        });

        return dialog;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private ArrayList<Rule> generateRules() {
        ArrayList<Rule> rules = new ArrayList<>();

        rules.add(new Rule(Constants.DICE_RULE_8_AGAIN, false));
        rules.add(new Rule(Constants.DICE_RULE_9_AGAIN, false));
        rules.add(new Rule(Constants.DICE_RULE_10_AGAIN, false));

        return rules;
    }

    @Override
    public void onChoicePicked(Object pick) {
        if (pick != null) {
            Rule rule = (Rule) pick;

            switch (rule.getName()) {
                case Constants.DICE_RULE_8_AGAIN: {
                    rule.setValue(Constants.DICE_VALUE_8_AGAIN);
                    break;
                }
                case Constants.DICE_RULE_9_AGAIN: {
                    rule.setValue(Constants.DICE_VALUE_9_AGAIN);
                    break;
                }
                case Constants.DICE_RULE_10_AGAIN: {
                    rule.setValue(Constants.DICE_VALUE_10_AGAIN);
                    break;
                }
            }
            this.rule = rule;
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        } else {
            rule = null;
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    private void addPick(Object pick) {
        Rule rule = (Rule) pick;

        for(Rule p : rules) {
            if (p.getName().equals(rule.getName())) {
                rules.remove(p);
                break;
            }
        }

        rules.add(rule);
    }
}
