package com.emi.nwodcombat.tools;

import com.emi.nwodcombat.model.realm.Entry;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.List;

/**
 * Created by Crux on 4/17/2016.
 */
public class ArrayHelper {

    public static <T extends Entry> String findValue(List<T> list, final String key) {
        return findEntry(list, key).getValue();
    }

    public static <T extends Entry> Entry findEntry(List<T> list, final String key) {
        return Iterables.find(list, new Predicate<T>() {
            public boolean apply(T instance) {
                return instance.getKey().equals(
                    key);
            }
        });
    }

    public static <T extends Entry> Entry findEntry(List<T> list, final long id) {
        return Iterables.find(list, new Predicate<T>() {
            public boolean apply(T instance) {
                return instance.getId() == id;
            }
        });
    }
}
