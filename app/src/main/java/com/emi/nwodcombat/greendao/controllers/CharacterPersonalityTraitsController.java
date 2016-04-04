package com.emi.nwodcombat.greendao.controllers;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.db.CharacterPersonalityTraits;

/**
 * Created by Crux on 3/25/2016.
 */
public class CharacterPersonalityTraitsController extends BaseController<CharacterPersonalityTraits> {
    protected static CharacterPersonalityTraitsController instance;

    public static CharacterPersonalityTraitsController getInstance() { return instance; }

    public static CharacterPersonalityTraitsController getInstance(Context context) {
        if (instance == null) {
            init(context);
        }
        return instance;
    }

    private static void init(Context context) {
        try {
            instance = new CharacterPersonalityTraitsController(context);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("CharacterVicesCTRL", context.getString(R.string.error_message_context_null));
        }
    }

    private CharacterPersonalityTraitsController(Context context) {
        dao = getSession(context).getCharacterPersonalityTraitsDao();
    }
}
