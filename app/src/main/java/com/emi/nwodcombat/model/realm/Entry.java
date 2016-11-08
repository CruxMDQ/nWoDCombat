package com.emi.nwodcombat.model.realm;

import com.emi.nwodcombat.tools.Constants;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emiliano.desantis on 14/04/2016.
 */

public class Entry extends RealmObject {

    @PrimaryKey
    private long id;

    private String key;
    private String value;
    private String type;
    private RealmList<Entry> extras;

    public static Entry newInstance() {
        return new Entry();
    }

    public static Entry newInstance(long id) {
        Entry entry = new Entry();

        entry.setId(id);

        return entry;
    }

    public static Entry newInstance(String key, String type, String value) {
        Entry entry = new Entry();

        entry.setKey(key);
        entry.setType(type);
        entry.setValue(value);

        return entry;
    }

    public static Entry newInstance(String key, String type, int value) {
        Entry entry = new Entry();

        entry.setKey(key);
        entry.setType(type);
        entry.setValue(value);

        return entry;
    }

    public long getId() {
        return id;
    }

    public Entry setId(long id) {
        this.id = id;
        return this;
    }

    public void setId(long lastRecordedId, long addition) {
        this.id = lastRecordedId + addition;
    }

    public String getKey() {
        return key;
    }

    public Entry setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Entry setValue(String value) {
        this.value = value;
        return this;
    }

    public String getType() {
        return type;
    }

    public Entry setType(String type) {
        this.type = type;
        return this;
    }

    private Entry setValue(Integer integer) {
        this.value = String.valueOf(integer);
        return this;
    }

    public RealmList<Entry> getExtras() {
        return extras;
    }

    public void setExtras(RealmList<Entry> extras) {
        this.extras = extras;
    }

    public boolean hasSpecialties() {
        for (Entry extra : getExtras()) {
            if (extra.getKey().equalsIgnoreCase(Constants.SKILL_SPECIALTY)) {
                return true;
            }
        }
        return false;
    }
}
