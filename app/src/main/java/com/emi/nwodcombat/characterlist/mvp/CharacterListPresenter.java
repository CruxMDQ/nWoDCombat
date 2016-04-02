package com.emi.nwodcombat.characterlist.mvp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.emi.nwodcombat.characterlist.interfaces.MainMVP;
import com.emi.nwodcombat.model.db.Character;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by emiliano.desantis on 29/03/2016.
 * Refer to this link for further info on MVP:
 * http://www.tinmegali.com/en/model-view-presenter-mvp-in-android-part-2/
 */
public class CharacterListPresenter implements MainMVP.RequiredPresenterOps, MainMVP.PresenterOps, LoaderManager.LoaderCallbacks<List<Character>>{

    // Layer View reference  [VSM] this is not a layer
    private CharacterListView mView;

    // Layer Model reference  [VSM] this is not a layer
    private MainMVP.ModelOps mModel;

    public CharacterListPresenter(CharacterListModel model, CharacterListView view) {
        this.mModel = model;
        this.mView = view;
    }

    @Override
    //TODO this characterJson doesn't belong here instead this should be done into the model.
    //Due this method is not used I can't give you a suggestion about the best place for this code.
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
    public void onFabPressed() {
        mView.showSnackBar("FAB pressed");
    }

    @Override
    public void onCharacterAdded() {
        mView.showSnackBar("New character created!");
    }

    @Override
    public void onCharacterRemoved() {
        mView.showSnackBar("Character removed!");
    }

    @Override
    public void onError(String message) {
        mView.showAlert(message);
    }

    @Override
    public Loader<List<Character>> onCreateLoader(int id, Bundle args) {
        return mModel.getCharatersLoader();
    }

    @Override
    public void onLoadFinished(Loader<List<Character>> loader, List<Character> characters) {
        if (characters.size() == 0) {
            mView.showNoCharacters();
            return;
        }
        mView.hideNoCharacters();
        mView.showCharacters(characters);
    }

    @Override
    public void onLoaderReset(Loader<List<Character>> loader) {

    }
}
