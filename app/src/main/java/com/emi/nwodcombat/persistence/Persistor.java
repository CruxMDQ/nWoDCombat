package com.emi.nwodcombat.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emiliano.desantis on 07/04/2016.
 */
public interface Persistor<T> {
    long save(T item);

    long save(Class klass, String json);

    long save(Class klass, ArrayList<String> jsonObjects);

    List<T> getList();

    void delete(T item);

    T get(long id);

    void update(T item);

    int getCount(Class className);

    long getLastId(Class className);
}
