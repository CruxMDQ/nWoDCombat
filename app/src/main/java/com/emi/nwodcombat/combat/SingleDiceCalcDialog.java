package com.emi.nwodcombat.combat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.diceroller.interfaces.OnValueChangedListener;
import com.emi.nwodcombat.interfaces.AfterChoosingValueAndIndexListener;
import com.emi.nwodcombat.widgets.NumberPickerWidget;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/29/16.
 */
public class SingleDiceCalcDialog extends DialogFragment implements OnValueChangedListener {
    String title;
    int number = 0;
    int adapterPosition = 0;
    AfterChoosingValueAndIndexListener listener;

    AlertDialog dialog;

    @Bind(R.id.nbpk) NumberPickerWidget nbpk;

    public static SingleDiceCalcDialog newInstance (String title, int number, int adapterPosition, AfterChoosingValueAndIndexListener listener) {
        SingleDiceCalcDialog fragment = new SingleDiceCalcDialog();
        fragment.title = title;
        fragment.number = number;
        fragment.listener = listener;
        fragment.adapterPosition = adapterPosition;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.dialog_single_dice, null);
        ButterKnife.bind(this, root);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        nbpk.setListener(this);

        builder.setView(root);

        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.afterChoosingValueAndIndex(adapterPosition, nbpk.getNumber());
            }
        });

        dialog = builder.create();

        nbpk.setTitle(title);
        nbpk.setNumber(number);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setEnabled(false);
            }
        });

        return dialog;
    }

    @Override
    public void onDestroyView() {
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
