package com.emi.nwodcombat.charactercreator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.activities.NavDrawerActivity;
import com.emi.nwodcombat.charactercreator.interfaces.PagerMaster;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;
import com.emi.nwodcombat.greendao.controllers.CharacterController;
import com.emi.nwodcombat.model.db.Character;

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
    CharacterController controller;

    @Bind(R.id.viewPager) ViewPager pager;
    @Bind(R.id.btnPrevious) Button btnPrevious;
    @Bind(R.id.btnNext) Button btnNext;

    public static CharacterCreatorPagerFragment newInstance(List<Fragment> fragments) {
        CharacterCreatorPagerFragment fragment = new CharacterCreatorPagerFragment();
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

    @Override
    public void commitChoices(Character character) {
        controller = CharacterController.getInstance();

        long result = controller.save(character);

        Log.d("Character creator", String.valueOf(result));

        ((NavDrawerActivity) getActivity()).onCharacterCreatorFinish();
    }

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
        Toast.makeText(getContext(), "Implement creator committing", Toast.LENGTH_SHORT).show();
    }
}
