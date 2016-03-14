package com.emi.nwodcombat.greendao.controllers;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.db.Virtue;

/**
 * Created by Emi on 3/14/16.
 */
public class VirtueController extends BaseController<Virtue>{
    protected static VirtueController instance;

    public static VirtueController getInstance() { return instance; }

    public static VirtueController getInstance(Context context) {
        if (instance == null) {
            init(context);
        }
        return instance;
    }

    public static void init(Context context) {
        try {
            instance = new VirtueController(context);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("VirtueController", context.getString(R.string.error_message_context_null));
        }
    }

    private VirtueController(Context context) {
        dao = getSession(context).getVirtueDao();
    }
}
