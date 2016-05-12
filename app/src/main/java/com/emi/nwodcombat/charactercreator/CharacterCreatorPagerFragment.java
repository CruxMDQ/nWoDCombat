package com.emi.nwodcombat.charactercreator;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.interfaces.PagerFinisher;
import com.emi.nwodcombat.charactercreator.interfaces.PagerMaster;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;
import com.emi.nwodcombat.charactercreator.mvp.CharacterCreatorModel;
import com.emi.nwodcombat.charactercreator.mvp.CharacterCreatorPresenter;
import com.emi.nwodcombat.charactercreator.mvp.CharacterCreatorView;
import com.emi.nwodcombat.utils.BusProvider;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/3/16.
 */
public class CharacterCreatorPagerFragment extends Fragment implements PagerMaster {
//    CharacterCreatorPagerAdapter adapter;
    List<PagerStep> fragmentList;
    CharacterCreatorHelper characterCreatorHelper;
    PagerFinisher pagerFinisher;

    @Bind(R.id.viewPager) ViewPager pager;
    @Bind(R.id.btnPrevious) Button btnPrevious;
    @Bind(R.id.btnNext) Button btnNext;

    private CharacterCreatorPresenter presenter;

    public static CharacterCreatorPagerFragment newInstance(List<PagerStep> fragments, PagerFinisher pagerFinisher) {
        CharacterCreatorPagerFragment fragment = new CharacterCreatorPagerFragment();
        fragment.characterCreatorHelper = CharacterCreatorHelper.getInstance();
        fragment.fragmentList = fragments;
        fragment.pagerFinisher = pagerFinisher;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.register(presenter);
    }

    @Override
    public void onPause() {
        BusProvider.unregister(presenter);
        super.onPause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, container, false);

//        ButterKnife.bind(this, view);

//        pager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // Disables step switching on swipe
//                return true;
//            }
//        });

//        btnPrevious.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                moveToPreviousStep();
//            }
//        });

//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int lastPage = pager.getAdapter().getCount() - 1;
//                if (pager.getCurrentItem() < lastPage) {
//                    moveToNextStep();
//                } else {
//                    finishWizard();
//                }
//            }
//        });
//
//        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//        setHasOptionsMenu(true);
//        this.adapter = new CharacterCreatorPagerAdapter(getChildFragmentManager(), fragmentList);
//    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        pager.setAdapter(adapter);
//    }


    @Override
    public void checkStepIsComplete(final boolean isComplete, PagerStep caller) {
        if (isComplete) {
            btnNext.setEnabled(true);
            btnPrevious.setEnabled(true);
//            characterCreatorHelper.putAll(caller.saveChoices());
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

    private void createPresenter() {
        presenter = new CharacterCreatorPresenter(getChildFragmentManager(), new CharacterCreatorModel(getActivity()), new CharacterCreatorView(this,
            BusProvider.getInstance()));
        presenter.setUpView();
    }

//    public void moveToNextStep() {
//        characterCreatorHelper.putAll(fragmentList.get(pager.getCurrentItem()).saveChoices());
//
//        pager.setCurrentItem(pager.getCurrentItem() + 1);
//        int lastPage = pager.getAdapter().getCount() - 1;
//
//        if (fragmentList.get(pager.getCurrentItem()) instanceof PagerStep.ChildStep) {
//            ((PagerStep.ChildStep) fragmentList.get(pager.getCurrentItem())).retrieveChoices();
//        }
//
//        if (pager.getCurrentItem() == lastPage) {
//            btnNext.setText(getString(R.string.button_finish));
//        } else {
//            btnNext.setText(getString(R.string.button_next));
//        }
//    }
//
//    public void moveToPreviousStep() {
//        if (pager.getCurrentItem() != 0) {
//            pager.setCurrentItem(pager.getCurrentItem() - 1);
//        } else {
//            getFragmentManager().popBackStack();
//        }
//    }

    private void finishWizard() {
        pagerFinisher.onPagerFinished();
    }

}
