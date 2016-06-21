package com.emi.nwodcombat.interfaces;

import com.emi.nwodcombat.model.realm.Entry;

/**
 * Created by emiliano.desantis on 16/06/2016.
 */
public interface SpecialtiesModel {
    Entry addSpecialty(String key, String specialtyName);

    void removeSpecialty(String key, String specialty);

    int countSpecialties();
}
