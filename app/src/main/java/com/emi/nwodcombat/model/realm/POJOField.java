package com.emi.nwodcombat.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emiliano.desantis on 14/04/2016.
 */
public class POJOField extends RealmObject {
    @PrimaryKey
    private long id;

    private String key;
    private String value;
    private String type;

    public long getId() {
        return id;
    }

    public POJOField setId(long id) {
        this.id = id;
        return this;
    }

    public String getKey() {
        return key;
    }

    public POJOField setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public POJOField setValue(String value) {
        this.value = value;
        return this;
    }

    public String getType() {
        return type;
    }

    public POJOField setType(String type) {
        this.type = type;
        return this;
    }
}
