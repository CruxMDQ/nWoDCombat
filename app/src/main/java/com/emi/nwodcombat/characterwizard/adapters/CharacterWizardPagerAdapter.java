package com.emi.nwodcombat.characterwizard.adapters;


import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.emi.nwodcombat.characterwizard.steps.PagerFragment;

import java.util.List;

/**
 * Created by Emi on 3/3/16.
 */
public class CharacterWizardPagerAdapter extends FragmentStatePagerAdapter {
    private List<Class<? extends PagerFragment>> fragmentClasses;

    public CharacterWizardPagerAdapter(FragmentManager fm, List<Class<? extends PagerFragment>> fragmentClasses) {
        super(fm);
        this.fragmentClasses = fragmentClasses;
    }

    @Override
    public PagerFragment getItem(int position) {
        try {
            return fragmentClasses.get(position).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getCount() {
        return this.fragmentClasses.size();
    }

    public List<Class<? extends PagerFragment>> getFragmentClasses() {
        return fragmentClasses;
    }

    public int indexOf(Object object) {
        return fragmentClasses.indexOf(object);
    }
}
