package com.emi.nwodcombat.characterviewer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerModel;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerPresenter;
import com.emi.nwodcombat.characterviewer.mvp.CharacterViewerView;
import com.emi.nwodcombat.utils.Constants;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerFragment extends Fragment {
    private CharacterViewerPresenter mPresenter;

    public CharacterViewerFragment() {}

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

        if (mPresenter == null) {
            createPresenter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_character, container, false);
    }


    private void createPresenter() {
        mPresenter = new CharacterViewerPresenter(new CharacterViewerModel(getActivity()), new CharacterViewerView(this));
        mPresenter.setUpView(this.getArguments().getLong(Constants.FIELD_ID));
    }

}
