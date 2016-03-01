package com.emi.nwodcombat.combat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.pojos.Value;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 2/29/16.
 * As either attacker or defender may have several different modifiers, instead of creating
 * static fields for them, they are loaded on recycler views.
 */
public class CombatFragment extends Fragment {

    @Bind(R.id.rvAttackerValues) RecyclerView rvAttackerValues;
    @Bind(R.id.rvDefenderValues) RecyclerView rvDefenderValues;
    @Bind(R.id.btnSetAttackerThreshold) Button btnSetAttackerThreshold;
    @Bind(R.id.btnSetDefenderThreshold) Button btnSetDefenderThreshold;
    
    private ArrayList<Value> attackerValues;
    private ArrayList<Value> defenderValues;

    public CombatFragment() {
    }

    public static CombatFragment newInstance(ArrayList<Value> attackerModifiers, ArrayList<Value> defenderModifiers) {
        CombatFragment fragment = new CombatFragment();
        fragment.attackerValues = attackerModifiers;
        fragment.defenderValues = defenderModifiers;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combat, container, false);

        ButterKnife.bind(this, view);

        setUpUI();

        return view;
    }

    private void setUpUI() {
        final ValuesAdapter attackerValuesAdapter = new ValuesAdapter<>(this.attackerValues, getActivity());
        final ValuesAdapter defenderValuesAdapter = new ValuesAdapter<>(this.defenderValues, getActivity());

        rvAttackerValues.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAttackerValues.setAdapter(attackerValuesAdapter);

        rvDefenderValues.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDefenderValues.setAdapter(defenderValuesAdapter);

        btnSetAttackerThreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSetDefenderThreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public int getAttackerTotal() {
        return ((ValuesAdapter) rvAttackerValues.getAdapter()).getSum();
    }

    public int getDefenderTotal() {
        return ((ValuesAdapter) rvDefenderValues.getAdapter()).getSum();
    }
}
