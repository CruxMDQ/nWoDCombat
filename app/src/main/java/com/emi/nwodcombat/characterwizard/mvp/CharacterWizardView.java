package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.adapters.CharacterWizardPagerAdapter;
import com.emi.nwodcombat.characterwizard.steps.AttrSettingFragment;
import com.emi.nwodcombat.characterwizard.steps.MeritsFragment;
import com.emi.nwodcombat.characterwizard.steps.PersonalInfoFragment;
import com.emi.nwodcombat.characterwizard.steps.SkillSettingFragment;
import com.emi.nwodcombat.characterwizard.steps.SummaryFragment;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by emiliano.desantis on 12/05/2016.
 */
@SuppressWarnings("WeakerAccess")
public class CharacterWizardView extends FragmentView {
    private final Bus bus;

    @BindView(R.id.viewPager) ViewPager pager;
    @BindView(R.id.btnPrevious) Button btnPrevious;
    @BindView(R.id.btnNext) Button btnNext;

    public CharacterWizardView(Fragment fragment, Bus instance) {
        super(fragment);
        this.bus = instance;
//        ButterKnife.bind(this, fragment.getView());
        disablePagerSwitchOnSwiping();
        btnPrevious.setEnabled(true);
    }

    private void disablePagerSwitchOnSwiping() {
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disables step switching on swipe
                return true;
            }
        });
    }

    @OnClick(R.id.btnPrevious)
    void onBtnPreviousClicked() {
        bus.post(new Events.WizardProgressEvent(pager.getCurrentItem(), false));
    }

    @OnClick(R.id.btnNext)
    void onBtnNextClicked() {
        bus.post(new Events.WizardProgressEvent(pager.getCurrentItem(), true));
    }

    @SuppressWarnings("ConstantConditions")
    public void pagerGoForward() {
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }

    @SuppressWarnings("ConstantConditions")
    public void pagerGoBackwards() {
        pager.setCurrentItem(pager.getCurrentItem() - 1);
    }

    public void setAdapter(final PagerAdapter adapter) {
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                CharacterWizardPagerAdapter pagerAdapter = (CharacterWizardPagerAdapter) adapter;

                final int indexOfInfoFragment = pagerAdapter.indexOf(PersonalInfoFragment.class);
                final int indexOfAttrsFragment = pagerAdapter.indexOf(AttrSettingFragment.class);
                final int indexOfSkillsFragment = pagerAdapter.indexOf(SkillSettingFragment.class);
                final int indexOfMeritsFragment = pagerAdapter.indexOf(MeritsFragment.class);
                final int indexOfSummaryFragment = pagerAdapter.indexOf(SummaryFragment.class);

                if (pager.getCurrentItem() == indexOfInfoFragment) {
                    bus.post(new Events.InfoFragmentLoaded());
                } else if (pager.getCurrentItem() == indexOfAttrsFragment) {
                    bus.post(new Events.AttrsFragmentLoaded());
                } else if (pager.getCurrentItem() == indexOfSkillsFragment) {
                    bus.post(new Events.SkillsFragmentLoaded());
                } else if (pager.getCurrentItem() == indexOfMeritsFragment) {// fire new event
                    bus.post(new Events.MeritsFragmentLoaded());
                } else if (pager.getCurrentItem() == indexOfSummaryFragment) {
                    bus.post(new Events.WizardComplete());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    void toggleNextButton(boolean isStepComplete) {
        btnNext.setEnabled(isStepComplete);
    }

    void setNextLabel(String label) {
        btnNext.setText(label);
    }

    void setPreviousLabel(String label) {
        btnPrevious.setText(label);
    }

}
