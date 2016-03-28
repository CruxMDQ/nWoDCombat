package com.emi.nwodcombat;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

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
    public FragmentActivity getActivity() {
        Fragment f = fragmentRef.get();
        return (f == null) ? null : f.getActivity();
    }

    @Nullable
    public Context getContext() {
        return getActivity();
    }

    @Nullable
    public FragmentManager getFragmentManager() {
        FragmentActivity activity = getActivity();
        return (activity != null) ? activity.getSupportFragmentManager() : null;
    }

    public void unbind() {
        ButterKnife.unbind(fragmentRef.get());
    }
}