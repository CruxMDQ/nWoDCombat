package com.emi.nwodcombat.characterlist.mvp;

import android.content.Context;

import com.emi.nwodcombat.characterlist.interfaces.MainMVP;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.persistence.RealmHelper;

import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 29/03/2016.
 */
public class CharacterListModel implements MainMVP.ModelOps {

    // Presenter reference
    private MainMVP.RequiredPresenterOps mPresenter;
    private Context context;
    private RealmHelper helper;

    public CharacterListModel(Context context) {
        this.context = context;
        helper = RealmHelper.getInstance(context);
    }

    public CharacterListModel(Context context, MainMVP.RequiredPresenterOps mPresenter) {
        this.context = context;
        this.mPresenter = mPresenter;
        helper = RealmHelper.getInstance(context);
    }

    @Override
    public void insertCharacter(com.emi.nwodcombat.model.realm.Character character) {
//        controller.save(character);
        helper.save(character);
        mPresenter.onCharacterAdded();
    }

    @Override
    public void removeCharacter(long id) {
//        controller.deleteById(character);
        helper.delete(id);
        mPresenter.onCharacterRemoved();
    }

    @Override
    public void onDestroy() {
        // Should stop/kill operations that could be running and aren't needed anymore
    }

//    @Override
//    public Loader<List<com.emi.nwodcombat.model.realm.Character>> getCharactersLoader() {
//        return new AsyncTaskLoader<List<com.emi.nwodcombat.model.realm.Character>>(context) {
//            @Override
//            public List<com.emi.nwodcombat.model.realm.Character> loadInBackground() {
//                return helper.getList(com.emi.nwodcombat.model.realm.Character.class);
//            }
//
//            @Override
//            protected void onStartLoading() {
//                super.onStartLoading();
//                forceLoad();
//            }
//        };
//    }

    @Override
    public RealmResults<Character> getList() {
        return helper.getList(Character.class);
    }

    public void setCallback(CharacterListPresenter callback) {
        this.mPresenter = callback;
    }

    public MainMVP.RequiredPresenterOps getCallback() {
        return mPresenter;
    }
}
