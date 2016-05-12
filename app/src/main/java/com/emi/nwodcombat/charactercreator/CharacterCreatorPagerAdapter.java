package com.emi.nwodcombat.charactercreator;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;

import java.util.List;

/**
 * Created by Emi on 3/3/16.
 */
public class CharacterCreatorPagerAdapter extends FragmentStatePagerAdapter {
    private List<PagerStep> fragments;

    public CharacterCreatorPagerAdapter(FragmentManager fm, List<PagerStep> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
