package com.emi.nwodcombat.combat.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.tools.Roller;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/29/16.
 */
public class CombatDialog extends DialogFragment {
    private int attackerPool;
    private int attackerRerollThreshold;
    private int defense;

    @Bind(R.id.txtAttackerPool) TextView txtAttackerPool;
    @Bind(R.id.txtDefense) TextView txtDefense;
    @Bind(R.id.txtAttackerRolls) TextView txtAttackerRolls;
    @Bind(R.id.txtResult) TextView txtResult;

    private AlertDialog dialog;

    public static CombatDialog newInstance(int attackerPool, int attackerRerollThreshold, int defense) {
        CombatDialog fragment = new CombatDialog();
        fragment.attackerPool = attackerPool;
        fragment.attackerRerollThreshold = attackerRerollThreshold;
        fragment.defense = defense;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.dialog_combat, null);
        ButterKnife.bind(this, root);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(root);

        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();

        switch (attackerRerollThreshold) {
            case 8: {
                txtAttackerPool.setText(getString(R.string.dialog_attacker_pool_rerolls, attackerPool, attackerRerollThreshold));
                break;
            }
            case 9: {
                txtAttackerPool.setText(getString(R.string.dialog_attacker_pool_rerolls, attackerPool, attackerRerollThreshold));
                break;
            }
            case 10: {
                txtAttackerPool.setText(getString(R.string.dialog_attacker_pool, attackerPool));
                break;
            }
            case 11: {
                txtAttackerPool.setText(getString(R.string.dialog_attacker_pool_no_rerolls, attackerPool));
            }
        }

        txtDefense.setText(getString(R.string.dialog_defender_pool, defense));

        int toRoll = attackerPool - defense;
        int successes = 0;
        if (toRoll < 1) {
            // TODO special case = chance die
            toRoll = 1;
        } else {
            StringBuilder attackerReport = new StringBuilder();

            ArrayList<Integer> rolls = Roller.rollNWoDDice(toRoll, 10, attackerRerollThreshold);

            Iterator a = rolls.iterator();

            while (a.hasNext()) {
                Integer integer = (Integer) a.next();
                if (integer >= 8) {
                    successes++;
                }
                attackerReport.append(String.valueOf(integer));
                if (a.hasNext()) {
                    attackerReport.append(", ");
                }
            }

            txtAttackerRolls.setText(attackerReport.toString());

            if (successes != 0) {
                txtResult.setText(getResources().getQuantityString(R.plurals.dialog_attacker_result, successes, successes));
            } else {
                txtResult.setText(getString(R.string.dialog_attacker_result_failure));
            }

        }

        return dialog;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
