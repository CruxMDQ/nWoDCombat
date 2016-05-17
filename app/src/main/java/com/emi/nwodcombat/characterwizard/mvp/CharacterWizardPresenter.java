package com.emi.nwodcombat.characterwizard.mvp;

import android.app.FragmentManager;

import com.emi.nwodcombat.characterwizard.CharacterWizardPagerAdapter;
import com.emi.nwodcombat.characterwizard.steps.AttrSettingFragment;
import com.emi.nwodcombat.characterwizard.steps.PagerFragment;
import com.emi.nwodcombat.characterwizard.steps.PersonalInfoFragment;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emiliano.desantis on 12/05/2016.
 */
public class CharacterWizardPresenter {
    private CharacterWizardView view;
    private CharacterWizardModel model;
    private CharacterWizardPagerAdapter adapter;

    public CharacterWizardPresenter(FragmentManager fragmentManager,
                                    CharacterWizardModel model, CharacterWizardView view) {
        this.model = model;
        this.view = view;

        this.adapter = new CharacterWizardPagerAdapter(fragmentManager, getClassesList());
    }

    private List<Class<? extends PagerFragment>> getClassesList() {
        ArrayList<Class<? extends PagerFragment>> classes = new ArrayList<>();

        classes.add(PersonalInfoFragment.class);
        classes.add(AttrSettingFragment.class);

        return classes;
    }

    public void setUpView() {
        view.setAdapter(adapter);
    }

    public void moveToNextStep(int currentItem) {
        // Move pager forward
        view.pagerGoForward();

        // Change title
        changeTitle(currentItem);

        // After moving pager forward, if this is a child step, retrieve choices affecting how you distribute points
//        if (fragmentList.get(pager.getCurrentItem()) instanceof PagerStep.ChildStep) {
//            ((PagerStep.ChildStep) fragmentList.get(pager.getCurrentItem())).retrieveChoices();
//        }
    }

    public void moveToPreviousStep(int currentItem) {
        view.pagerGoBackwards();

        if (currentItem >= 0) {
            changeTitle(currentItem);
        }
    }

    private void changeTitle(int currentItem) {
        view.setToolbarTitle(adapter.getItem(currentItem).getToolbarTitle());
    }

    private void finishWizard() {
        // TODO Code wizard completion
//        pagerFinisher.onPagerFinished();
    }

    @Subscribe
    public void onStepCompletionChecked(StepCompletionCheckEvent event) {
        view.toggleNextButton(event.isStepComplete);
    }

    @Subscribe
    public void onWizardProgressEvent(CharacterWizardView.WizardProgressEvent event) {
        int lastPage = adapter.getCount() - 1;

        if (event.movesForward) {
            if (event.currentItem < lastPage) {
                moveToNextStep(event.currentItem);
            } else {
                finishWizard();
            }
        } else {
            moveToPreviousStep(event.currentItem);
        }
    }

    public static class StepCompletionCheckEvent {
        final boolean isStepComplete;

        public StepCompletionCheckEvent(boolean isStepComplete) {
            this.isStepComplete = isStepComplete;
        }
    }
}
