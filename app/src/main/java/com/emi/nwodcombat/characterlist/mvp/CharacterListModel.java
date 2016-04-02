package com.emi.nwodcombat.characterlist.mvp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import com.emi.nwodcombat.characterlist.interfaces.MainMVP;
import com.emi.nwodcombat.greendao.controllers.CharacterController;
import com.emi.nwodcombat.model.db.Character;

import java.util.List;

/**
 * Created by emiliano.desantis on 29/03/2016.
 */
public class CharacterListModel implements MainMVP.ModelOps {

    // Presenter reference
    private MainMVP.RequiredPresenterOps mPresenter;
    private Context context;
    private CharacterController controller;

    public CharacterListModel(Context context, MainMVP.RequiredPresenterOps mPresenter) {
        this.context = context;
        this.mPresenter = mPresenter;
        controller = CharacterController.getInstance(context);
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
    public Loader<List<Character>> getCharatersLoader() {
        return new AsyncTaskLoader<List<Character>>(context) {
            @Override
            public List<Character> loadInBackground() {
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
