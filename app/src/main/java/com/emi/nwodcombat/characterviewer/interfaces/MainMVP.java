package com.emi.nwodcombat.characterviewer.interfaces;

import com.emi.nwodcombat.model.realm.Character;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public interface MainMVP {
    interface RequiredViewOps {
        void setUpView(Character queriedCharacter);
    }

    interface PresenterOps {
        RealmResults<Character> queryCharacters();
    }

    interface RequiredPresenterOps {

    }

    interface ModelOps {
        Character getQueriedCharacter(long id);
        void updateCharacter(Character character);
//        RealmResults<Character> getList();
    }
}
