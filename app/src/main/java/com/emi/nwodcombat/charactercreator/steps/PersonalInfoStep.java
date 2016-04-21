package com.emi.nwodcombat.charactercreator.steps;

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
import com.emi.nwodcombat.adapters.ViceRealmAdapter;
import com.emi.nwodcombat.adapters.VirtueRealmAdapter;
import com.emi.nwodcombat.charactercreator.dialogs.AddRecordDialog;
import com.emi.nwodcombat.charactercreator.interfaces.AfterCreatingRecordListener;
import com.emi.nwodcombat.model.pojos.PersonalityArchetypePojo;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.persistence.PersistenceLayer;
import com.emi.nwodcombat.persistence.RealmHelper;
import com.emi.nwodcombat.utils.Constants;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by Crux on 3/11/2016.
 */
public class PersonalInfoStep extends WizardStep implements AfterCreatingRecordListener {
    @Bind(R.id.editConcept) EditText editConcept;
    @Bind(R.id.editName) EditText editName;
    @Bind(R.id.editPlayer) EditText editPlayer;

    @Bind(R.id.spinnerDemeanor) Spinner spinnerDemeanor;
    @Bind(R.id.spinnerNature) Spinner spinnerNature;
    @Bind(R.id.spinnerVice) Spinner spinnerVice;
    @Bind(R.id.spinnerVirtue) Spinner spinnerVirtue;

    @Bind(R.id.btnAddDemeanor) Button btnAddDemeanor;
    @Bind(R.id.btnAddNature) Button btnAddNature;
    // TODO Implement code for adding vices and virtues
    @Bind(R.id.btnAddVice) Button btnAddVice;
    @Bind(R.id.btnAddVirtue) Button btnAddVirtue;

    private PersistenceLayer helper;

    private Long idDemeanor;
    private String demeanorName;

    private Long idNature;
    private String natureName;

    private long idVice;
    private String viceName;

    private Long idVirtue;
    private String virtueName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);

        ButterKnife.bind(this, view);

        helper = RealmHelper.getInstance(getActivity());

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
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);

        if (isVisible) {
            pagerMaster.checkStepIsComplete(false, this);
        }
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
                checkCompletionConditions();
            }
        };

        editConcept.addTextChangedListener(textWatcher);
        editName.addTextChangedListener(textWatcher);
        editPlayer.addTextChangedListener(textWatcher);

        if (Constants.MODE_TEST) {
            editName.setText(R.string.test_info_name);
            editConcept.setText(R.string.test_info_concept);
        }

        setUpDemeanorSpinner();
        setUpNatureSpinner();
        setUpViceSpinner();
        setUpVirtueSpinner();

        btnAddDemeanor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRecordDialog dialog = AddRecordDialog.newInstance(
                        PersonalityArchetypePojo.class,
                        getString(R.string.dialog_demeanor_new_title),
                        getString(R.string.dialog_demeanor_new_hint)
                );
                dialog.setListener(PersonalInfoStep.this);
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
                dialog.setListener(PersonalInfoStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });

    }

    @Override
    public void checkCompletionConditions() {
        pagerMaster.checkStepIsComplete(!hasEmptyValues(), this);
    }

    @Override
    public HashMap<String, Object> saveChoices() {
        HashMap<String, Object> output = new HashMap<>();

        output.put(Constants.CHARACTER_CONCEPT, editConcept.getText().toString());
        output.put(Constants.CHARACTER_NAME, editName.getText().toString());
        output.put(Constants.CHARACTER_PLAYER, editPlayer.getText().toString());
        output.put(Constants.CHARACTER_VICE, idVice);
        output.put(Constants.CHARACTER_VIRTUE, idVirtue);
        output.put(Constants.CHARACTER_DEMEANOR, idDemeanor);
        output.put(Constants.CHARACTER_NATURE, idNature);

        return output;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_personal_info);
    }

    @Override
    public int getLayout() {
        return R.layout.step_personal_info;
    }

    public boolean hasEmptyValues() {
        int selectedDemeanor = spinnerDemeanor.getSelectedItemPosition();
        return editConcept.getText().toString().equals("")
                || editName.getText().toString().equals("")
                || editPlayer.getText().toString().equals("")
                || selectedDemeanor == 0;
    }

    private void setUpDemeanorSpinner() {
        DemeanorsAdapter adapter;
        List<Demeanor> demeanors = helper.getList(Demeanor.class);

        adapter = new DemeanorsAdapter(getActivity(), (RealmResults<Demeanor>) demeanors, true);

        spinnerDemeanor.setAdapter(adapter);

        spinnerDemeanor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    Demeanor archetype = ((Demeanor) spinnerDemeanor
                        .getItemAtPosition(position));
                    idDemeanor = archetype.getId();
                    demeanorName = archetype.getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setUpNatureSpinner() {
        NaturesAdapter adapter;
        List<Nature> natures = helper.getList(Nature.class);

        adapter = new NaturesAdapter(getActivity(), (RealmResults<Nature>) natures, true);

        spinnerNature.setAdapter(adapter);

        spinnerNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    Nature archetype = ((Nature) spinnerNature
                        .getItemAtPosition(position));
                    idNature = archetype.getId();
                    natureName = archetype.getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setUpViceSpinner() {
        ViceRealmAdapter adapter;
        List<Vice> vices = helper.getList(Vice.class);

        adapter = new ViceRealmAdapter(getActivity(), (RealmResults<Vice>) vices, true);

        spinnerVice.setAdapter(adapter);

        spinnerVice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    Vice vice = ((Vice) spinnerVice.getItemAtPosition(position));
                    idVice = vice.getId();
                    viceName = vice.getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setUpVirtueSpinner() {
        VirtueRealmAdapter adapter;
        List<Virtue> virtues = helper.getList(Virtue.class);

        adapter = new VirtueRealmAdapter(getActivity(), (RealmResults<Virtue>) virtues, true);

        spinnerVirtue.setAdapter(adapter);

        spinnerVirtue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    Virtue virtue = ((Virtue) spinnerVirtue.getItemAtPosition(position));
                    idVirtue = virtue.getId();
                    virtueName = virtue.getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void afterCreatingRecord(Object record) {
        if (record instanceof PersonalityArchetypePojo) {
            helper.save(Nature.class, new Gson().toJson(record));
            setUpDemeanorSpinner();
            setUpNatureSpinner();
        }
    }
}
