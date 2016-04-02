package com.emi.nwodcombat.charactercreator.steps;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.CharacterCreatorHelper;
import com.emi.nwodcombat.charactercreator.interfaces.PagerMaster;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;

import java.util.HashMap;

/**
 * Created by Emi on 3/7/16.
 */
public abstract class WizardStep extends Fragment implements PagerStep {
    protected PagerMaster pagerMaster;
    protected CharacterCreatorHelper characterCreatorHelper;

    public WizardStep() {
        characterCreatorHelper = CharacterCreatorHelper.getInstance();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarTitle(getToolbarTitle());
    }

    protected void setToolbarTitle(String title) {
        TextView txtToolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar).getRootView().findViewById(R.id.txtToolbarTitle);

        txtToolbarTitle.setText(title);
    }

    protected void setPoolTitle(String titleString, int pool, TextView textView) {
        String text;
        if (pool > 0) {
            text = titleString +
                    " (" +
                    pool +
                    " points remaining)";
        } else {
            text = titleString + " (no points remaining)";
        }
        textView.setText(text);
    }

    protected abstract void setUpUI();

    public abstract void checkCompletionConditions();

    public abstract HashMap<String, Object> saveChoices();

    public abstract String getToolbarTitle();

    public abstract int getLayout();

    public void setPagerMaster(PagerMaster pagerMaster) {
        this.pagerMaster = pagerMaster;
    }

}
