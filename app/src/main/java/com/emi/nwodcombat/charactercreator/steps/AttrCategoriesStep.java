package com.emi.nwodcombat.charactercreator.steps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.CategorySettingDialog;
import com.emi.nwodcombat.interfaces.AfterSettingRulesListener;
import com.emi.nwodcombat.model.pojos.Rule;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class AttrCategoriesStep extends WizardStep implements AfterSettingRulesListener {
    @ContextVariable private int mentalPoints;
    @ContextVariable private int physicalPoints;
    @ContextVariable private int socialPoints;

    @Bind(R.id.btnAttrMental) Button btnAttrMental;
    @Bind(R.id.btnAttrPhysical) Button btnAttrPhysical;
    @Bind(R.id.btnAttrSocial) Button btnAttrSocial;

    private ArrayList<Rule> categories;

    private SharedPreferences sharedPreferences;
    
    public AttrCategoriesStep() {
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
        btnAttrMental.setContentDescription(Constants.CONTENT_DESC_MENTAL);
        btnAttrMental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnAttrMental.getContentDescription().toString(), categories, AttrCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });
        
        btnAttrPhysical.setContentDescription(Constants.CONTENT_DESC_PHYSICAL);
        btnAttrPhysical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnAttrPhysical.getContentDescription().toString(), categories, AttrCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });
        
        btnAttrSocial.setContentDescription(Constants.CONTENT_DESC_SOCIAL);
        btnAttrSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySettingDialog dialog = CategorySettingDialog.newInstance(btnAttrSocial.getContentDescription().toString(), categories, AttrCategoriesStep.this);
                dialog.show(getActivity().getFragmentManager(), "Some tag");
            }
        });
    }

    @Override
    public void afterSettingRules(@Nullable Rule rule) {
        setButtonText(rule);
    }
    
    private void setButtonText(@Nullable Rule category) {
        StringBuilder builder = new StringBuilder();

        if (category != null) {
            builder.append(category.getContentDescription());
            builder.append(": ");
            builder.append(category.getName());

            switch (category.getContentDescription()) {
                case Constants.CONTENT_DESC_MENTAL: {
                    btnAttrMental.setText(builder.toString());
                    mentalPoints = category.getValue();
                    break;
                }
                case Constants.CONTENT_DESC_PHYSICAL: {
                    btnAttrPhysical.setText(builder.toString());
                    physicalPoints = category.getValue();
                    break;
                }
                case Constants.CONTENT_DESC_SOCIAL: {
                    btnAttrSocial.setText(builder.toString());
                    socialPoints = category.getValue();
                    break;
                }
            }
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
}
