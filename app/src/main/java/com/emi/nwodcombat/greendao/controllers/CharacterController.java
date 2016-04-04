package com.emi.nwodcombat.greendao.controllers;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.db.Character;

/**
 * Created by Emi on 2/27/16.
 */
public class CharacterController extends BaseController<Character> {
    protected static CharacterController instance;

    public static CharacterController getInstance() {
        return instance;
    }

    public static CharacterController getInstance(Context context) {
        if (instance == null) {
            init(context);
        }
        return instance;
    }

    public static void init(Context context) {
        try {
            instance = new CharacterController(context);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("CharacterController", context.getString(R.string.error_message_context_null));
        }
    }

    private CharacterController(Context context) {
        dao = getSession(context).getCharacterDao();
    }

}
