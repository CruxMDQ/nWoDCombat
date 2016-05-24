package com.emi.nwodcombat.characterwizard.mvp;

import android.content.Context;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.CharacterWizardPagerAdapter;
import com.emi.nwodcombat.characterwizard.steps.AttrSettingFragment;
import com.emi.nwodcombat.characterwizard.steps.PagerFragment;
import com.emi.nwodcombat.characterwizard.steps.PersonalInfoFragment;
import com.emi.nwodcombat.characterwizard.steps.SkillSettingFragment;
import com.emi.nwodcombat.characterwizard.steps.SummaryFragment;
import com.emi.nwodcombat.utils.BusProvider;
import com.emi.nwodcombat.utils.Events;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emiliano.desantis on 12/05/2016.
 */
public class CharacterWizardPresenter {
    private final Context context;
    private CharacterWizardView view;
    private CharacterWizardModel model;
    private CharacterWizardPagerAdapter adapter;
    private Bus bus;

    public CharacterWizardPresenter(CharacterWizardModel model, CharacterWizardView view) {
        this.model = model;
        this.view = view;
        this.adapter = new CharacterWizardPagerAdapter(view.getChildFragmentManager(), getClassesList());
        this.context = view.getContext();

        this.changeTitle(R.string.title_character_create);

        bus = BusProvider.getInstance();

        view.setAdapter(adapter);
        model.setupNewCharacter();
    }

    private List<Class<? extends PagerFragment>> getClassesList() {
        ArrayList<Class<? extends PagerFragment>> classes = new ArrayList<>();

        classes.add(PersonalInfoFragment.class);
        classes.add(AttrSettingFragment.class);
        classes.add(SkillSettingFragment.class);
        classes.add(SummaryFragment.class);

        return classes;
    }

    public void moveToNextStep(int currentItem) {
        // Move pager forward

        // Get last page
        int lastPage = adapter.getCount() - 1;

        // Change button label depending on where on the wizard we are
        if (currentItem == lastPage) {
            view.setNextLabel(context.getString(R.string.button_finish));
            return;
        } else {
            view.setNextLabel(context.getString(R.string.button_next));
        }

        view.pagerGoForward();
    }

    public void moveToPreviousStep(int currentItem) {
        view.pagerGoBackwards(currentItem);
    }

    private void changeTitle(int resId) {
        view.setToolbarTitle(context.getString(resId));
    }

    private void finishWizard() {
        model.save();

        bus.post(new Events.WizardClose());
    }

    @Subscribe
    public void onStepCompletionChecked(Events.StepCompletionChecked event) {
        view.toggleNextButton(event.isStepComplete);
    }

    @Subscribe
    public void onWizardProgressEvent(CharacterWizardView.WizardProgressEvent event) {
        int lastPage = adapter.getCount() - 1;

        if (event.movesForward) {
            if (event.currentItem < lastPage) {
                moveToNextStep(event.currentItem);

                if (event.currentItem + 1 == lastPage) {
                    bus.post(new Events.WizardComplete());
                }
            } else {
                finishWizard();
            }
        } else {
            moveToPreviousStep(event.currentItem);
        }
    }

}
