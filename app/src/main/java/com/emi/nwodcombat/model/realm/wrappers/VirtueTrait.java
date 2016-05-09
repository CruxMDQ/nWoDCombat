package com.emi.nwodcombat.model.realm.wrappers;

import com.emi.nwodcombat.model.realm.Virtue;

import io.realm.RealmObject;

/**
 * Created by Crux on 9/6/2016.
 * This class has a non-generic object for its 'value' field because an approach using a generic
 * RealmObject instead failed.
 */
public class VirtueTrait extends RealmObject {
    private Long ordinal;
    private String type;
    private Virtue virtue;

    public Long getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Long ordinal) {
        this.ordinal = ordinal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Virtue getVirtue() {
        return virtue;
    }

    public void setVirtue(Virtue demeanor) {
        this.virtue = demeanor;
    }
}
