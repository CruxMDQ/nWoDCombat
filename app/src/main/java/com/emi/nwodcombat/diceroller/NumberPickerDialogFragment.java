package com.emi.nwodcombat.diceroller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/18/16.
 */
public class NumberPickerDialogFragment extends DialogFragment {
    int number = 0;
    String tag;
    String title;
    AfterChoosingNumberListener listener;

    @Bind(R.id.txtNumber) TextView txtNumber;
    @Bind(R.id.btnDecrease) Button btnDecrease;
    @Bind(R.id.btnIncrease) Button btnIncrease;

    public static NumberPickerDialogFragment newInstance (String title, String tag, AfterChoosingNumberListener listener) {
        NumberPickerDialogFragment fragment = new NumberPickerDialogFragment();
        fragment.listener = listener;
        fragment.tag = tag;
        fragment.title = title;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_pick_dice, null);
        ButterKnife.bind(this, root);

        txtNumber.setText(String.valueOf(number));

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number > 1) {
                    number--;
                }
                txtNumber.setText(String.valueOf(number));
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                txtNumber.setText(String.valueOf(number));
            }
        });

        return new AlertDialog.Builder(getActivity())
            .setTitle(title)
            .setView(root)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.afterChoosingNumber(tag, number);
                }
            })
            .create();

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
