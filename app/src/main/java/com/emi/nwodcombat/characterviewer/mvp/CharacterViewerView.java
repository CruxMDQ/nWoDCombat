package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.adapters.NaturesAdapter;
import com.emi.nwodcombat.adapters.ViceRealmAdapter;
import com.emi.nwodcombat.adapters.VirtueRealmAdapter;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.POJOField;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.utils.Constants;
import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerView extends FragmentView {

    private final Bus bus;
    private Character queriedCharacter;
    private Character updatedCharacter = new Character();

    @Bind(R.id.txtCharacterName) TextView txtCharacterName;
    @Bind(R.id.txtCharacterConcept) TextView txtCharacterConcept;
    @Bind(R.id.txtCharacterPlayer) TextView txtCharacterPlayer;

    @Bind(R.id.spinnerVirtue) Spinner spinnerVirtue;
    @Bind(R.id.spinnerVice) Spinner spinnerVice;
    @Bind(R.id.spinnerNature) Spinner spinnerNature;
    @Bind(R.id.spinnerDemeanor) Spinner spinnerDemeanor;

    @Bind(R.id.txtCharacterVirtue) TextView txtCharacterVirtue;
    @Bind(R.id.txtCharacterVice) TextView txtCharacterVice;
    @Bind(R.id.txtCharacterNature) TextView txtCharacterNature;
    @Bind(R.id.txtCharacterDemeanor) TextView txtCharacterDemeanor;

    private boolean hasChanges = false;

    public CharacterViewerView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
    }

    public void setUpView(Character queriedCharacter) {
        this.queriedCharacter = queriedCharacter;
        updatedCharacter.setId(queriedCharacter.getId());

        RealmList<POJOField> fields = this.queriedCharacter.getPojoFields();

        for (POJOField field : fields) {
            switch (field.getKey()) {
                case Constants.CHARACTER_NAME: {
                    txtCharacterName.setText(field.getValue());
                    break;
                }
                case Constants.CHARACTER_CONCEPT: {
                    txtCharacterConcept.setText(field.getValue());
                    break;
                }
                case Constants.CHARACTER_PLAYER: {
                    txtCharacterPlayer.setText(field.getValue());
                    break;
                }
            }
        }

        txtCharacterVirtue.setText(this.queriedCharacter.getVirtues().first().getName());
        txtCharacterVice.setText(this.queriedCharacter.getVices().first().getName());
        txtCharacterNature.setText(this.queriedCharacter.getNatures().first().getName());
        txtCharacterDemeanor.setText(this.queriedCharacter.getDemeanors().first().getName());

    }

    @OnClick(R.id.txtCharacterNature)
    public void onNatureClicked() {
        txtCharacterNature.setVisibility(View.GONE);
        spinnerNature.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txtCharacterDemeanor)
    public void onDemeanorClicked() {
        txtCharacterDemeanor.setVisibility(View.GONE);
        spinnerDemeanor.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txtCharacterVice)
    public void onViceClicked() {
        txtCharacterVice.setVisibility(View.GONE);
        spinnerVice.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txtCharacterVirtue)
    public void onVirtueClicked() {
        txtCharacterVirtue.setVisibility(View.GONE);
        spinnerVirtue.setVisibility(View.VISIBLE);
    }

    public void setUpDemeanorsSpinner(RealmResults<Demeanor> traits) {
        NaturesAdapter demeanors;

        demeanors = new NaturesAdapter(getActivity(), traits, true);

        spinnerDemeanor.setAdapter(demeanors);

        for (int i = 0; i < traits.size(); i++) {
            Demeanor demeanor = traits.get(i);
            if (demeanor.getName().equals(queriedCharacter.getDemeanors().get(0).getName())) {
                spinnerDemeanor.setSelection(i);
                break;
            }
        }

        spinnerDemeanor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Demeanor demeanor = ((Demeanor) spinnerDemeanor.getItemAtPosition(position));

                txtCharacterDemeanor.setText(demeanor.getName());
                txtCharacterDemeanor.setVisibility(View.VISIBLE);
                spinnerDemeanor.setVisibility(View.GONE);

                updatedCharacter.getDemeanors().clear();
                updatedCharacter.getDemeanors().add(demeanor);

                hasChanges = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void setUpNaturesSpinner(RealmResults<Nature> traits) {
        NaturesAdapter natures;

        natures = new NaturesAdapter(getActivity(), traits, true);

        spinnerNature.setAdapter(natures);

        for (int i = 0; i < traits.size(); i++) {
            Nature nature = traits.get(i);
            if (nature.getName().equals(queriedCharacter.getNatures().get(0).getName())) {
                spinnerNature.setSelection(i);
                break;
            }
        }

        spinnerNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Nature nature = ((Nature) spinnerNature
                    .getItemAtPosition(position));

                txtCharacterNature.setText(nature.getName());
                txtCharacterNature.setVisibility(View.VISIBLE);
                spinnerNature.setVisibility(View.GONE);

                updatedCharacter.getNatures().clear();
                updatedCharacter.getNatures().add(nature);

                hasChanges = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setUpViceSpinner(RealmResults<Vice> vices) {
        ViceRealmAdapter adapter;

        adapter = new ViceRealmAdapter(getActivity(), vices, true);

        spinnerVice.setAdapter(adapter);

        for (int i = 0; i < vices.size(); i++) {
            Vice vice = vices.get(i);
            if (vice.getName().equals(queriedCharacter.getVices().get(0).getName())) {
                spinnerVice.setSelection(i);
                break;
            }
        }

        spinnerVice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vice vice = ((Vice) spinnerVice.getItemAtPosition(position));

                txtCharacterVice.setText(vice.getName());
                txtCharacterVice.setVisibility(View.VISIBLE);
                spinnerVice.setVisibility(View.GONE);
                
                updatedCharacter.getVices().clear();
                updatedCharacter.getVices().add(vice);

                hasChanges = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setUpVirtueSpinner(RealmResults<Virtue> virtues) {
        VirtueRealmAdapter adapter;

        adapter = new VirtueRealmAdapter(getActivity(), virtues, true);

        spinnerVirtue.setAdapter(adapter);

        for (int i = 0; i < virtues.size(); i++) {
            Virtue virtue = virtues.get(i);
            if (virtue.getName().equals(queriedCharacter.getVirtues().get(0).getName())) {
                spinnerVirtue.setSelection(i);
                break;
            }
        }

        spinnerVirtue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    Virtue virtue = ((Virtue) spinnerVirtue.getItemAtPosition(position));

                    txtCharacterVirtue.setText(virtue.getName());
                    txtCharacterVirtue.setVisibility(View.VISIBLE);
                    spinnerVirtue.setVisibility(View.GONE);

                    updatedCharacter.getVirtues().clear();
                    updatedCharacter.getVirtues().add(virtue);

                    hasChanges = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public void onPause() {
        if (hasChanges) {
            bus.post(new UpdateCharacterEvent(updatedCharacter));
        }
    }

    public static class UpdateCharacterEvent {
        public Character characterToUpdate;

        UpdateCharacterEvent(Character character) {
            this.characterToUpdate = character;
        }
    }
}
