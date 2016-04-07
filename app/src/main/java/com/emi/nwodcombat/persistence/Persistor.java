package com.emi.nwodcombat.persistence;

import java.util.List;

/**
 * Created by emiliano.desantis on 07/04/2016.
 */
public interface Persistor<T> {
    long save(T item);

    long save(Class className, String json);

    List<T> getList();

    void delete(T item);

    T get(long id);

    void update(T item);

    int getCount(Class className);
}
