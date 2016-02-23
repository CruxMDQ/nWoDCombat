package com.emi.nwodcombat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.emi.nwodcombat.diceroller.mvp.CompositeDiceRollerFragment;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    CompositeDiceRollerFragment attackerFragment;
    CompositeDiceRollerFragment defenderFragment;

    @Bind(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v4.app.FragmentManager supportFragmentManager = getSupportFragmentManager();

        attackerFragment = (CompositeDiceRollerFragment) supportFragmentManager.findFragmentByTag(getString(R.string.fragment_attacker));
        defenderFragment = (CompositeDiceRollerFragment) supportFragmentManager.findFragmentByTag(getString(R.string.fragment_defender));

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> attackerRolls = attackerFragment.rollDice();
                ArrayList<Integer> defenderRolls = defenderFragment.rollDice();

                if (attackerRolls.isEmpty() || defenderRolls.isEmpty()) {
                    Snackbar.make(view, "One or both sets of dice are missing.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                } else {
                    StringBuilder attackerReport = new StringBuilder();

                    attackerReport.append("Attacker rolled: ");

                    Iterator a = attackerRolls.iterator();

                    while (a.hasNext()) {
                        Integer integer = (Integer) a.next();
                        attackerReport.append(String.valueOf(integer));
                        if (a.hasNext()) {
                            attackerReport.append(", ");
                        }
                    }

                    StringBuilder defenderReport = new StringBuilder();

                    defenderReport.append("Defender rolled: ");

                    Iterator d = defenderRolls.iterator();

                    while (d.hasNext()) {
                        Integer integer = (Integer) d.next();
                        defenderReport.append(String.valueOf(integer));
                        if (d.hasNext()) {
                            defenderReport.append(", ");
                        }
                    }

                    Snackbar.make(view, attackerReport.toString() + ". "
                        + defenderReport.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
