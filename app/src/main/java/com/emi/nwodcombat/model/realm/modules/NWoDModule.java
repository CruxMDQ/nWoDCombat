package com.emi.nwodcombat.model.realm.modules;

import com.emi.nwodcombat.model.realm.PersonalityArchetype;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;

import io.realm.annotations.RealmModule;

/**
 * Created by emiliano.desantis on 06/04/2016.
 */
@RealmModule(classes = {PersonalityArchetype.class, Vice.class, Virtue.class})
public class NWoDModule { }
