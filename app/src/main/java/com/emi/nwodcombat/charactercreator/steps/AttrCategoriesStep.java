package com.emi.nwodcombat.charactercreator.steps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.CategorySettingDialog;
import com.emi.nwodcombat.charactercreator.PagerMaster;
import com.emi.nwodcombat.charactercreator.PagerStep;
import com.emi.nwodcombat.interfaces.AfterSettingRulesListener;
import com.emi.nwodcombat.model.pojos.Rule;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class AttrCategoriesStep extends Fragment implements AfterSettingRulesListener, PagerStep {
    private int mentalPoints;
    private int physicalPoints;
    private int socialPoints;

    @Bind(R.id.btnSetMentalCategory) Button btnSetMentalCategory;
    @Bind(R.id.btnSetPhysicalCategory) Button btnSetPhysicalCategory;
    @Bind(R.id.btnSetSocialCategory) Button btnSetSocialCategory;
    @Bind(R.id.txtMental) TextView txtMental;
    @Bind(R.id.txtPhysical) TextView txtPhysical;
    @Bind(R.id.txtSocial) TextView txtSocial;

    private ArrayList<Rule> categories;

    private SharedPreferences sharedPreferences;

    private PagerMaster pagerMaster;

    public AttrCategoriesStep() {
    }

    public AttrCategoriesStep newInstance (PagerMaster listener) {
        AttrCategoriesStep fragment = new AttrCategoriesStep();
        fragment.pagerMaster = listener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_attr_categories, container, false);

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

    private void setUpUI() {
        btnSetMentalCategory.setContentDescription(Constants.CONTENT_DESC_MENTAL);
        btnSetMentalCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnSetMentalCategory.getContentDescription().toString(), categories, AttrCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });
        
        btnSetPhysicalCategory.setContentDescription(Constants.CONTENT_DESC_PHYSICAL);
        btnSetPhysicalCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnSetPhysicalCategory.getContentDescription().toString(), categories, AttrCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });
        
        btnSetSocialCategory.setContentDescription(Constants.CONTENT_DESC_SOCIAL);
        btnSetSocialCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnSetSocialCategory.getContentDescription().toString(), categories, AttrCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });
    }

    @Override
    public void afterSettingRules(@Nullable Rule rule) {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHAREDPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.CONTENT_DESC_MENTAL)
            .remove(Constants.MENTAL_POOL)
            .remove(Constants.CONTENT_DESC_PHYSICAL)
            .remove(Constants.PHYSICAL_POOL)
            .remove(Constants.CONTENT_DESC_SOCIAL)
            .remove(Constants.SOCIAL_POOL);
        editor.apply();
        setButtonText(rule);
    }
    
    private void setButtonText(@Nullable Rule category) {
        StringBuilder builder = new StringBuilder();

        if (category != null) {
            builder.append(category.getName());

            switch (category.getContentDescription()) {
                case Constants.CONTENT_DESC_MENTAL: {
                    txtMental.setText(builder.toString());
                    txtMental.setVisibility(View.VISIBLE);
                    mentalPoints = category.getValue();
                    break;
                }
                case Constants.CONTENT_DESC_PHYSICAL: {
                    txtPhysical.setText(builder.toString());
                    txtPhysical.setVisibility(View.VISIBLE);
                    physicalPoints = category.getValue();
                    break;
                }
                case Constants.CONTENT_DESC_SOCIAL: {
                    txtSocial.setText(builder.toString());
                    txtSocial.setVisibility(View.VISIBLE);
                    socialPoints = category.getValue();
                    break;
                }
            }
        }
        boolean hasDuplicateValues = hasDuplicateValues();
        pagerMaster.onStepCompleted(hasDuplicateValues, this);
        if (!hasDuplicateValues) {
            saveChoices();
        }
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

    public void saveChoices() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHAREDPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.CONTENT_DESC_MENTAL, mentalPoints);
        editor.putInt(Constants.CONTENT_DESC_PHYSICAL, physicalPoints);
        editor.putInt(Constants.CONTENT_DESC_SOCIAL, socialPoints);
        editor.apply();
    }

    public PagerMaster getPagerMaster() {
        return pagerMaster;
    }

    public void setPagerMaster(PagerMaster pagerMaster) {
        this.pagerMaster = pagerMaster;
    }

    @Override
    public HashMap<String, Object> returnOutput() {
        HashMap<String, Object> output = new HashMap<>();

        output.put(Constants.CONTENT_DESC_MENTAL, mentalPoints);
        output.put(Constants.CONTENT_DESC_PHYSICAL, physicalPoints);
        output.put(Constants.CONTENT_DESC_SOCIAL, socialPoints);

        return output;
    }
}
