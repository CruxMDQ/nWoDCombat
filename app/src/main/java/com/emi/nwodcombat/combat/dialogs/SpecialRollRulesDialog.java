package com.emi.nwodcombat.combat.dialogs;

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

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.combat.adapters.RadioAdapter;
import com.emi.nwodcombat.interfaces.AfterSettingRulesListener;
import com.emi.nwodcombat.interfaces.OnChoicePickedListener;
import com.emi.nwodcombat.model.pojos.CombatRule;
import com.emi.nwodcombat.tools.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Emi on 2/19/16.
 */
public class SpecialRollRulesDialog extends DialogFragment implements OnChoicePickedListener {

    @BindView(R.id.rvOptions) RecyclerView rvOptions;

    String tag;
    String title;
    AfterSettingRulesListener listener;

    AlertDialog dialog;

    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, root);

        final RadioAdapter adapter = new RadioAdapter(getActivity(), generateRules(), this);

        rvOptions.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOptions.setAdapter(adapter);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(root);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CombatRule rule = (CombatRule) adapter.mItems.get(adapter.mSelectedItem);

                listener.afterSettingRules(rule);
            }
        });

        alertDialogBuilder.setTitle(title);

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
        unbinder.unbind();
    }

    private ArrayList<CombatRule> generateRules() {
        ArrayList<CombatRule> rules = new ArrayList<>();

        rules.add(new CombatRule(Constants.DICE_RULE_8_AGAIN, false, Constants.DICE_VALUE_8_AGAIN));
        rules.add(new CombatRule(Constants.DICE_RULE_9_AGAIN, false, Constants.DICE_VALUE_9_AGAIN));
        rules.add(new CombatRule(Constants.DICE_RULE_10_AGAIN, false, Constants.DICE_VALUE_10_AGAIN));
        rules.add(new CombatRule(Constants.DICE_RULE_NO_AGAIN, false, Constants.DICE_VALUE_NO_AGAIN));

        return rules;
    }

    @Override
    public void onChoicePicked(Object object) {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
    }
}
