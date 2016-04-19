package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.adapters.VirtueRealmAdapter;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.POJOField;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerView extends FragmentView {

    private Character character;

    @Bind(R.id.txtCharacterName) TextView txtCharacterName;
    @Bind(R.id.txtCharacterConcept) TextView txtCharacterConcept;
    @Bind(R.id.txtCharacterPlayer) TextView txtCharacterPlayer;

    @Bind(R.id.spinnerVirtue) Spinner spinnerVirtue;
    @Bind(R.id.txtCharacterVirtue) TextView txtCharacterVirtue;

    public CharacterViewerView(Fragment fragment) {
        super(fragment);
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

    @OnClick(R.id.txtCharacterVirtue)
    public void onVirtueClicked() {
        txtCharacterVirtue.setVisibility(View.GONE);
        spinnerVirtue.setVisibility(View.VISIBLE);
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
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        for (int i = 0; i < virtues.size(); i++) {
            Virtue virtue = virtues.get(i);
            if (virtue.getName().equals(character.getVirtues().get(0).getName())) {
                spinnerVirtue.setSelection(i);
                break;
            }
        }
    }
}
