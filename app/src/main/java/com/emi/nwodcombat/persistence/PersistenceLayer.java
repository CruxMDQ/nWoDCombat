package com.emi.nwodcombat.persistence;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 07/04/2016.
 */
public interface PersistenceLayer {
    <T extends RealmObject> long save(T item);

    <T extends RealmObject> long save(Class<T> klass, String json);

    <T extends RealmObject> long save(Class<T> klass, List<String> jsonObjects);

    <T extends RealmObject> RealmResults<T> getList(Class<T> klass);

    <T extends RealmObject> void delete(Class<T> clazz, long id);

    <T extends RealmObject> T get(long id);

    <T extends RealmObject> T get(Class<T> klass, long id);

    int getCount(Class className);

    long getLastId(Class className);

    void updateEntry(Long characterId, Long entryId, int value);
}
