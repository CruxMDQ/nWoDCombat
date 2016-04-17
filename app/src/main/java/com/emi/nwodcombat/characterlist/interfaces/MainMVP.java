package com.emi.nwodcombat.characterlist.interfaces;

import com.emi.nwodcombat.model.realm.Character;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 31/03/2016.
 */
public interface MainMVP {

    // Presenter -> View
    interface RequiredViewOps {
        void showSnackBar(String msg);
        void showAlert(String msg);
        void setUpRV(RealmResults<Character> characters);
        void updateRV(RealmResults<Character> characters);
    }

    // View -> Presenter
    interface PresenterOps {
        RealmResults<Character> queryCharacters();
        void newCharacter(Character character);
        void removeCharacter(long idCharacter);
        void onFabPressed();

        // Any other ops to be called from View
    }

    // Model -> Presenter
    interface RequiredPresenterOps {
        void onCharacterAdded();
        void onCharacterRemoved();
        void onError(String message);
        void setUpRV();
        void updateRV();

        // Any other returning operation Model -> Presenter
    }

    // Presenter -> Model
    interface ModelOps {
        void insertCharacter(Character character);
        void removeCharacter(long id);
        void onDestroy();

//        Loader<List<com.emi.nwodcombat.model.realm.Character>> getCharactersLoader();
        RealmResults<Character> getList();

        // Any other data operations
    }
}
