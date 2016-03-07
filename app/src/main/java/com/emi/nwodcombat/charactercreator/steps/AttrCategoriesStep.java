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
 * Created by Emi on 3/1/16.
 */
public class AttrCategoriesStep extends WizardStep implements PagerStep.ParentStep, AfterSettingRulesListener{
    private int mentalPoints;
    private int physicalPoints;
    private int socialPoints;

    @Bind(R.id.btnSetMentalAttrCategory) Button btnSetMentalAttrCategory;
    @Bind(R.id.btnSetPhysicalAttrCategory) Button btnSetPhysicalAttrCategory;
    @Bind(R.id.btnSetSocialAttrCategory) Button btnSetSocialAttrCategory;
    @Bind(R.id.txtAttrMental) TextView txtAttrMental;
    @Bind(R.id.txtAttrPhysical) TextView txtAttrPhysical;
    @Bind(R.id.txtAttrSocial) TextView txtAttrSocial;

    private ArrayList<Rule> categories;

    public AttrCategoriesStep() {
        super();
    }

    public WizardStep newInstance (PagerMaster listener) {
        WizardStep fragment = new AttrCategoriesStep();
        fragment.pagerMaster = listener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);

        setToolbarTitle(container, getToolbarTitle());

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
    public void afterSettingRules(@Nullable Rule rule) {
        clearChoices();
        setButtonText(rule);
    }

    @Override
    public void clearChoices() {
        characterCreatorHelper
            .remove(Constants.CONTENT_DESC_ATTR_MENTAL)
            .remove(Constants.MENTAL_POOL)
            .remove(Constants.CONTENT_DESC_ATTR_PHYSICAL)
            .remove(Constants.PHYSICAL_POOL)
            .remove(Constants.CONTENT_DESC_ATTR_SOCIAL)
            .remove(Constants.SOCIAL_POOL)
            .remove(Constants.ATTR_INT)
            .remove(Constants.ATTR_WIT)
            .remove(Constants.ATTR_RES)
            .remove(Constants.ATTR_STR)
            .remove(Constants.ATTR_DEX)
            .remove(Constants.ATTR_STA)
            .remove(Constants.ATTR_PRE)
            .remove(Constants.ATTR_MAN)
            .remove(Constants.ATTR_COM);
    }

    @Override
    public void checkCompletionConditions() {
        pagerMaster.checkStepIsComplete(!(hasDuplicateValues() || hasEmptyValues()), this);
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_cat_attrs_set);
    }

    public int getLayout() {
        return R.layout.step_attr_categories;
    }

    @Override
    public HashMap<String, Object> saveChoices() {
        HashMap<String, Object> output = new HashMap<>();

        output.put(Constants.CONTENT_DESC_ATTR_MENTAL, mentalPoints);
        output.put(Constants.CONTENT_DESC_ATTR_PHYSICAL, physicalPoints);
        output.put(Constants.CONTENT_DESC_ATTR_SOCIAL, socialPoints);

        return output;
    }

    @Override
    protected void setUpUI() {
        btnSetMentalAttrCategory.setContentDescription(Constants.CONTENT_DESC_ATTR_MENTAL);
        btnSetMentalAttrCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnSetMentalAttrCategory.getContentDescription().toString(), categories, AttrCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });

        btnSetPhysicalAttrCategory.setContentDescription(Constants.CONTENT_DESC_ATTR_PHYSICAL);
        btnSetPhysicalAttrCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnSetPhysicalAttrCategory.getContentDescription().toString(), categories, AttrCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });

        btnSetSocialAttrCategory.setContentDescription(Constants.CONTENT_DESC_ATTR_SOCIAL);
        btnSetSocialAttrCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnSetSocialAttrCategory.getContentDescription().toString(), categories, AttrCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });
    }

    private void setButtonText(@Nullable Rule category) {
        StringBuilder builder = new StringBuilder();

        if (category != null) {
            builder.append(category.getName());

            switch (category.getContentDescription()) {
                case Constants.CONTENT_DESC_ATTR_MENTAL: {
                    txtAttrMental.setText(builder.toString());
                    txtAttrMental.setVisibility(View.VISIBLE);
                    mentalPoints = category.getValue();
                    break;
                }
                case Constants.CONTENT_DESC_ATTR_PHYSICAL: {
                    txtAttrPhysical.setText(builder.toString());
                    txtAttrPhysical.setVisibility(View.VISIBLE);
                    physicalPoints = category.getValue();
                    break;
                }
                case Constants.CONTENT_DESC_ATTR_SOCIAL: {
                    txtAttrSocial.setText(builder.toString());
                    txtAttrSocial.setVisibility(View.VISIBLE);
                    socialPoints = category.getValue();
                    break;
                }
            }
        }
        checkCompletionConditions();
    }

    private ArrayList<Rule> generateCategories() {
        ArrayList<Rule> rules = new ArrayList<>();

        rules.add(new Rule(Constants.CATEGORY_PRIMARY, false, Constants.ATTR_PTS_PRIMARY));
        rules.add(new Rule(Constants.CATEGORY_SECONDARY, false, Constants.ATTR_PTS_SECONDARY));
        rules.add(new Rule(Constants.CATEGORY_TERTIARY, false, Constants.ATTR_PTS_TERTIARY));

        return rules;
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
        return txtAttrMental.getText().equals("")
            || txtAttrPhysical.getText().equals("")
            || txtAttrSocial.getText().equals("");
    }
}
