package com.emi.nwodcombat.model.realm.modules;

import com.emi.nwodcombat.model.realm.wrappers.DemeanorTrait;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;

import io.realm.annotations.RealmModule;

/**
 * Created by emiliano.desantis on 06/04/2016.
 */
@RealmModule(classes = {Nature.class, Vice.class, Virtue.class, DemeanorTrait.class})
public class NWoDModule { }
