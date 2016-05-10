package com.emi.nwodcombat.model.realm.wrappers;

import com.emi.nwodcombat.model.realm.Demeanor;

import io.realm.RealmObject;

/**
 * Created by Crux on 5/6/2016.
 * This class has a non-generic object for its 'value' field because an approach using a generic
 * RealmObject instead failed.
 */
public class DemeanorTrait extends RealmObject {
    private Long ordinal;
    private String type;
    private Demeanor demeanor;

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

    public Demeanor getDemeanor() {
        return demeanor;
    }

    public void setDemeanor(Demeanor demeanor) {
        this.demeanor = demeanor;
    }
}
