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
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Bus;

import butterknife.BindView;

/**
 * Created by emiliano.desantis on 18/05/2016.
 */
@SuppressWarnings("WeakerAccess")
public class PersonalInfoView extends FragmentView {
    private final Bus bus;

    @BindView(R.id.editConcept) EditText editConcept;
    @BindView(R.id.editName) EditText editName;
    @BindView(R.id.editPlayer) EditText editPlayer;

    @BindView(R.id.spinnerDemeanor) Spinner spinnerDemeanor;
    @BindView(R.id.spinnerNature) Spinner spinnerNature;
    @BindView(R.id.spinnerVice) Spinner spinnerVice;
    @BindView(R.id.spinnerVirtue) Spinner spinnerVirtue;

    public PersonalInfoView(Fragment fragment, Bus instance) {
        super(fragment);
        this.bus = instance;
//        ButterKnife.bind(this, fragment.getView());
        setUpTextWatcher();
    }

    private void setUpTextWatcher() {
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

    public void setName(String name)  {
        editName.setText(name);
    }

    public void setConcept(String concept)  {
        editConcept.setText(concept);
    }

    public void setPlayer(String player)  {
        editPlayer.setText(player);
    }

    // TODO model is the one who knows if steps are complete
    private boolean performStepCompletionCycle() {
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
        //TODO this looks very odd to me, why three post instead of just one?
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
                bus.post(new Events.DemeanorChanged(position));

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
                bus.post(new Events.NatureChanged(position));

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
                bus.post(new Events.ViceChanged(position));

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
                bus.post(new Events.VirtueChanged(position));

                bus.post(new Events.StepCompletionChecked(
                        performStepCompletionCycle()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}
