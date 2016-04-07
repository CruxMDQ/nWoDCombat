package com.emi.nwodcombat.charactercreator.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.AfterCreatingRecordListener;
import com.emi.nwodcombat.model.pojos.Record;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Crux on 3/26/2016.
 */
public class AddRecordDialog<T extends Record> extends DialogFragment {
    @Bind(R.id.editRecordName) EditText editRecordName;

    private Class<T> clazz;
    private String hint;
    private String title;
    private AfterCreatingRecordListener listener;

    AlertDialog dialog;

    public static AddRecordDialog newInstance (
            Class clazz,
            String title,
            String hint,
            AfterCreatingRecordListener listener
    ) {
        AddRecordDialog fragment = new AddRecordDialog();
        fragment.clazz = clazz;
        fragment.title = title;
        fragment.hint = hint;
        fragment.listener = listener;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout root = (LinearLayout) inflater.inflate(getLayout(), null);
        ButterKnife.bind(this, root);

        editRecordName.setHint(hint);
        editRecordName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(root);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Record record = getInstanceOfT();

                record.setName(editRecordName.getText().toString());

                listener.afterCreatingRecord(record);
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
        ButterKnife.unbind(this);
    }

    private int getLayout() {
        return R.layout.dialog_new_record_name;
    }

    public T getInstanceOfT() {
        try {
            return clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
