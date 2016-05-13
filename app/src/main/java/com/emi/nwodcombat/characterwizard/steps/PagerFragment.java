package com.emi.nwodcombat.characterwizard.steps;

import android.app.Fragment;
import android.widget.TextView;

import com.emi.nwodcombat.charactercreator.interfaces.PagerMaster;

import java.util.HashMap;

/**
 * Created by Emi on 3/7/16.
 */
public abstract class PagerFragment extends Fragment {
    protected PagerMaster pagerMaster;
//    protected CharacterCreatorHelper characterCreatorHelper;

    public PagerFragment() {
//        characterCreatorHelper = CharacterCreatorHelper.getInstance();
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setToolbarTitle(getToolbarTitle());
//    }

//    protected void setToolbarTitle(String title) {
//        TextView txtToolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar).getRootView().findViewById(R.id.txtToolbarTitle);
//
//        txtToolbarTitle.setText(title);
//    }

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

    public abstract boolean checkCompletionConditions();

    public abstract HashMap<String, Object> saveChoices();

    public abstract String getToolbarTitle();

    public abstract int getLayout();

    public void setPagerMaster(PagerMaster pagerMaster) {
        this.pagerMaster = pagerMaster;
    }

}
