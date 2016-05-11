package com.emi.nwodcombat.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by emiliano.desantis on 22/03/2016.
 */
public class FragmentView {
    private WeakReference<Fragment> fragmentRef;

    public FragmentView(Fragment fragment) {
        fragmentRef = new WeakReference<>(fragment);
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

    public void unbind() {
        ButterKnife.unbind(fragmentRef.get());
    }
}