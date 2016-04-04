package com.emi.nwodcombat.charactercreator;

import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by Emi on 3/4/16.
 */
public class CharacterCreatorHelper {
    private static CharacterCreatorHelper instance;

    private static HashMap<String, Object> values;

    private CharacterCreatorHelper() {
        values = new HashMap<>();
    }

    public static CharacterCreatorHelper getInstance () {
        if (instance == null) {
            instance = new CharacterCreatorHelper();
        }
        return instance;
    }


    public void putAll(@Nullable HashMap<String, Object> newValues) {
        if (newValues != null) {
            values.putAll(newValues);
        }
    }

    public CharacterCreatorHelper putInt(String key, Integer value) {
        values.put(key, value);

        return getInstance();
    }

    public CharacterCreatorHelper remove(String key) {
        values.remove(key);

        return getInstance();
    }

    public Object get(String key) {
        return values.get(key);
    }

    public Integer getInt(String key, Integer defaultValue) {
        Integer result = (Integer) values.get(key);
        if (result != null) {
            return result;
        } else return defaultValue;
    }

    public HashMap<String, Object> getAll() {
        return values;
    }
}
