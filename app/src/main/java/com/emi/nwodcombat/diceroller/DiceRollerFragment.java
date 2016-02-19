package com.emi.nwodcombat.diceroller;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiceRollerFragment extends Fragment implements
    DiceRollerContract.View {

    @Bind(R.id.txtAttackerDice) TextView txtAttackerDice;
    @Bind(R.id.txtDefenderDice) TextView txtDefenderDice;
    @Bind(R.id.panelAttackerDice) LinearLayout panelAttackerDice;
    @Bind(R.id.panelDefenderDice) LinearLayout panelDefenderDice;

    private DiceRollerContract.InputListener mActionListener;

    public DiceRollerFragment() {
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionListener = new DiceRollerPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        setUpUI();

        return view;
    }

    private void setUpUI() {
        txtAttackerDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.setAttackerDice(getActivity().getFragmentManager());
            }
        });

        txtDefenderDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.setDefenderDice(getActivity().getFragmentManager());
            }
        });
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void afterChoosingDice(String tag, int number) {
        LinearLayout activePanel = null;
        if (tag.equals(Constants.DICE_ATTACKER)) {
            activePanel = panelAttackerDice;
        } else if (tag.equals(Constants.DICE_DEFENDER)) {
            activePanel = panelDefenderDice;
        }

        if (activePanel != null) {
            activePanel.removeAllViews();
            for (int i = 0; i < number; i++) {
                RadioButton rdb = new RadioButton(getContext());

                rdb.setChecked(true);

                rdb.setButtonDrawable(getResources().getDrawable(R.drawable.selector_points));

                activePanel.addView(rdb);
            }
        } else throw new NullPointerException("View is null!");
    }

    @Override
    public void setInputListener(DiceRollerContract.InputListener listener) {
        mActionListener = listener;
    }
}
