package com.emi.nwodcombat.greendao.controllers;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.db.CharacterVices;

/**
 * Created by Emi on 3/15/16.
 */
public class CharacterVicesController extends BaseController<CharacterVices> {
    protected static CharacterVicesController instance;

    public static CharacterVicesController getInstance() {
        return instance;
    }

    public static CharacterVicesController getInstance(Context context) {
        if (instance == null) {
            init(context);
        }
        return instance;
    }

    public static void init(Context context) {
        try {
            instance = new CharacterVicesController(context);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("CharacterVicesCTRL", context.getString(R.string.error_message_context_null));
        }
    }

    private CharacterVicesController(Context context) {
        dao = getSession(context).getCharacterVicesDao();
    }
}
