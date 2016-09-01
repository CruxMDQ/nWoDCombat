package com.emi.nwodcombat.tools;

import android.util.Log;

import com.emi.nwodcombat.model.realm.Entry;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.List;

/**
 * Created by Crux on 4/17/2016.
 */
public class ArrayHelper {

    public static <T extends Entry> String findValue(List<T> list, final String key) {
        try {
            return findEntry(list, key).getValue();
        } catch (Exception e) {
            Log.e(ArrayHelper.class.toString(), e.getMessage());
            return null;
        }
    }

    public static <T extends Entry> Entry findEntry(List<T> list, final String key) {
        try {
            return Iterables.find(list, new Predicate<T>() {
                public boolean apply(T instance) {
                    return instance.getKey().equals(
                        key);
                }
            });
        } catch (Exception e) {
            Log.e(ArrayHelper.class.toString(), "" + e.getMessage());
            return null;
        }
    }

    // Source: http://stackoverflow.com/a/7143170
    public static boolean isIncreasing(int[] arr) {
        for(int i = 0 ; i < arr.length - 1; i++) { // finish at length - 1
            if (arr[i] > arr[i+1]) {
                return false; // found elements that are out of order - return false
            }
        }
        return true; // nothing out of order found - return true
    }

    public static boolean isIncreasingAndContiguous(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {

//            if (list.get(i) > list.get(i+1)) {
            if (list.get(i) != list.get(i+1) - 1) {
                return false; // found elements that are out of order - return false
            }
        }
        return true; // nothing out of order found - return true
    }

    // --Commented out by Inspection START (07/07/2016 01:13 PM):
//    public static <T extends Entry> Entry findEntry(List<T> list, final long id) {
//        return Iterables.find(list, new Predicate<T>() {
//            public boolean apply(T instance) {
//                return instance.getId() == id;
//            }
//        });
//    }
// --Commented out by Inspection STOP (07/07/2016 01:13 PM)
}
