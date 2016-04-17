package com.emi.nwodcombat.characterlist.mvp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.emi.nwodcombat.FragmentView;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterlist.RealmCharacterAdapter;
import com.emi.nwodcombat.characterlist.interfaces.MainMVP;
import com.emi.nwodcombat.model.realm.Character;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * Created by emiliano.desantis on 29/03/2016.
 */
public class CharacterListView extends FragmentView implements MainMVP.RequiredViewOps {
    private RealmCharacterAdapter realmCharacterAdapter;

    @Bind(R.id.rvCharacters) co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView rvCharacters;
    @Bind(R.id.fabNewCharacter) FloatingActionButton fab;
    @Bind(R.id.empty_characters) TextView empty;

    private MainMVP.PresenterOps mPresenter;

    public CharacterListView(Fragment fragment) {
        super(fragment);
        ButterKnife.bind(this, fragment.getView());
    }

    public void showSnackBar(String s) {
        Snackbar.make(rvCharacters, s, Snackbar.LENGTH_SHORT).show();
    }

    public void showAlert(String message) {
        AlertDialog.Builder d = new AlertDialog.Builder(getActivity());
        d.setTitle(R.string.alert_title);
        d.setMessage(message);
        d.setCancelable(true);
        d.create().show();
    }

    @OnClick(R.id.fabNewCharacter)
    public void fabPressed() {
        mPresenter.onFabPressed();
    }

    public void showNoCharacters() {
        empty.setVisibility(View.VISIBLE);
    }

    public void hideNoCharacters() {
        empty.setVisibility(View.GONE);
    }

    public void setCallback(CharacterListPresenter callback) {
        this.mPresenter = callback;
    }

    public MainMVP.PresenterOps getCallback() {
        return mPresenter;
    }

    public void setUpRV(RealmResults<Character> characters) {
        realmCharacterAdapter = new RealmCharacterAdapter(characters, getActivity(), R.layout.row_character_name, true, false);
        rvCharacters.setAdapter(realmCharacterAdapter);
    }

    public void updateRV(RealmResults<Character> characters) {
        realmCharacterAdapter.updateRealmResults(characters);
        rvCharacters.setAdapter(realmCharacterAdapter);
    }
}
