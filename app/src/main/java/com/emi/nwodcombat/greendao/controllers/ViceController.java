package com.emi.nwodcombat.greendao.controllers;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.db.Vice;

/**
 * Created by Emi on 3/14/16.
 */
public class ViceController extends BaseController<Vice>{
    protected static ViceController instance;

    public static ViceController getInstance() { return instance; }

    public static ViceController getInstance(Context context) {
        if (instance == null) {
            init(context);
        }
        return instance;
    }

    public static void init(Context context) {
        try {
            instance = new ViceController(context);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("ViceController", context.getString(R.string.error_message_context_null));
        }
    }

    private ViceController(Context context) {
        dao = getSession(context).getViceDao();
    }
}
