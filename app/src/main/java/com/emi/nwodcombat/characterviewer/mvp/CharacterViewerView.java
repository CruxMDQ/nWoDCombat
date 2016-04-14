package com.emi.nwodcombat.characterviewer.mvp;

import android.app.Fragment;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.FragmentView;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterviewer.interfaces.MainMVP;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.POJOField;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerView extends FragmentView implements MainMVP.RequiredViewOps {

    private Character character;

    private MainMVP.PresenterOps callback;

    @Bind(R.id.txtCharacterName) TextView txtCharacterName;
    @Bind(R.id.txtCharacterConcept) TextView txtCharacterConcept;
    @Bind(R.id.txtCharacterPlayer) TextView txtCharacterPlayer;

    public CharacterViewerView(Fragment fragment) {
        super(fragment);
        ButterKnife.bind(this, fragment.getView());
    }

    @Override
    public void setUpView(Character queriedCharacter) {
        this.character = queriedCharacter;

        RealmList<POJOField> fields = character.getPojoFields();

        for (POJOField field : fields) {
            switch (field.getKey()) {
                case Constants.CHARACTER_NAME: {
                    txtCharacterName.setText(field.getValue());
                    break;
                }
            }
        }
//        txtCharacterName.setText(character.getPojoFields().g);

//        txtCharacterName.setText(character.getName());
//        txtCharacterConcept.setText(character.getConcept());
//        txtCharacterPlayer.setText(character.getPlayer());
    }

    public void setCallback(MainMVP.PresenterOps callback) {
        this.callback = callback;
    }

    public MainMVP.PresenterOps getCallback() {
        return callback;
    }
}
