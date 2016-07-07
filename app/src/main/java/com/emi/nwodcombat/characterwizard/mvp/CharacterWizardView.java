package com.emi.nwodcombat.characterwizard.mvp;

import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.fragments.FragmentView;
import com.squareup.otto.Bus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by emiliano.desantis on 12/05/2016.
 */
public class CharacterWizardView extends FragmentView {
    private final Bus bus;

    @Bind(R.id.viewPager) ViewPager pager;
    @Bind(R.id.btnPrevious) Button btnPrevious;
    @Bind(R.id.btnNext) Button btnNext;

    public CharacterWizardView(Fragment fragment, Bus instance) {
        super(fragment);
        this.bus = instance;
        ButterKnife.bind(this, fragment.getView());
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
        bus.post(new WizardProgressEvent(pager.getCurrentItem(), false));
    }

    @OnClick(R.id.btnNext)
    void onBtnNextClicked() {
        bus.post(new WizardProgressEvent(pager.getCurrentItem(), true));
    }

    @SuppressWarnings("ConstantConditions")
    public void pagerGoForward() {
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }

    @SuppressWarnings("ConstantConditions")
    public void pagerGoBackwards() {
        pager.setCurrentItem(pager.getCurrentItem() - 1);
    }

    public void setAdapter(PagerAdapter adapter) {
        pager.setAdapter(adapter);
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

    public static class WizardProgressEvent {
        final int currentItem;
        final boolean movesForward;

        public WizardProgressEvent(int currentItem, boolean movesForward) {
            this.currentItem = currentItem;
            this.movesForward = movesForward;
        }
    }
}
