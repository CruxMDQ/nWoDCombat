package com.emi.nwodcombat.charactercreator.mvp;

import android.app.Fragment;
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
public class CharacterCreatorView extends FragmentView {
    private Bus bus;

    @Bind(R.id.viewPager) ViewPager pager;
    @Bind(R.id.btnPrevious) Button btnPrevious;
    @Bind(R.id.btnNext) Button btnNext;

    public CharacterCreatorView(Fragment fragment, Bus instance) {
        super(fragment);
        this.bus = instance;
        ButterKnife.bind(this, fragment.getView());

        disablePagerSwitchOnSwiping();
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
        bus.post(new WizardProgressEvent(pager.getCurrentItem(), false));
    }

    @OnClick(R.id.btnNext)
    void onBtnNextClicked() {
        bus.post(new WizardProgressEvent(pager.getCurrentItem(), true));
    }

    public void pagerGoForward() {
        pager.setCurrentItem(pager.getCurrentItem() + 1);

        // Get last page
        int lastPage = pager.getAdapter().getCount() - 1;

        // Change button label depending on where on the wizard we are
        if (pager.getCurrentItem() == lastPage) {
            btnNext.setText(getActivity().getString(R.string.button_finish));
        } else {
            btnNext.setText(getActivity().getString(R.string.button_next));
        }
    }

    public void pagerGoBackwards() {
        // If this is not the first page, go back one step
        if (pager.getCurrentItem() != 0) {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        } else {
        // If yes, then remove the fragment altogether from the view
            getFragmentManager().popBackStack();
        }
    }

    public static class WizardProgressEvent {
        final int currentItem;
        final boolean isProgressing;

        public WizardProgressEvent(int currentItem, boolean isProgressing) {
            this.currentItem = currentItem;
            this.isProgressing = isProgressing;
        }
    }
}
