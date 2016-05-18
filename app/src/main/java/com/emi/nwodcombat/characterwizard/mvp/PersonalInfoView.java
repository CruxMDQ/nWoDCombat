package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.adapters.DemeanorsAdapter;
import com.emi.nwodcombat.adapters.NaturesAdapter;
import com.emi.nwodcombat.adapters.VicesAdapter;
import com.emi.nwodcombat.adapters.VirtuesAdapter;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.utils.Events;
import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 18/05/2016.
 */
public class PersonalInfoView extends FragmentView {
    private Bus bus;

    @Bind(R.id.editConcept) EditText editConcept;
    @Bind(R.id.editName) EditText editName;
    @Bind(R.id.editPlayer) EditText editPlayer;

    @Bind(R.id.spinnerDemeanor) Spinner spinnerDemeanor;
    @Bind(R.id.spinnerNature) Spinner spinnerNature;
    @Bind(R.id.spinnerVice) Spinner spinnerVice;
    @Bind(R.id.spinnerVirtue) Spinner spinnerVirtue;

    public PersonalInfoView(Fragment fragment, Bus instance) {
        super(fragment);
        this.bus = instance;
        ButterKnife.bind(this, fragment.getView());
    }

    public void setUpTextWatcher() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                bus.post(
                    new Events.StepCompletionChecked(checkCompletionConditions()));
            }
        };

        editConcept.addTextChangedListener(textWatcher);
        editName.addTextChangedListener(textWatcher);
        editPlayer.addTextChangedListener(textWatcher);
    }

    public void setupTestData() {
        if (Constants.MODE_TEST) {
            editName.setText(R.string.test_info_name);
            editConcept.setText(R.string.test_info_concept);
        }
    }

    public boolean checkCompletionConditions() {
        return spinnerDemeanor.getSelectedItemPosition() != Constants.NO_OPTION_SELECTED &&
            spinnerNature.getSelectedItemPosition() != Constants.NO_OPTION_SELECTED  &&
            spinnerVice.getSelectedItemPosition() != Constants.NO_OPTION_SELECTED  &&
            spinnerVirtue.getSelectedItemPosition() != Constants.NO_OPTION_SELECTED &&
            !editConcept.getText().toString().equals("") &&
            !editName.getText().toString().equals("") &&
            !editPlayer.getText().toString().equals("");
    }

    public void setDemeanorsSpinnerAdapter(DemeanorsAdapter demeanors) {
        spinnerDemeanor.setAdapter(demeanors);

        // Vanilla listener setting - could have been managed as a parameter, except for one of one
        // of the variables requiring that it be final, don't recall which now
        spinnerDemeanor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Demeanor demeanor = ((Demeanor) parent.getItemAtPosition(position));

                    // TODO Write call to model to save value

                    bus.post(
                        new Events.StepCompletionChecked(
                            checkCompletionConditions()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void setNaturesSpinnerAdapter(NaturesAdapter natures) {
        spinnerNature.setAdapter(natures);

        spinnerNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Nature nature = ((Nature) parent.getItemAtPosition(position));

                    // TODO Write call to model to save value

                    bus.post(
                        new Events.StepCompletionChecked(
                            checkCompletionConditions()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setVicesSpinnerAdapter(VicesAdapter vices) {
        spinnerVice.setAdapter(vices);

        spinnerVice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Vice vice = ((Vice) parent.getItemAtPosition(position));

                    // TODO Write call to model to save value

                    bus.post(
                        new Events.StepCompletionChecked(
                            checkCompletionConditions()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setVirtuesSpinnerAdapter(VirtuesAdapter virtues) {
        spinnerVirtue.setAdapter(virtues);

        spinnerVirtue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                    Virtue virtue = ((Virtue) parent.getItemAtPosition(position));
//
//                    // TODO Write call to model to save value
//
//                    bus.post(
//                        new Events.StepCompletionChecked(
//                            checkCompletionConditions()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
