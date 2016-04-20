package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.adapters.PersonalityRealmAdapter;
import com.emi.nwodcombat.adapters.ViceRealmAdapter;
import com.emi.nwodcombat.adapters.VirtueRealmAdapter;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.POJOField;
import com.emi.nwodcombat.model.realm.PersonalityArchetype;
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
    private Character character;

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

    public CharacterViewerView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
    }

    public void setUpView(Character queriedCharacter) {
        this.character = queriedCharacter;

        RealmList<POJOField> fields = character.getPojoFields();

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

        txtCharacterVirtue.setText(character.getVirtues().get(0).getName());

    }

    @OnClick(R.id.txtCharacterNature)
    public void onNatureClicked() {
        txtCharacterNature.setVisibility(View.GONE);
        spinnerNature.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txtCharacterDemeanor)
    public void onDemeanorClicked() {
        txtCharacterDemeanor.setVisibility(View.GONE);
        spinnerNature.setVisibility(View.VISIBLE);
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

    public void setPersonalityTraitsSpinners(RealmResults<PersonalityArchetype> traits) {
        PersonalityRealmAdapter natures;
        PersonalityRealmAdapter demeanors;

        natures = new PersonalityRealmAdapter(getActivity(), traits, true);
        demeanors = new PersonalityRealmAdapter(getActivity(), traits, true);

        spinnerNature.setAdapter(natures);
        spinnerDemeanor.setAdapter(demeanors);

        spinnerNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PersonalityArchetype nature = ((PersonalityArchetype) spinnerDemeanor.getItemAtPosition(position));

                txtCharacterNature.setText(nature.getName());
                txtCharacterNature.setVisibility(View.VISIBLE);
                spinnerNature.setVisibility(View.GONE);
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

        spinnerVice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vice vice = ((Vice) spinnerVice.getItemAtPosition(position));

                txtCharacterVice.setText(vice.getName());
                txtCharacterVice.setVisibility(View.VISIBLE);
                spinnerVice.setVisibility(View.GONE);

                character.getVices().get(0).setId(vice.getId());
                character.getVices().get(0).setName(vice.getName());
                character.getVices().get(0).setDescription(vice.getDescription());
                character.getVices().get(0).setRegainOne(vice.getRegainOne());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        for (int i = 0; i < vices.size(); i++) {
            Vice vice = vices.get(i);
            if (vice.getName().equals(character.getVices().get(0).getName())) {
                spinnerVice.setSelection(i);
                break;
            }
        }
    }

    public void setUpVirtueSpinner(RealmResults<Virtue> virtues) {
        VirtueRealmAdapter adapter;

        adapter = new VirtueRealmAdapter(getActivity(), virtues, true);

        spinnerVirtue.setAdapter(adapter);

        spinnerVirtue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    Virtue virtue = ((Virtue) spinnerVirtue.getItemAtPosition(position));

                    txtCharacterVirtue.setText(virtue.getName());
                    txtCharacterVirtue.setVisibility(View.VISIBLE);
                    spinnerVirtue.setVisibility(View.GONE);

                    // TODO Research how to use Otto bus to change the virtue on the character
                    character.getVirtues().get(0).setId(virtue.getId());
                    character.getVirtues().get(0).setName(virtue.getName());
                    character.getVirtues().get(0).setDescription(virtue.getDescription());
                    character.getVirtues().get(0).setRegainAll(virtue.getRegainAll());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        for (int i = 0; i < virtues.size(); i++) {
            Virtue virtue = virtues.get(i);
            if (virtue.getName().equals(character.getVirtues().get(0).getName())) {
                spinnerVirtue.setSelection(i);
                break;
            }
        }
    }

    public void onPause() {
        bus.post(new UpdateCharacterEvent(character));
    }

    public static class UpdateCharacterEvent {
        public Character updatedCharacter;

        UpdateCharacterEvent(Character character) {
            this.updatedCharacter = character;
        }
    }
}
