package com.emi.nwodcombat.characterviewer.mvp;

import com.squareup.otto.Subscribe;

import static com.emi.nwodcombat.characterviewer.mvp.CharacterViewerView.*;

/**
 * Created by emiliano.desantis on 12/04/2016.
 */
public class CharacterViewerPresenter  {
    private CharacterViewerView view;
    private CharacterViewerModel model;

    public CharacterViewerPresenter(CharacterViewerModel model, CharacterViewerView view) {
        this.model = model;
        this.view = view;
    }

    public void setUpView(long idCharacter) {
        view.setUpView(model.getQueriedCharacter(idCharacter));
        view.setUpVirtueSpinner(model.getVirtues());
        view.setUpViceSpinner(model.getVices());
        view.setUpNaturesSpinner(model.getNatures());
        view.setUpDemeanorsSpinner(model.getDemeanors());
    }

    public void onPause() {
        view.onPause();
    }

    public void onCharacterDelete() {
        view.onCharacterDelete();
    }

    @Subscribe
    public void onUpdateCharacterEvent(UpdateCharacterEvent event) {
        model.updateCharacter(event.characterToUpdate);
    }

    @Subscribe
    public void onDeleteCharacterEvent(DeleteCharacterEvent event) {
        model.deleteCharacter(event.characterToDelete);
    }
}
