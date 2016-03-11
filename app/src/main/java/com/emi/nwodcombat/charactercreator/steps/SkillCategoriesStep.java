package com.emi.nwodcombat.charactercreator.steps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.CategorySettingDialog;
import com.emi.nwodcombat.charactercreator.interfaces.PagerMaster;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;
import com.emi.nwodcombat.interfaces.AfterSettingRulesListener;
import com.emi.nwodcombat.model.pojos.Rule;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/4/16.
 */
public class SkillCategoriesStep extends WizardStep implements AfterSettingRulesListener, PagerStep.ParentStep {
    private int mentalPoints;
    private int physicalPoints;
    private int socialPoints;

    @Bind(R.id.btnSetMentalSkillCategory) Button btnSetMentalSkillCategory;
    @Bind(R.id.btnSetPhysicalSkillCategory) Button btnSetPhysicalSkillCategory;
    @Bind(R.id.btnSetSocialSkillCategory) Button btnSetSocialSkillCategory;
    @Bind(R.id.txtSkillMental) TextView txtSkillMental;
    @Bind(R.id.txtSkillPhysical) TextView txtSkillPhysical;
    @Bind(R.id.txtSkillSocial) TextView txtSkillSocial;

    private ArrayList<Rule> categories;

    public SkillCategoriesStep() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
            getLayout(), container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpUI();

        categories = generateCategories();
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);

        if (isVisible) {
            pagerMaster.checkStepIsComplete(false, this);
        }
    }

    private ArrayList<Rule> generateCategories() {
        ArrayList<Rule> rules = new ArrayList<>();

        rules.add(new Rule(Constants.CATEGORY_PRIMARY, false, Constants.SKILL_PTS_PRIMARY));
        rules.add(new Rule(Constants.CATEGORY_SECONDARY, false, Constants.SKILL_PTS_SECONDARY));
        rules.add(new Rule(Constants.CATEGORY_TERTIARY, false, Constants.SKILL_PTS_TERTIARY));

        return rules;
    }

    protected void setUpUI() {
        btnSetMentalSkillCategory.setContentDescription(Constants.CONTENT_DESC_SKILL_MENTAL);
        btnSetMentalSkillCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnSetMentalSkillCategory.getContentDescription().toString(), categories, SkillCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });

        btnSetPhysicalSkillCategory.setContentDescription(Constants.CONTENT_DESC_SKILL_PHYSICAL);
        btnSetPhysicalSkillCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnSetPhysicalSkillCategory.getContentDescription().toString(), categories, SkillCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });

        btnSetSocialSkillCategory.setContentDescription(Constants.CONTENT_DESC_SKILL_SOCIAL);
        btnSetSocialSkillCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnSetSocialSkillCategory.getContentDescription().toString(), categories, SkillCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });
    }

    public int getLayout() {
        return R.layout.step_skill_categories;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_cat_skills_set);
    }

    @Override
    public void afterSettingRules(@Nullable Rule rule) {
        clearChoices();
        setButtonText(rule);
    }

    private void setButtonText(@Nullable Rule category) {
        StringBuilder builder = new StringBuilder();

        if (category != null) {
            builder.append(category.getName());

            switch (category.getContentDescription()) {
                case Constants.CONTENT_DESC_SKILL_MENTAL: {
                    txtSkillMental.setText(builder.toString());
                    txtSkillMental.setVisibility(View.VISIBLE);
                    mentalPoints = category.getValue();
                    break;
                }
                case Constants.CONTENT_DESC_SKILL_PHYSICAL: {
                    txtSkillPhysical.setText(builder.toString());
                    txtSkillPhysical.setVisibility(View.VISIBLE);
                    physicalPoints = category.getValue();
                    break;
                }
                case Constants.CONTENT_DESC_SKILL_SOCIAL: {
                    txtSkillSocial.setText(builder.toString());
                    txtSkillSocial.setVisibility(View.VISIBLE);
                    socialPoints = category.getValue();
                    break;
                }
            }
        }
        checkCompletionConditions();
    }

    public void checkCompletionConditions() {
        pagerMaster.checkStepIsComplete(!(hasDuplicateValues() || hasEmptyValues()), this);
    }

    public boolean hasDuplicateValues() {
        Object[] array = new Object[] { mentalPoints, physicalPoints, socialPoints };

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                final Object o1 = array[i];
                final Object o2 = array[j];

                if (o1 != null && o2 != null) {
                    if (o1.equals(o2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean hasEmptyValues() {
        return txtSkillMental.getText().equals("")
            || txtSkillPhysical.getText().equals("")
            || txtSkillSocial.getText().equals("");
    }

    @Override
    public HashMap<String, Object> saveChoices() {
        HashMap<String, Object> output = new HashMap<>();

        output.put(Constants.CONTENT_DESC_SKILL_MENTAL, mentalPoints);
        output.put(Constants.CONTENT_DESC_SKILL_PHYSICAL, physicalPoints);
        output.put(Constants.CONTENT_DESC_SKILL_SOCIAL, socialPoints);

        return output;
    }

    @Override
    public void clearChoices() {
        characterCreatorHelper
            .remove(Constants.CONTENT_DESC_SKILL_MENTAL)
            .remove(Constants.POOL_SKILL_MENTAL)
            .remove(Constants.CONTENT_DESC_SKILL_PHYSICAL)
            .remove(Constants.POOL_SKILL_PHYSICAL)
            .remove(Constants.CONTENT_DESC_SKILL_SOCIAL)
            .remove(Constants.POOL_SKILL_SOCIAL)
            .remove(Constants.SKILL_ACADEMICS)
            .remove(Constants.SKILL_ANIMAL_KEN)
            .remove(Constants.SKILL_ATHLETICS)
            .remove(Constants.SKILL_BRAWL)
            .remove(Constants.SKILL_COMPUTER)
            .remove(Constants.SKILL_CRAFTS)
            .remove(Constants.SKILL_DRIVE)
            .remove(Constants.SKILL_EMPATHY)
            .remove(Constants.SKILL_EXPRESSION)
            .remove(Constants.SKILL_FIREARMS)
            .remove(Constants.SKILL_INTIMIDATION)
            .remove(Constants.SKILL_INVESTIGATION)
            .remove(Constants.SKILL_LARCENY)
            .remove(Constants.SKILL_MEDICINE)
            .remove(Constants.SKILL_OCCULT)
            .remove(Constants.SKILL_PERSUASION)
            .remove(Constants.SKILL_POLITICS)
            .remove(Constants.SKILL_SCIENCE)
            .remove(Constants.SKILL_SOCIALIZE)
            .remove(Constants.SKILL_STEALTH)
            .remove(Constants.SKILL_STREETWISE)
            .remove(Constants.SKILL_SUBTERFUGE)
            .remove(Constants.SKILL_SURVIVAL)
            .remove(Constants.SKILL_WEAPONRY);
    }

    public void setPagerMaster(PagerMaster pagerMaster) {
        this.pagerMaster = pagerMaster;
    }
}
