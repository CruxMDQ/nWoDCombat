package com.emi.nwodcombat.tools;

import android.support.annotation.NonNull;

import com.emi.nwodcombat.model.realm.POJOField;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Crux on 4/17/2016.
 */
public class ArrayHelper {
    public static <T extends POJOField> String find(List<T> list,@NonNull String arg) {
        try {
            for (T field : list) {
                if (field.getKey().equals(arg)) {
                    return field.getValue();
                }
            }
            return null;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }
}
