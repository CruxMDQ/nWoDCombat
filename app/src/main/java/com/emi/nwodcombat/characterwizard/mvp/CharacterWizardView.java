package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterwizard.adapters.CharacterWizardPagerAdapter;
import com.emi.nwodcombat.characterwizard.steps.MeritsFragment;
import com.emi.nwodcombat.fragments.FragmentView;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by emiliano.desantis on 12/05/2016.
 */
public class CharacterWizardView extends FragmentView {
    private Bus bus;

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

    public void disablePagerSwitchOnSwiping() {
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
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int indexOfMeritsFragment = ((CharacterWizardPagerAdapter) adapter).getFragmentClasses()
                    .indexOf(MeritsFragment.class);

                if (pager.getCurrentItem() == indexOfMeritsFragment) {
                    // fire new event
                    bus.post(new Events.MeritsFragmentLoaded());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void toggleNextButton(boolean isStepComplete) {
        btnNext.setEnabled(isStepComplete);
    }

    public void setNextLabel(String label) {
        btnNext.setText(label);
    }

    public void setPreviousLabel(String label) {
        btnPrevious.setText(label);
    }

}
