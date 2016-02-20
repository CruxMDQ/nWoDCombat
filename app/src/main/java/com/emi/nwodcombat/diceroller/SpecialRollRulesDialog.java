package com.emi.nwodcombat.diceroller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;

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

    ArrayList<Pair<String, Boolean>> rules = new ArrayList<>();

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
                listener.afterSettingRules(tag, rules);
            }
        });

        alertDialogBuilder.setTitle(title);

        rvOptions.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOptions.setAdapter(new RollingRulesAdapter(generateRules(), this));

        return alertDialogBuilder.create();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private ArrayList<String> generateRules() {
        ArrayList<String> rules = new ArrayList<>();

        rules.add(Constants.DICE_RULE_8_AGAIN);
        rules.add(Constants.DICE_RULE_9_AGAIN);
        rules.add(Constants.DICE_RULE_10_AGAIN);

        return rules;
    }

    @Override
    public void onChoicePicked(Object pick) {
        addPick(pick);
    }

    private void addPick(Object pick) {
        Pair<String, Boolean> rule = (Pair<String, Boolean>) pick;

        for (Pair<String, Boolean> p : rules) {
            if (p.first.equals(rule.first)) {
                rules.remove(p);
                break;
            }
        }
        rules.add(rule);
    }
}
