package com.emi.nwodcombat.characterwizard.mvp;

import android.content.Context;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.adapters.CharacterWizardPagerAdapter;
import com.emi.nwodcombat.characterwizard.steps.AttrSettingFragment;
import com.emi.nwodcombat.characterwizard.steps.MeritsFragment;
import com.emi.nwodcombat.characterwizard.steps.PagerFragment;
import com.emi.nwodcombat.characterwizard.steps.PersonalInfoFragment;
import com.emi.nwodcombat.characterwizard.steps.SkillSettingFragment;
import com.emi.nwodcombat.characterwizard.steps.SummaryFragment;
import com.emi.nwodcombat.tools.BusProvider;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emiliano.desantis on 12/05/2016.
 */
public class CharacterWizardPresenter {
    private final Context context;
    private final CharacterWizardView view;
    private final CharacterWizardModel model;
    private final CharacterWizardPagerAdapter adapter;
    private final Bus bus;

    public CharacterWizardPresenter(CharacterWizardModel model, CharacterWizardView view) {
        this.model = model;
        this.view = view;
        this.adapter = new CharacterWizardPagerAdapter(view.getChildFragmentManager(), getClassesList());
        this.context = view.getContext();

        setTitle(R.string.title_character_create);

        bus = BusProvider.getInstance();
        view.setAdapter(adapter);
        view.setPreviousLabel(context.getString(R.string.button_cancel));
        model.setupNewCharacter();
    }

    private List<Class<? extends PagerFragment>> getClassesList() {
        ArrayList<Class<? extends PagerFragment>> classes = new ArrayList<>();

        classes.add(PersonalInfoFragment.class);
        classes.add(AttrSettingFragment.class);
        classes.add(SkillSettingFragment.class);
        classes.add(MeritsFragment.class);
        classes.add(SummaryFragment.class);

        return classes;
    }

    private void moveToNextStep() {
        view.pagerGoForward();
    }

    private void moveToPreviousStep() {
        view.pagerGoBackwards();
    }

    private void setTitle(int resId) {
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
    public void onWizardProgressEvent(Events.WizardProgressEvent event) {
        model.refreshCharacter();

        int lastPage = adapter.getCount() - 1;
        int nextPage = event.movesForward ? event.currentItem + 1 : event.currentItem - 1;

        if (nextPage < 0) {
            // If yes, then remove the fragment altogether from the view
            view.getActivity().getFragmentManager().popBackStack();
            return;
        }

        if (nextPage > lastPage) {
            finishWizard();
            return;
        } else if (event.movesForward && nextPage == lastPage) {
            bus.post(new Events.WizardComplete());
        }

        view.setNextLabel(nextPage == lastPage ? context.getString(R.string.button_finish)
                : context.getString(R.string.button_next));

        view.setPreviousLabel(nextPage == 0 ? context.getString(R.string.button_cancel)
                : context.getString(R.string.button_previous));

        if (event.movesForward) {
            moveToNextStep();
        } else {
            moveToPreviousStep();
        }
    }

}
