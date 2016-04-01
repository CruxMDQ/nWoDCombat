package com.emi.nwodcombat.characterlist.mvp;

import com.emi.nwodcombat.characterlist.interfaces.MainMVP;
import com.emi.nwodcombat.model.db.Character;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;

/**
 * Created by emiliano.desantis on 29/03/2016.
 * Refer to this link for further info on MVP:
 * http://www.tinmegali.com/en/model-view-presenter-mvp-in-android-part-2/
 */
public class CharacterListPresenter implements MainMVP.RequiredPresenterOps, MainMVP.PresenterOps {

    // Layer View reference
    private WeakReference<MainMVP.RequiredViewOps> mView;

    // Layer Model reference
    private MainMVP.ModelOps mModel;

    public CharacterListPresenter(MainMVP.RequiredViewOps view) {
        this.mModel = new CharacterListModel(this);
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void newCharacter(String characterJson) {
        Character character;
        Gson gson = new Gson();
        Type type = new TypeToken<Character>() {}.getType();
        character = gson.fromJson(characterJson, type);
        mModel.insertCharacter(character);
    }

    @Override
    public void removeCharacter(long idCharacter) {
        mModel.removeCharacter(idCharacter);
    }

    @Override
    public void onCharacterAdded() {
        mView.get().showSnackBar("New character created!");
    }

    @Override
    public void onCharacterRemoved() {
        mView.get().showSnackBar("Character removed!");
    }

    @Override
    public void onError(String message) {
        mView.get().showAlert(message);
    }
}
