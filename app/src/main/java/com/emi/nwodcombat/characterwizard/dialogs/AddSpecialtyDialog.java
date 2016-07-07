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
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.adapters.SpecialtyAdapter;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardModel;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.tools.BusProvider;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 03/06/2016.
 */
public class AddSpecialtyDialog extends DialogFragment {
    @Bind(R.id.editSpecialtyName) EditText editSpecialtyName;
    @Bind(R.id.rvDialogSpecialties) RecyclerView rvDialogSpecialties;
    @Bind(R.id.txtSpecError) TextView txtSpecError;

    private String title;
    private String key;

    private CharacterWizardModel model;

    private AlertDialog dialog;
    private SpecialtyAdapter specialtyAdapter;

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

        final RealmList<Entry> specialties = model.getSpecialties(key);

        specialtyAdapter = new SpecialtyAdapter(specialties, getActivity(), R.layout.row_specialty,
            BusProvider.getInstance());

        rvDialogSpecialties.setAdapter(specialtyAdapter);

        editSpecialtyName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String specialtyName = editSpecialtyName.getText().toString().trim();

                    // TODO Create override for cheating here
                    if (model.countSpecialties() < Constants.SKILL_SPECIALTIES_STARTING) {
                        for (Entry specialty : model.getSpecialties(key)) {
                            if (specialty.getValue().equalsIgnoreCase(specialtyName)) {
                                txtSpecError.setText(getActivity().getText(R.string.error_specialty_exists));
                                txtSpecError.setVisibility(View.VISIBLE);
                                return false;
                            }
                        }
                    } else {
                        txtSpecError.setText(R.string.error_specialty_limit_reached);
                        txtSpecError.setVisibility(View.VISIBLE);
                        return false;
                    }

                    txtSpecError.setVisibility(View.GONE);

                    addSpecialty(specialtyName);

                    performDialogClosing();

                    return false;
                }
                return true;
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(root);

        alertDialogBuilder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performDialogClosing();
                dismiss();
            }
        });

        alertDialogBuilder.setTitle(title);

        dialog = alertDialogBuilder.create();

//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positive.setEnabled(false);
//            }
//        });

        toggleRV();

        return dialog;
    }

    private void toggleRV() {
        if (specialtyAdapter.getItemCount() > 0) {
            rvDialogSpecialties.setVisibility(View.VISIBLE);
        } else {
            rvDialogSpecialties.setVisibility(View.GONE);
        }
    }

    private void performDialogClosing() {
        BusProvider.getInstance().post(new Events.SpecialtyDialogClosing(key));

        dismiss();
    }

    private void addSpecialty(String specialtyName) {
        model.addSpecialty(key, specialtyName);

        specialtyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private int getLayout() {
        return R.layout.dialog_new_specialty;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.register(this);
    }

    @Override
    public void onPause() {
        BusProvider.unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onSpecialtyTapped(Events.SpecialtyTapped event) {
        model.removeSpecialty(key, event.value);

        toggleRV();

        specialtyAdapter.notifyDataSetChanged();
    }
}
