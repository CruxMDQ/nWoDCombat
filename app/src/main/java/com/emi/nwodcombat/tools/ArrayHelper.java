package com.emi.nwodcombat.tools;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.model.realm.POJOField;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Crux on 4/17/2016.
 */
public class ArrayHelper {
    public static Object find(List list, String arg) {
        try {
            for (Object field : list) {
                if (field instanceof POJOField) {
                    if (((POJOField) field).getKey().equals(arg)) {
                        return ((POJOField) field).getValue();
                    }
                }
            }
            return null;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }
}
