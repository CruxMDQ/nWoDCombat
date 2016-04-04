package com.emi.nwodcombat.greendao.controllers;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.db.Demeanor;

/**
 * Created by Crux on 3/26/2016.
 */
public class DemeanorController extends BaseController<Demeanor> {
    protected static DemeanorController instance;

    public static DemeanorController getInstance() { return instance; }

    public static DemeanorController getInstance(Context context) {
        if (instance == null) {
            init(context);
        }
        return instance;
    }

    public static void init(Context context) {
        try {
            instance = new DemeanorController(context);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("CharacterVicesCTRL", context.getString(R.string.error_message_context_null));
        }
    }

    private DemeanorController(Context context) {
        dao = getSession(context).getDemeanorDao();
    }
}
