package com.emi.nwodcombat.characterwizard.mvp;

import android.content.Context;
import android.content.res.Resources;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.pojos.Trait;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Subscribe;

/**
 * Created by emiliano.desantis on 24/05/2016.
 */
public class SummaryPresenter {
    private final Context context;
    private final SummaryView view;
    private final CharacterWizardModel model;

    public SummaryPresenter(CharacterWizardModel model, SummaryView view) {
        this.model = model;
        this.view = view;
        this.context = this.view.getContext();

        setUpUI();

//        populateView();
    }

    private void setUpUI() {
        String advantage = getString(R.string.kind_advantage);

        view.setUpValueSetterDefense(new Trait(advantage, getString(R.string.trait_defense),
            getString(R.string.cat_derived), null));
        view.setUpValueSetterHealth(new Trait(advantage, getString(R.string.trait_health),
            getString(R.string.cat_derived), null));
        view.setUpValueSetterInitiative(new Trait(advantage, getString(R.string.trait_initiative),
            getString(R.string.cat_derived), null));
        view.setUpValueSetterMorality(new Trait(advantage, getString(R.string.trait_morality),
            getString(R.string.cat_derived), null));
        view.setUpValueSetterSpeed(new Trait(advantage, getString(R.string.trait_speed),
            getString(R.string.cat_derived), null));
        view.setUpValueSetterWillpower(new Trait(advantage, getString(R.string.trait_willpower),
            getString(R.string.cat_derived), null));
    }

    @Subscribe
    public void onWizardComplete(Events.WizardComplete event) {
        populateView();
    }

    private void populateView() {
        view.setAttrSummaryMental(model.getMentalAttrSummary());
        view.setAttrSummaryPhysical(model.getPhysicalAttrSummary());
        view.setAttrSummarySocial(model.getSocialAttrSummary());
        view.setSkillSummaryMental(model.getMentalSkillsSummary());
        view.setSkillSummaryPhysical(model.getPhysicalSkillsSummary());
        view.setSkillSummarySocial(model.getSocialSkillsSummary());
        view.setSkillSummarySpecialties(model.getSpecialtiesSummary());
        view.setMeritsSummary(model.getMeritsSummary());

        view.setDefense(model.calculateDefense());
        view.setHealth(model.calculateHealth());
        view.setInitiative(model.calculateInitiative());
        view.setMorality(model.calculateMorality());
        view.setSpeed(model.calculateSpeed());
        view.setWillpower(model.calculateWillpower());
    }

    @SuppressWarnings("ConstantConditions")
    private String getString(int resId) {
        Resources resources = context.getResources();

        return resources.getString(resId);
    }
}
