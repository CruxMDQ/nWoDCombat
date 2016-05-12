package com.emi.nwodcombat.characterlist.mvp;

import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.persistence.RealmHelper;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 29/03/2016.
 */
public class CharacterListModel {

    // Presenter reference
    private RealmHelper helper;

    public CharacterListModel(RealmHelper helper) {
        this.helper = helper;
    }

    // Todos los parametros se los tenes que pasar a este metodo y que iternamente haga
    // la transformacion a  realm, entonces asi te queda encapsulado con datos primitivos.
    // PD: Cada vez me gusta menos Realm
    public void insertCharacter(String name) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Character c = realm.createObject(Character.class);
        c.setId(1L);
        realm.commitTransaction();
    }

    public void insertCharacter(Character character) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(character);
        realm.commitTransaction();
    }

    public void removeCharacter(long id) {
        helper.delete(Character.class, id);
    }

    public RealmResults<Character> getList() {
        return helper.getList(Character.class);
    }
}
