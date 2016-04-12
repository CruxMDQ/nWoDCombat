package com.emi.nwodcombat.characterlist.interfaces;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 31/03/2016.
 */
public interface MainMVP {

    // Presenter -> View
    interface RequiredViewOps {
        void showSnackBar(String msg);
        void showAlert(String msg);
    }

    // View -> Presenter
    interface PresenterOps {
        RealmResults<com.emi.nwodcombat.model.realm.Character> queryCharacters();
        void newCharacter(com.emi.nwodcombat.model.realm.Character character);
        void removeCharacter(long idCharacter);
        void onFabPressed();

        // Any other ops to be called from View
    }

    // Model -> Presenter
    interface RequiredPresenterOps {
        void onCharacterAdded();
        void onCharacterRemoved();
        void onError(String message);

        // Any other returning operation Model -> Presenter
    }

    // Presenter -> Model
    interface ModelOps {
        void insertCharacter(com.emi.nwodcombat.model.realm.Character character);
        void removeCharacter(long id);
        void onDestroy();

//        Loader<List<com.emi.nwodcombat.model.realm.Character>> getCharactersLoader();
        RealmResults<com.emi.nwodcombat.model.realm.Character> getList();

        // Any other data operations
    }
}
