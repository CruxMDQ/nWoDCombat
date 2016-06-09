package com.emi.nwodcombat.characterviewer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerModel;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerPresenter;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerView;
import com.emi.nwodcombat.tools.BusProvider;
import com.emi.nwodcombat.tools.Constants;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerFragment extends Fragment {
    private CharacterViewerPresenter presenter;

    public CharacterViewerFragment() {
    }

    public static CharacterViewerFragment newInstance(long idCharacter) {
        CharacterViewerFragment fragment = new CharacterViewerFragment();
        Bundle args = new Bundle();
        args.putLong(Constants.FIELD_ID, idCharacter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_character, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.character_viewer, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            presenter.onCharacterDelete();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createPresenter() {
        presenter = new CharacterViewerPresenter(new CharacterViewerModel(getActivity(), getArguments().getLong(Constants.FIELD_ID)), new CharacterViewerView(this,
                BusProvider.getInstance()));
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.register(presenter);
        presenter.checkSettings();
    }

    @Override
    public void onPause() {
        BusProvider.unregister(presenter);
        super.onPause();
    }
}
