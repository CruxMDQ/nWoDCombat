package com.emi.nwodcombat.characterlist.mvp;

import android.app.FragmentManager;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterviewer.CharacterViewerFragment;
import com.squareup.otto.Subscribe;

import static com.emi.nwodcombat.tools.Events.CharacterDetail;
import static com.emi.nwodcombat.characterlist.mvp.CharacterListView.ErrorEvent;
import static com.emi.nwodcombat.characterlist.mvp.CharacterListView.FabPressedEvent;
import static com.emi.nwodcombat.characterlist.mvp.CharacterListView.NewCharacterEvent;
import static com.emi.nwodcombat.characterlist.mvp.CharacterListView.RemoveCharacterEvent;

/**
 * Created by emiliano.desantis on 29/03/2016.
 * Refer to this link for further info on MVP:
 * http://www.tinmegali.com/en/model-view-presenter-mvp-in-android-part-2/
 */
public class CharacterListPresenter {
    private final CharacterListView view;
    private final CharacterListModel model;

    public CharacterListPresenter(CharacterListModel model, CharacterListView view) {
        this.model = model;
        this.view = view;
        view.updateRV(model.getList());
    }

    @Subscribe
    public void onNewCharacter(NewCharacterEvent event) {
        model.insertCharacter(event.name);
        view.showSnackBar("New character created!");
    }

    @Subscribe
    public void onRemoveCharacter(RemoveCharacterEvent event) {
        model.removeCharacter(event.id);
        view.showSnackBar("Character removed!");
    }

    @Subscribe
    public void onFabPressed(FabPressedEvent event) {
        view.showSnackBar("FAB pressed");
    }

    @Subscribe
    public void onError(ErrorEvent event) {
        view.showAlert(event.message);
    }

    @Subscribe
    public void onCharacterDetailPressed(CharacterDetail event) {
        FragmentManager fragmentManager = view.getFragmentManager();
        if (fragmentManager == null) {
            return;
        }

        fragmentManager.beginTransaction().replace(R.id.flContent, CharacterViewerFragment.newInstance(event.id))
                .addToBackStack(null).commit();
    }
}
