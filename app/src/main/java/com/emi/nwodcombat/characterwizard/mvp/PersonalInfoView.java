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
        editConcept.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                bus.post(new Events.StepCompletionChecked(performStepCompletionCycle()));
            }
        });

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                bus.post(new Events.StepCompletionChecked(performStepCompletionCycle()));
            }
        });

        editPlayer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                bus.post(new Events.StepCompletionChecked(performStepCompletionCycle()));
            }
        });
    }

    public void setupTestData() {
        if (Constants.MODE_TEST) {
            editName.setText(R.string.test_info_name);
            editConcept.setText(R.string.test_info_concept);
            editPlayer.setText(R.string.default_step_personal_info_player);
        }
    }

    public boolean performStepCompletionCycle() {
        boolean complete = checkCompletionConditions();

        if (complete) {
            saveStepValues();
        }

        return complete;
    }

    private boolean checkCompletionConditions() {
        return spinnerDemeanor.getSelectedItemPosition() != Constants.NO_OPTION_SELECTED &&
                    spinnerNature.getSelectedItemPosition() != Constants.NO_OPTION_SELECTED &&
                    spinnerVice.getSelectedItemPosition() != Constants.NO_OPTION_SELECTED &&
                    spinnerVirtue.getSelectedItemPosition() != Constants.NO_OPTION_SELECTED &&
                    !editConcept.getText().toString().equals("") &&
                    !editName.getText().toString().equals("") &&
                    !editPlayer.getText().toString().equals("");
    }

    private void saveStepValues() {
        bus.post(new Events.TextEntryChanged(Constants.CHARACTER_CONCEPT,
            Constants.FIELD_TYPE_STRING, editConcept.getText().toString()));

        bus.post(new Events.TextEntryChanged(Constants.CHARACTER_NAME,
            Constants.FIELD_TYPE_STRING, editName.getText().toString()));

        bus.post(new Events.TextEntryChanged(Constants.CHARACTER_PLAYER,
            Constants.FIELD_TYPE_STRING, editPlayer.getText().toString()));

//        bus.post(new Events.DemeanorTraitChanged(spinnerDemeanor.getSelectedItemPosition()));

//        bus.post(new Events.NatureTraitChanged(spinnerNature.getSelectedItemPosition()));

//        bus.post(new Events.ViceTraitChanged(spinnerVice.getSelectedItemPosition()));

//        bus.post(new Events.VirtueTraitChanged(spinnerVirtue.getSelectedItemPosition()));
    }

    public void setDemeanorsSpinnerAdapter(DemeanorsAdapter demeanors) {
        spinnerDemeanor.setAdapter(demeanors);

        spinnerDemeanor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bus.post(new Events.DemeanorTraitChanged(position));

                bus.post(new Events.StepCompletionChecked(
                        performStepCompletionCycle()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    public void setNaturesSpinnerAdapter(NaturesAdapter natures) {
        spinnerNature.setAdapter(natures);

        spinnerNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bus.post(new Events.NatureTraitChanged(position));

                bus.post(new Events.StepCompletionChecked(
                        performStepCompletionCycle()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public void setVicesSpinnerAdapter(VicesAdapter vices) {
        spinnerVice.setAdapter(vices);

        spinnerVice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bus.post(new Events.ViceTraitChanged(position));

                bus.post(new Events.StepCompletionChecked(
                        performStepCompletionCycle()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public void setVirtuesSpinnerAdapter(VirtuesAdapter virtues) {
        spinnerVirtue.setAdapter(virtues);

        spinnerVirtue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bus.post(new Events.VirtueTraitChanged(position));

                bus.post(new Events.StepCompletionChecked(
                        performStepCompletionCycle()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}
