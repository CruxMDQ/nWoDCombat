package com.emi.nwodcombat.model.realm;

import android.support.annotation.NonNull;

import com.emi.nwodcombat.tools.ArrayHelper;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emiliano.desantis on 05/04/2016.
 */
public class Character extends RealmObject {
    @PrimaryKey
    private Long id;

    private RealmList<POJOField> pojoFields = new RealmList<>();
    private RealmList<Nature> natures = new RealmList<>();
    private RealmList<Demeanor> demeanors = new RealmList<>();
    private RealmList<Vice> vices = new RealmList<>();
    private RealmList<Virtue> virtues = new RealmList<>();

    public RealmList<Nature> getNatures() {
        return natures;
    }

    public void setNatures(RealmList<Nature> natures) {
        this.natures = natures;
    }

    public RealmList<Demeanor> getDemeanors() {
        return demeanors;
    }

    public void setDemeanors(RealmList<Demeanor> demeanors) {
        this.demeanors = demeanors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RealmList<Vice> getVices() {
        return vices;
    }

    public void setVices(RealmList<Vice> vices) {
        this.vices = vices;
    }

    public RealmList<Virtue> getVirtues() {
        return virtues;
    }

    public void setVirtues(RealmList<Virtue> virtues) {
        this.virtues = virtues;
    }

    public RealmList<POJOField> getPojoFields() {
        return pojoFields;
    }

    public void setPojoFields(RealmList<POJOField> pojoFields) {
        this.pojoFields = pojoFields;
    }

    public int getSkill(@NonNull String code) {
        String result = ArrayHelper.find(pojoFields, code);

        return result != null ? Integer.valueOf(result) : 0;
    }

    public Object getValue(@NonNull String code, Class klass) {
        if (klass.equals(Integer.class)) {
            String result = ArrayHelper.find(pojoFields, code);

            return result != null ? Integer.valueOf(result) : 0;
        } else if (klass.equals(String.class)) {
            return ArrayHelper.find(pojoFields, code);
        }
        return null;
    }
}
