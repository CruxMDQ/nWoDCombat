package com.emi.nwodcombat.model.realm;

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

    public long getId() {
        return id;
    }

    public Entry setId(long id) {
        this.id = id;
        return this;
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
}
