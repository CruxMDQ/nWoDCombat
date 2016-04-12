package com.emi.nwodcombat.characterlist.mvp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import com.emi.nwodcombat.characterlist.interfaces.MainMVP;
import com.emi.nwodcombat.greendao.controllers.CharacterController;
import com.emi.nwodcombat.persistence.RealmHelper;

import java.util.List;

/**
 * Created by emiliano.desantis on 29/03/2016.
 */
public class CharacterListModel implements MainMVP.ModelOps {

    // Presenter reference
    private MainMVP.RequiredPresenterOps mPresenter;
    private Context context;
    private CharacterController controller;
//    private RealmHelper helper;

    public CharacterListModel(Context context, MainMVP.RequiredPresenterOps mPresenter) {
        this.context = context;
        this.mPresenter = mPresenter;
        controller = CharacterController.getInstance(context);
//        helper = RealmHelper.getInstance(context);
    }

    @Override
    public void insertCharacter(com.emi.nwodcombat.model.db.Character character) {
        controller.save(character);
        mPresenter.onCharacterAdded();
    }

    @Override
    public void removeCharacter(long character) {
        controller.deleteById(character);
        mPresenter.onCharacterRemoved();
    }

    @Override
    public void onDestroy() {
        // Should stop/kill operations that could be running and aren't needed anymore
    }

    @Override
    public Loader<List<Object>> getCharactersLoader() {
        return new AsyncTaskLoader<List<Object>>(context) {
            @Override
            public List<Object> loadInBackground() {
                return controller.getList();
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }
        };
    }
}
