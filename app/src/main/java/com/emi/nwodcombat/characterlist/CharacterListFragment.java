package com.emi.nwodcombat.characterlist;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterlist.mvp.CharacterListModel;
import com.emi.nwodcombat.characterlist.mvp.CharacterListPresenter;
import com.emi.nwodcombat.characterlist.mvp.CharacterListView;
import com.emi.nwodcombat.persistence.RealmHelper;
import com.emi.nwodcombat.utils.BusProvider;

/**
 * Created by emiliano.desantis on 28/03/2016.
 */
public class CharacterListFragment extends Fragment {

    private CharacterListPresenter presenter;

    public CharacterListFragment() {
    }

    public static CharacterListFragment newInstance() {
        return new CharacterListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createPresenter();
    }

    private void createPresenter() {
        presenter = new CharacterListPresenter(new CharacterListModel(RealmHelper.getInstance(getActivity())),
                new CharacterListView(this, BusProvider.getInstance()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_characters, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.register(presenter);
    }

    @Override
    public void onPause() {
        BusProvider.unregister(presenter);
        super.onPause();
    }
}
