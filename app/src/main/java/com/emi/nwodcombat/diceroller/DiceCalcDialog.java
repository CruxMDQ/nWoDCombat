package com.emi.nwodcombat.diceroller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.widgets.NumberPickerWidget;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/18/16.
 */
public class DiceCalcDialog extends DialogFragment {
    int number = 0;
    String tag;
    String title;
    AfterChoosingNumberListener listener;

    @Bind(R.id.nbpkAttribute) NumberPickerWidget nbpkAttribute;
    @Bind(R.id.nbpkSkill) NumberPickerWidget nbpkSkill;
    @Bind(R.id.nbpkPotency) NumberPickerWidget nbpkPotency;

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

        return new AlertDialog.Builder(getActivity())
            .setTitle(title)
            .setView(root)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int total = nbpkAttribute.getNumber()
                        + nbpkSkill.getNumber()
                        + nbpkPotency.getNumber();
                    listener.afterChoosingNumber(tag, total);
                }
            })
            .create();

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
