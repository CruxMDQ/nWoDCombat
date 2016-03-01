package com.emi.nwodcombat.activities;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.combat.CombatDialog;
import com.emi.nwodcombat.combat.CombatFragment;
import com.emi.nwodcombat.model.pojos.Value;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NavDrawerActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        loadCombatFragment();

//        setUpFAB();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    private void setUpFAB() {
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//            }
//        });
//    }

    private void loadCombatFragment() {
        fab.setImageDrawable(getDrawable(R.drawable.die));
        final CombatFragment combatFragment = CombatFragment.newInstance(generateSampleAttackValues(), generateSampleDefenseValues());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CombatDialog dialog = CombatDialog.newInstance(combatFragment.getAttackerTotal(), 10, combatFragment.getDefenderTotal());

                dialog.show(getFragmentManager(), "tag");
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, combatFragment).commit();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
