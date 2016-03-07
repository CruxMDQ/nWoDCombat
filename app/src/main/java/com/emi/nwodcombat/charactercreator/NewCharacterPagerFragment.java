package com.emi.nwodcombat.charactercreator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.PagerMaster;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/3/16.
 */
public class NewCharacterPagerFragment extends Fragment implements PagerMaster {
    CharacterCreatorStatePagerAdapter adapter;
    List<Fragment> fragmentList;
    CharacterCreatorHelper characterCreatorHelper;

    @Bind(R.id.viewPager) ViewPager pager;
    @Bind(R.id.btnPrevious) Button btnPrevious;
    @Bind(R.id.btnNext) Button btnNext;

//    private boolean isStepComplete;

    public static NewCharacterPagerFragment newInstance(List<Fragment> fragments) {
        NewCharacterPagerFragment fragment = new NewCharacterPagerFragment();
        fragment.fragmentList = fragments;
        fragment.characterCreatorHelper = CharacterCreatorHelper.getInstance();
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
                // Disables step switching on swipe
                return true;
//                return !isStepComplete;
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToPreviousStep();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNextStep();
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
    public void checkStepIsComplete(final boolean isComplete, PagerStep caller) {
        if (isComplete) {
//            isStepComplete = isComplete;
            btnNext.setEnabled(true);
            btnPrevious.setEnabled(true);
            characterCreatorHelper.putAll(caller.saveChoices());
        } else {
            btnNext.setEnabled(false);
        }
    }

    public void moveToNextStep() {
        // TODO Setup logic for final step
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }

    public void moveToPreviousStep() {
        pager.setCurrentItem(pager.getCurrentItem() - 1);
    }
}
