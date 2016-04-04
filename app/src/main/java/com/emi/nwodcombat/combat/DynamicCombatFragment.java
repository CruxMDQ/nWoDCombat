package com.emi.nwodcombat.combat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.combat.dialogs.CombatDialog;
import com.emi.nwodcombat.combat.mvp.CombatantInfoFragment;
import com.emi.nwodcombat.model.pojos.Value;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/1/16.
 */
public class DynamicCombatFragment extends Fragment {
    @Bind(R.id.panelCombatants) LinearLayout panelCombatants;
    @Bind(R.id.fabDoCombat) FloatingActionButton fabDoCombat;

    private FragmentManager fragmentManager;

    public DynamicCombatFragment() {
    }
    
    public static DynamicCombatFragment newInstance() {
        DynamicCombatFragment fragment = new DynamicCombatFragment();
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combat_dynamic, container, false);

        ButterKnife.bind(this, view);
        
        generateSampleFragments();

        setUpFAB();
        
        return view;
    }

    private void setUpFAB() {
        fabDoCombat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int attackerPool = ((CombatantInfoFragment) fragmentManager.findFragmentByTag(getString(R.string.fragment_attacker))).getCombatantTotal();
                int attackerThreshold = ((CombatantInfoFragment) fragmentManager.findFragmentByTag(getString(R.string.fragment_attacker))).getCombatantThreshold();
                int defense = ((CombatantInfoFragment) fragmentManager.findFragmentByTag(getString(R.string.fragment_defender))).getCombatantTotal();

                CombatDialog dialog = CombatDialog.newInstance(attackerPool, attackerThreshold, defense);

                dialog.show(getActivity().getFragmentManager(), "tag");
            }
        });
    }

    private void generateSampleFragments() {
        fragmentManager = getActivity().getFragmentManager();
        
        CombatantInfoFragment attacker = CombatantInfoFragment.newInstance(generateSampleAttackValues());
        attacker.setCombatantTag(getString(R.string.fragment_attacker));
        fragmentManager.beginTransaction().add(R.id.panelCombatants, attacker, getString(R.string.fragment_attacker)).commit();

        CombatantInfoFragment defender = CombatantInfoFragment.newInstance(generateSampleDefenseValues());
        defender.setCombatantTag(getString(R.string.fragment_defender));
        fragmentManager.beginTransaction().add(R.id.panelCombatants, defender, getString(R.string.fragment_defender)).commit();
    }


    private ArrayList<Value> generateSampleAttackValues() {
        ArrayList<Value> result = new ArrayList<>();

        result.add(new Value("Attribute", 3));
        result.add(new Value("Skill", 2));
        result.add(new Value("Equipment", 2));

        return result;
    }

    private ArrayList<Value> generateSampleDefenseValues() {
        ArrayList<Value> result = new ArrayList<>();

        result.add(new Value("Attribute", 2));
        result.add(new Value("Armor", 2));

        return result;
    }

}
