package com.emi.nwodcombat.charactercreator;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.PagerFinisher;
import com.emi.nwodcombat.charactercreator.interfaces.PagerMaster;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/3/16.
 */
public class CharacterCreatorPagerFragment extends Fragment implements PagerMaster {
    CharacterCreatorStatePagerAdapter adapter;
    List<Fragment> fragmentList;
    CharacterCreatorHelper characterCreatorHelper;
//    CharacterController controller;
    PagerFinisher pagerFinisher;

    @Bind(R.id.viewPager) ViewPager pager;
    @Bind(R.id.btnPrevious) Button btnPrevious;
    @Bind(R.id.btnNext) Button btnNext;

    public static CharacterCreatorPagerFragment newInstance(List<Fragment> fragments, PagerFinisher pagerFinisher) {
        CharacterCreatorPagerFragment fragment = new CharacterCreatorPagerFragment();
        fragment.characterCreatorHelper = CharacterCreatorHelper.getInstance();
        fragment.fragmentList = fragments;
        fragment.pagerFinisher = pagerFinisher;
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
                int lastPage = pager.getAdapter().getCount() - 1;
                if (pager.getCurrentItem() < lastPage) {
                    moveToNextStep();
                } else {
                    finishWizard();
                }
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
            btnNext.setEnabled(true);
            btnPrevious.setEnabled(true);
            characterCreatorHelper.putAll(caller.saveChoices());
        } else {
            btnNext.setEnabled(false);
        }
    }

//    @Override
//    public long commitChoices(Character character) {
//        controller = CharacterController.getInstance(getContext());
//
//        long result = controller.save(character);
//
//        Log.d("Character creator", String.valueOf(result));
//
//        ((NavDrawerActivity) getActivity()).onCharacterCreatorFinish();
//
//        return result;
//    }

    public void moveToNextStep() {
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        int lastPage = pager.getAdapter().getCount() - 1;

        if (pager.getCurrentItem() == lastPage) {
            btnNext.setText(getString(R.string.button_finish));
        } else {
            btnNext.setText(getString(R.string.button_next));
        }
    }

    public void moveToPreviousStep() {
        if (pager.getCurrentItem() != 0) {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void finishWizard() {
        pagerFinisher.onPagerFinished();
    }
}
