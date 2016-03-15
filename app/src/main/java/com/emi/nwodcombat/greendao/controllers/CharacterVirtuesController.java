package com.emi.nwodcombat.greendao.controllers;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.db.CharacterVirtues;

/**
 * Created by Emi on 3/15/16.
 */
public class CharacterVirtuesController extends BaseController<CharacterVirtues> {
    protected static CharacterVirtuesController instance;

    public static CharacterVirtuesController getInstance() {
        return instance;
    }

    public static CharacterVirtuesController getInstance(Context context) {
        if (instance == null) {
            init(context);
        }
        return instance;
    }

    public static void init(Context context) {
        try {
            instance = new CharacterVirtuesController(context);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("CharacterVirtuesCTRL", context.getString(R.string.error_message_context_null));
        }
    }

    private CharacterVirtuesController(Context context) {
        dao = getSession(context).getCharacterVirtuesDao();
    }
}
