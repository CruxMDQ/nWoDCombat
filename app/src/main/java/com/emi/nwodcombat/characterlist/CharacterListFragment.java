package com.emi.nwodcombat.characterlist;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterlist.interfaces.MainMVP;
import com.emi.nwodcombat.characterlist.mvp.CharacterListModel;
import com.emi.nwodcombat.characterlist.mvp.CharacterListPresenter;
import com.emi.nwodcombat.characterlist.mvp.CharacterListView;

/**
 * Created by emiliano.desantis on 28/03/2016.
 */
public class CharacterListFragment extends Fragment {

    private CharacterListPresenter presenter;

    public CharacterListFragment() {
    }

    //VSM: You can use new CharacterListFragment() instead newInstance() due there is not extra parameters involved
    public static CharacterListFragment newInstance() {
        return new CharacterListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // According to Santiago Vidal, this goes here.
        // VSM: I don't know where it was before but this code goes here
        if (presenter == null) {
            createPresenter();
        }

    }

    private void createPresenter() {
        //VSM: With Even bus you don't need delegators or callbacks
        presenter = new CharacterListPresenter(new CharacterListModel(getActivity(), modelDelegator),
                new CharacterListView(this, viewDelegator));

        //VSM: If you want to avoid delegators you can do this:
        /*
        MainMVP.ModelOps model = new CharacterListModel(getActivity());
        FragmentView view = new CharacterListView(getActivity());
        presenter = new CharacterListPresenter(model, view);
        model.setCallback(presenter); // Due presenter implements model callback interface
        view.setCallback(presenter); // Due presenter implements view callback interface
        */

        getLoaderManager().initLoader(R.id.characters_loader, null, presenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_characters, container, false);
    }

    //Callback from model
    //WARNING:  most of these method should be bubbled through the Loaders.
    private MainMVP.RequiredPresenterOps modelDelegator = new MainMVP.RequiredPresenterOps() {
        @Override
        public void onCharacterAdded() {
            presenter.onCharacterAdded();
        }

        @Override
        public void onCharacterRemoved() {
            presenter.onCharacterRemoved();
        }

        @Override
        public void onError(String message) {
            presenter.onError(message);
        }
    };

    //Callback from view
    private MainMVP.PresenterOps viewDelegator = new MainMVP.PresenterOps() {
        @Override
        public void newCharacter(String characterJson) {
            presenter.newCharacter(characterJson);
        }

        @Override
        public void removeCharacter(long idCharacter) {
            presenter.removeCharacter(idCharacter);
        }

        @Override
        public void onFabPressed() {
            presenter.onFabPressed();
        }
    };
}
