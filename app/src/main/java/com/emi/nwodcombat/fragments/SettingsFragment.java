package com.emi.nwodcombat.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.tools.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Crux on 3/10/2016.
 */
public class SettingsFragment extends Fragment {
    private Unbinder unbinder;

    private SharedPreferences preferences;

    @BindView(R.id.chkCheat) CheckBox chkCheat;
    @BindView(R.id.chkIgnoreStatCaps) CheckBox chkIgnoreStatCaps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
            getLayout(), container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = getActivity().getSharedPreferences(Constants.TAG_SHARED_PREFS, Context.MODE_PRIVATE);

        setUpUI();
    }

    private void setUpUI() {
        setToolbarTitle(getToolbarTitle());

        chkCheat.setChecked(preferences.getBoolean(Constants.SETTING_CHEAT, false));
        chkCheat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Constants.SETTING_CHEAT, isChecked);
                editor.apply();
            }
        });

        chkIgnoreStatCaps.setChecked(preferences.getBoolean(Constants.SETTING_IGNORE_STAT_CAPS, false));
        chkIgnoreStatCaps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Constants.SETTING_IGNORE_STAT_CAPS, isChecked);
                editor.apply();
            }
        });
    }

    private int getLayout() {
        return R.layout.fragment_settings;
    }

    private void setToolbarTitle(String title) {
        TextView txtToolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar).getRootView().findViewById(R.id.txtToolbarTitle);

        txtToolbarTitle.setText(title);
    }

    private String getToolbarTitle() {
        return getString(R.string.title_settings);
    }
}
