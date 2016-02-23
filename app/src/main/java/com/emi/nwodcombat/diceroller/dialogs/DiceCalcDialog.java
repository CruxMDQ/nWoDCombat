package com.emi.nwodcombat.diceroller.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.diceroller.interfaces.AfterChoosingNumberListener;
import com.emi.nwodcombat.diceroller.interfaces.OnValueChangedListener;
import com.emi.nwodcombat.widgets.NumberPickerWidget;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/18/16.
 */
public class DiceCalcDialog extends DialogFragment implements OnValueChangedListener {
    int number = 0;
    String tag;
    String title;
    AfterChoosingNumberListener listener;

    AlertDialog dialog;

    @Bind(R.id.nbpkAttribute) NumberPickerWidget nbpkAttribute;
    @Bind(R.id.nbpkSkill) NumberPickerWidget nbpkSkill;
    @Bind(R.id.nbpkPotency) NumberPickerWidget nbpkPotency;
    @Bind(R.id.nbpkEquipment) NumberPickerWidget nbpkEquipment;

    public static DiceCalcDialog newInstance (String title, String tag, AfterChoosingNumberListener listener) {
        DiceCalcDialog fragment = new DiceCalcDialog();
        fragment.listener = listener;
        fragment.tag = tag;
        fragment.title = title;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_pick_dice, null);
        ButterKnife.bind(this, root);

        nbpkAttribute.setListener(this);
        nbpkEquipment.setListener(this);
        nbpkPotency.setListener(this);
        nbpkSkill.setListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title);

        builder.setView(root);

        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int total = nbpkAttribute.getNumber()
                    + nbpkSkill.getNumber()
                    + nbpkPotency.getNumber()
                    + nbpkEquipment.getNumber();
                listener.afterChoosingNumber(tag, total);
            }
        });

        dialog = builder.create();

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

    @Override
    public void onValueChanged(int value) {
        number += value;
        if (number > 0) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        } else {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }
}
