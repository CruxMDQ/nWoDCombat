package com.emi.nwodcombat.characterlist.mvp;

import com.emi.nwodcombat.characterlist.interfaces.MainMVP;
import com.emi.nwodcombat.model.realm.Character;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 29/03/2016.
 * Refer to this link for further info on MVP:
 * http://www.tinmegali.com/en/model-view-presenter-mvp-in-android-part-2/
 */
public class CharacterListPresenter implements MainMVP.RequiredPresenterOps, MainMVP.PresenterOps {
//    ,LoaderManager.LoaderCallbacks<List<com.emi.nwodcombat.model.realm.Character>> {

    private CharacterListView mView;

    private MainMVP.ModelOps mModel;

    public CharacterListPresenter(CharacterListModel model, CharacterListView view) {
        this.mModel = model;
        this.mView = view;
        setUpRV();
    }

    @Override
    public void newCharacter(com.emi.nwodcombat.model.realm.Character character) {
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
    public RealmResults<Character> queryCharacters() {
        return mModel.getList();
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

    public void setUpRV() {
        mView.setUpRV(mModel.getList());
    }

    @Override
    public void updateRV() {
        mView.updateRV(mModel.getList());
    }
//    @Override
//    public Loader<List<com.emi.nwodcombat.model.realm.Character>> onCreateLoader(int id, Bundle args) {
//        return mModel.getCharactersLoader();
//    }
//
//    @Override
//    public void onLoadFinished(Loader<List<com.emi.nwodcombat.model.realm.Character>> loader, List<com.emi.nwodcombat.model.realm.Character> characters) {
//        if (characters.size() == 0) {
//            mView.showNoCharacters();
//            return;
//        }
//        mView.hideNoCharacters();
//        mView.showCharacters(characters);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<List<com.emi.nwodcombat.model.realm.Character>> loader) {
//
//    }
}
