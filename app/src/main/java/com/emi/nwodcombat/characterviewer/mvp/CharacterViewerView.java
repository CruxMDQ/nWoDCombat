package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
import android.widget.TextView;

import com.emi.nwodcombat.utils.Constants;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.POJOField;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerView extends FragmentView {

    private Character character;


    @Bind(R.id.txtCharacterName) TextView txtCharacterName;
    @Bind(R.id.txtCharacterConcept) TextView txtCharacterConcept;
    @Bind(R.id.txtCharacterPlayer) TextView txtCharacterPlayer;

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
    }
}
