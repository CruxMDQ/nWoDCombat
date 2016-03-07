package com.emi.nwodcombat.charactercreator.steps;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;
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

    protected void setToolbarTitle(ViewGroup container, String title) {
        TextView txtToolbarTitle = (TextView) container.getRootView().findViewById(R.id.toolbar).getRootView().findViewById(R.id.txtToolbarTitle);

        txtToolbarTitle.setText(title);
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
