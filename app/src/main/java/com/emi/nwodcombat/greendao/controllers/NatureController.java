package com.emi.nwodcombat.greendao.controllers;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.db.Nature;

/**
 * Created by Crux on 3/26/2016.
 */
public class NatureController extends BaseController<Nature> {
    protected static NatureController instance;

    public static NatureController getInstance() { return instance; }

    public static NatureController getInstance(Context context) {
        if (instance == null) {
            init(context);
        }
        return instance;
    }

    public static void init(Context context) {
        try {
            instance = new NatureController(context);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("CharacterVicesCTRL", context.getString(R.string.error_message_context_null));
        }
    }

    private NatureController(Context context) {
        dao = getSession(context).getNatureDao();
    }
}
