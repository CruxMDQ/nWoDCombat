package com.emi.nwodcombat.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.emi.nwodcombat.R;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by emiliano.desantis on 22/03/2016.
 */
public class FragmentView {
    private final Unbinder unbinder;
    private WeakReference<Fragment> fragmentRef;

    public FragmentView(Fragment fragment) {
        fragmentRef = new WeakReference<>(fragment);
        unbinder = ButterKnife.bind(this, fragment.getView());
    }

    @Nullable
    public Activity getActivity() {
        Fragment f = fragmentRef.get();
        return (f == null) ? null : f.getActivity();
    }

    @Nullable
    public Context getContext() {
        return getActivity();
    }

    @Nullable
    public FragmentManager getFragmentManager() {
        Activity activity = getActivity();
        return (activity != null) ? activity.getFragmentManager() : null;
    }

    @Nullable
    public FragmentManager getChildFragmentManager() {
        Fragment f = fragmentRef.get();
        return (f == null) ? null : f.getChildFragmentManager();
    }


    public void unbind() {
        unbinder.unbind();
    }

    @SuppressWarnings("ConstantConditions")
    public void setToolbarTitle(String title) {
        TextView txtToolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar).getRootView()
                .findViewById(R.id.txtToolbarTitle);

        txtToolbarTitle.setText(title);
    }
}