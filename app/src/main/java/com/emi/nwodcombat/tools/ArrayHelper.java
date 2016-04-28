package com.emi.nwodcombat.tools;

import com.emi.nwodcombat.model.realm.Entry;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.List;

/**
 * Created by Crux on 4/17/2016.
 */
public class ArrayHelper {

    public static <T extends Entry> String find(List<T> list, final String key) {
        T result = Iterables.find(list, new Predicate<T>() {
            public boolean apply(T instance) {
                return instance.getKey().equals(
                    key);
            }
        });

        return result.getValue();
    }
}
