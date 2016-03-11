package com.emi.nwodcombat.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Crux on 3/10/2016.
 */
public class SettingsFragment extends Fragment {
    private static SharedPreferences preferences;

    @Bind(R.id.chkCheat) CheckBox chkCheat;
    @Bind(R.id.chkIgnoreStatCaps) CheckBox chkIgnoreStatCaps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
            getLayout(), container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (SettingsFragment.preferences == null) {
            preferences = getContext().getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
        }

        setUpUI();
    }

    private void setUpUI() {
        chkCheat.setChecked(preferences.getBoolean(Constants.SETTING_CHEAT, false));
        chkCheat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Constants.SETTING_CHEAT, isChecked);
                editor.apply();
            }
        });

        chkIgnoreStatCaps.setChecked(preferences.getBoolean(Constants.SETTING_IGNORE_STAT_CAPS, false));
        chkIgnoreStatCaps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Constants.SETTING_IGNORE_STAT_CAPS, isChecked);
                editor.apply();
            }
        });
    }

    public int getLayout() {
        return R.layout.fragment_settings;
    }
}
