package com.emi.nwodcombat.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emiliano.desantis on 07/04/2016.
 */
public interface PersistenceLayer {
    long save(Object item);

    long save(Class klass, String json);

    long save(Class klass, ArrayList<String> jsonObjects);

    List<Object> getList();

    List getList(Class klass);

    void delete(Object item);

    Object get(long id);

    Object get(Class klass, long id);

    void update(Object item);

    int getCount(Class className);

    long getLastId(Class className);
}
