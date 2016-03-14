package com.emi.nwodcombat.charactercreator.steps;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.NothingSelectedSpinnerAdapter;
import com.emi.nwodcombat.greendao.controllers.BaseController;
import com.emi.nwodcombat.greendao.controllers.ViceController;
import com.emi.nwodcombat.greendao.controllers.VirtueController;
import com.emi.nwodcombat.model.db.Vice;
import com.emi.nwodcombat.model.db.Virtue;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Crux on 3/11/2016.
 */
public class PersonalInfoStep extends WizardStep {
    @Bind(R.id.editConcept) EditText editConcept;
    @Bind(R.id.editName) EditText editName;
    @Bind(R.id.editPlayer) EditText editPlayer;

    @Bind(R.id.spinnerVice) Spinner spinnerVice;
    @Bind(R.id.spinnerVirtue) Spinner spinnerVirtue;

    @Bind(R.id.btnAddVice) Button btnAddVice;
    @Bind(R.id.btnAddVirtue) Button btnAddVirtue;

    private long idVice;
    private String viceName;
    private ViceController viceController;

    private Long idVirtue;
    private String virtueName;
    private VirtueController virtueController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);

        viceController = ViceController.getInstance(getContext());
        virtueController = VirtueController.getInstance(getContext());

        ButterKnife.bind(this, view);

        return view;
    }

    @Override public void onDestroyView() {
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
        
        setUpViceSpinner();
        setUpVirtueSpinner();
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
        return editConcept.getText().toString().equals("")
                || editName.getText().toString().equals("")
                || editPlayer.getText().toString().equals("");
    }

    private void setUpViceSpinner() {
        NothingSelectedSpinnerAdapter adapter = setUpViceAdapter();

        spinnerVice.setAdapter(adapter);

        spinnerVice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    Vice vice = ((Vice) spinnerVice.getItemAtPosition(position));
                    idVice = vice.getIdVice();
                    viceName = vice.getName();
                }
            }

            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private NothingSelectedSpinnerAdapter setUpViceAdapter() {
        ArrayAdapter<Vice> viceArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, viceController.getList());

        viceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return new NothingSelectedSpinnerAdapter(
            viceArrayAdapter,
            R.layout.spinner_nothing_selected,
            getContext()
        );
    }

    private void setUpVirtueSpinner() {
        NothingSelectedSpinnerAdapter adapter = setUpVirtueAdapter();

        spinnerVirtue.setAdapter(adapter);

        spinnerVirtue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    Virtue virtue = ((Virtue) spinnerVirtue.getItemAtPosition(position));
                    idVirtue = virtue.getIdVirtue();
                    virtueName = virtue.getName();
                }
            }

            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private NothingSelectedSpinnerAdapter setUpVirtueAdapter() {
        ArrayAdapter<Virtue> viceArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, virtueController.getList());

        viceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return new NothingSelectedSpinnerAdapter(
            viceArrayAdapter,
            R.layout.spinner_nothing_selected,
            getContext()
        );
    }
}
