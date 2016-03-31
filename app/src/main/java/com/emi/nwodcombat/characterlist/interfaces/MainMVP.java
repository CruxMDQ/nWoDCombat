package com.emi.nwodcombat.characterlist.interfaces;

import com.emi.nwodcombat.model.db.Character;

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
        void newCharacter(String characterJson);
        void removeCharacter(long idCharacter);

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
        void insertCharacter(Character character);
        void removeCharacter(long character);
        void onDestroy();


        // Any other data operations
    }
}