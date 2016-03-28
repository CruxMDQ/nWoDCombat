package com.emi.nwodcombat.characterlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.db.Character;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 28/03/2016.
 */
public class CharacterListFragment extends Fragment {
    @Bind(R.id.rvCharacters) RecyclerView rvCharacters;

    private ArrayList<Character> characters;

    public CharacterListFragment() { }

    public static CharacterListFragment newInstance(ArrayList<com.emi.nwodcombat.model.db.Character> characters) {
        CharacterListFragment fragment = new CharacterListFragment();
        fragment.characters = characters;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_characters, container, false);

        ButterKnife.bind(this, view);

        setUpUI();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setUpUI() {
        final CharacterAdapter characterAdapter = new CharacterAdapter<>(getActivity(), R.layout.row_character_name, this.characters);

        rvCharacters.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCharacters.setAdapter(characterAdapter);

        // TODO Define button behavior
    }
}
