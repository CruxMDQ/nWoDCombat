package com.emi.nwodcombat.charactercreator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.emi.nwodcombat.R;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/3/16.
 */
public class NewCharacterPagerFragment extends Fragment implements PagerMaster {
    CharacterCreatorStatePagerAdapter adapter;
    List<Fragment> fragmentList;

//    HashMap<String, Object> values = new HashMap<>();

    @Bind(R.id.viewPager) ViewPager pager;

    private boolean isStepComplete;

    public static NewCharacterPagerFragment newInstance(List<Fragment> fragments) {
        NewCharacterPagerFragment fragment = new NewCharacterPagerFragment();
        fragment.fragmentList = fragments;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.adapter = new CharacterCreatorStatePagerAdapter(getChildFragmentManager(), fragmentList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        ButterKnife.bind(this, view);

        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return isStepComplete;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pager.setAdapter(adapter);
    }

    @Override
    public void onStepCompleted(final boolean isComplete, PagerStep caller) {
        isStepComplete = isComplete;
//        if (isComplete) {
//            values.putAll(caller.returnOutput());
//        }
    }
}
