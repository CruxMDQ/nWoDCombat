package com.emi.nwodcombat.characterlist.mvp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.emi.nwodcombat.FragmentView;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterlist.CharacterAdapter;
import com.emi.nwodcombat.characterlist.interfaces.MainMVP;
import com.emi.nwodcombat.model.db.Character;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by emiliano.desantis on 29/03/2016.
 */
public class CharacterListView extends FragmentView implements MainMVP.RequiredViewOps {
    private final CharacterAdapter characterAdapter;

    private ArrayList<Object> characters = new ArrayList<>();

    @Bind(R.id.rvCharacters) RecyclerView rvCharacters;
    @Bind(R.id.fabNewCharacter) FloatingActionButton fab;
    @Bind(R.id.empty_characters) TextView empty;

    private MainMVP.PresenterOps viewDelegator;

    public CharacterListView(Fragment fragment, MainMVP.PresenterOps viewDelegator) {
        super(fragment);
        ButterKnife.bind(this, fragment.getView());

        this.viewDelegator = viewDelegator;
        characterAdapter = new CharacterAdapter(getActivity(), R.layout.row_character_name, characters);
        rvCharacters.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCharacters.setAdapter(characterAdapter);
    }

    public void showCharacters(List<Object> characters) {
        this.characters.clear();
        this.characters.addAll(characters);
        characterAdapter.notifyDataSetChanged();
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
        viewDelegator.onFabPressed();
    }

    public void showNoCharacters() {
        empty.setVisibility(View.VISIBLE);
    }

    public void hideNoCharacters() {
        empty.setVisibility(View.GONE);
    }
}
