package com.emi.nwodcombat.characterwizard.steps;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.adapters.DemeanorsAdapter;
import com.emi.nwodcombat.adapters.NaturesAdapter;
import com.emi.nwodcombat.adapters.VicesAdapter;
import com.emi.nwodcombat.adapters.VirtuesAdapter;
import com.emi.nwodcombat.charactercreator.dialogs.AddRecordDialog;
import com.emi.nwodcombat.charactercreator.interfaces.AfterCreatingRecordListener;
import com.emi.nwodcombat.characterwizard.mvp.CharacterWizardPresenter;
import com.emi.nwodcombat.model.pojos.PersonalityArchetypePojo;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.persistence.RealmHelper;
import com.emi.nwodcombat.utils.BusProvider;
import com.emi.nwodcombat.utils.Constants;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;

/**
 * Created by Crux on 3/11/2016.
 */
public class PersonalInfoFragment extends PagerFragment implements AfterCreatingRecordListener {
    private static final int NO_OPTION_SELECTED = -1;

    @Bind(R.id.editConcept) EditText editConcept;
    @Bind(R.id.editName) EditText editName;
    @Bind(R.id.editPlayer) EditText editPlayer;

    @Bind(R.id.spinnerDemeanor) Spinner spinnerDemeanor;
    @Bind(R.id.spinnerNature) Spinner spinnerNature;
    @Bind(R.id.spinnerVice) Spinner spinnerVice;
    @Bind(R.id.spinnerVirtue) Spinner spinnerVirtue;

    @Bind(R.id.btnAddDemeanor) Button btnAddDemeanor;
    @Bind(R.id.btnAddNature) Button btnAddNature;
    @Bind(R.id.btnAddVice) Button btnAddVice;
    @Bind(R.id.btnAddVirtue) Button btnAddVirtue;

    private RealmHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);

        ButterKnife.bind(this, view);

        helper = RealmHelper.getInstance(getActivity());

        bus = BusProvider.getInstance();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpUI();
    }

    @Override
    protected void setUpUI() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                bus.post(
                    new CharacterWizardPresenter.StepCompletionCheckEvent(checkCompletionConditions()));
            }
        };

        editConcept.addTextChangedListener(textWatcher);
        editName.addTextChangedListener(textWatcher);
        editPlayer.addTextChangedListener(textWatcher);

        if (Constants.MODE_TEST) {
            editName.setText(R.string.test_info_name);
            editConcept.setText(R.string.test_info_concept);
        }

        btnAddDemeanor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRecordDialog dialog = AddRecordDialog.newInstance(
                    PersonalityArchetypePojo.class,
                    getString(R.string.dialog_demeanor_new_title),
                    getString(R.string.dialog_demeanor_new_hint)
                );
                dialog.setListener(PersonalInfoFragment.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });

        btnAddNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRecordDialog dialog = AddRecordDialog.newInstance(
                    PersonalityArchetypePojo.class,
                    getString(R.string.dialog_nature_new_title),
                    getString(R.string.dialog_nature_new_hint)
                );
                dialog.setListener(PersonalInfoFragment.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });

        setUpSpinner(spinnerDemeanor,
            new DemeanorsAdapter(getActivity(), helper.getList(Demeanor.class), true),
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Demeanor demeanor = ((Demeanor) parent.getItemAtPosition(position));

                    // TODO Write call to model to save value

                    bus.post(
                        new CharacterWizardPresenter.StepCompletionCheckEvent(
                            checkCompletionConditions()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        setUpSpinner(spinnerNature, 
            new NaturesAdapter(getActivity(), helper.getList(Nature.class), true),
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Nature nature = ((Nature) parent.getItemAtPosition(position));

                    // TODO Write call to model to save value

                    bus.post(
                        new CharacterWizardPresenter.StepCompletionCheckEvent(
                            checkCompletionConditions()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

        setUpSpinner(spinnerVice, new VicesAdapter(getActivity(), helper.getList(Vice.class), true),
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Vice vice = ((Vice) parent.getItemAtPosition(position));

                    // TODO Write call to model to save value

                    bus.post(
                        new CharacterWizardPresenter.StepCompletionCheckEvent(
                            checkCompletionConditions()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        setUpSpinner(spinnerVirtue,
            new VirtuesAdapter(getActivity(), helper.getList(Virtue.class), true),
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Virtue virtue = ((Virtue) parent.getItemAtPosition(position));

                    // TODO Write call to model to save value

                    bus.post(
                        new CharacterWizardPresenter.StepCompletionCheckEvent(
                            checkCompletionConditions()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
    }

    private void setUpSpinner(Spinner spinner, RealmBaseAdapter adapter, AdapterView.OnItemSelectedListener listener) {
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listener);
    }

    @Override
    public boolean checkCompletionConditions() {
        int selectedDemeanor = spinnerDemeanor.getSelectedItemPosition();
        int selectedNature = spinnerNature.getSelectedItemPosition();
        int selectedVice = spinnerVice.getSelectedItemPosition();
        int selectedVirtue = spinnerVirtue.getSelectedItemPosition();

        return selectedDemeanor != NO_OPTION_SELECTED &&
            selectedNature != NO_OPTION_SELECTED  &&
            selectedVice != NO_OPTION_SELECTED  &&
            selectedVirtue != NO_OPTION_SELECTED  &&
            !editConcept.getText().toString().equals("") &&
            !editName.getText().toString().equals("") &&
            !editPlayer.getText().toString().equals("");
    }

    @Override
    public String getToolbarTitle() {
        return Constants.TITLE_STEP_PERSONAL_INFO;
    }

    @Override
    public int getLayout() {
        return R.layout.step_personal_info;
    }

    @Override
    public void afterCreatingRecord(Object record) {
        if (record instanceof PersonalityArchetypePojo) {
            helper.save(Nature.class, new Gson().toJson(record));
            // TODO Recode spinner item addition
//            setUpDemeanorSpinner();
//            setUpNatureSpinner();
        }
    }
}
