package com.emi.nwodcombat.characterlist.mvp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterlist.RealmCharacterAdapter;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.utils.BusProvider;
import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmResults;


/**
 * Created by emiliano.desantis on 29/03/2016.
 */
public class CharacterListView extends FragmentView {
    private final Bus bus;
    private RealmCharacterAdapter realmCharacterAdapter;

    @Bind(R.id.rvCharacters) RealmRecyclerView rvCharacters;
    @Bind(R.id.fabNewCharacter) FloatingActionButton fab;
    @Bind(R.id.empty_characters) TextView empty;

    public CharacterListView(Fragment fragment, Bus bus) {
        super(fragment);
        this.bus = bus;
        ButterKnife.bind(this, fragment.getView());
        realmCharacterAdapter = new RealmCharacterAdapter(null, getActivity(), R.layout.row_character_name, true, false, BusProvider.getInstance());
        rvCharacters.setAdapter(realmCharacterAdapter);
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
        bus.post(new FabPressedEvent());
    }

    public void showNoCharacters() {
        empty.setVisibility(View.VISIBLE);
    }

    public void hideNoCharacters() {
        empty.setVisibility(View.GONE);
    }

    public void updateRV(RealmResults<Character> characters) {
        realmCharacterAdapter.updateRealmResults(characters);
    }

    public static class FabPressedEvent {

    }

    public static class ErrorEvent {
        public String message;

        ErrorEvent(String message) {
            this.message = message;
        }
    }

    public static class RemoveCharacterEvent {
        public long id;

        RemoveCharacterEvent(long id) {
            this.id = id;
        }
    }

    public static class NewCharacterEvent {
        String name;
    }
}
