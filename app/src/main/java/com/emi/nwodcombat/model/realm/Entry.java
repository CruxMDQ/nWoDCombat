package com.emi.nwodcombat.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emiliano.desantis on 14/04/2016.
 */

public class Entry extends RealmObject {
    private static long lastId = 0;

    @PrimaryKey
    private long id;

    private String key;
    private String value;
    private String type;
    private RealmList<Entry> extras;

    public static Entry newInstance() {
        Entry entry = new Entry();
        entry.setId(lastId);
        lastId++;
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

    public Entry setValue(Integer integer) {
        this.value = String.valueOf(integer);
        return this;
    }

    public RealmList<Entry> getExtras() {
        return extras;
    }

    public void setExtras(RealmList<Entry> extras) {
        this.extras = extras;
    }

    public static void setLastId(long lastId) {
        Entry.lastId = lastId;
    }
}
