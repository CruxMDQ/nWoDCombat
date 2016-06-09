package com.emi.nwodcombat.characterwizard.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.adapters.SpecialtyAdapter;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardModel;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.utils.BusProvider;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 03/06/2016.
 */
public class AddSpecialtyDialog extends DialogFragment {
    @Bind(R.id.editSpecialtyName) EditText editSpecialtyName;
    @Bind(R.id.rvDialogSpecialties) RecyclerView rvDialogSpecialties;

    private String title;
    private String key;

    private RealmList<Entry> specialties;

    private CharacterWizardModel model;

    AlertDialog dialog;
    public SpecialtyAdapter specialtyAdapter;

    public static AddSpecialtyDialog newInstance(String title, String key, CharacterWizardModel model) {
        AddSpecialtyDialog fragment = new AddSpecialtyDialog();
        fragment.title = title;
        fragment.key = key;
        fragment.model = model;
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout root = (LinearLayout) inflater.inflate(getLayout(), null);
        ButterKnife.bind(this, root);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        lm.setAutoMeasureEnabled(true);
        rvDialogSpecialties.setLayoutManager(lm);

        specialties = model.getSpecialties(key);

        specialtyAdapter = new SpecialtyAdapter(specialties, getActivity(), R.layout.row_specialty,
            BusProvider.getInstance());

        rvDialogSpecialties.setAdapter(specialtyAdapter);

        editSpecialtyName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO Introduce check to avoid repeated values

                    System.out.println("actionId= "+ actionId);

                    addSpecialty();

                    return false;
                }
                return true;
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(root);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
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

    private void addSpecialty() {
        String specialtyName = editSpecialtyName.getText().toString();

        model.addSpecialty(key, specialtyName);

        specialtyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public int getLayout() {
        return R.layout.dialog_new_specialty;
    }

}
