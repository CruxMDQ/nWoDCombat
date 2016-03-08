package com.emi.nwodcombat.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.NewCharacterPagerFragment;
import com.emi.nwodcombat.charactercreator.steps.AttrCategoriesStep;
import com.emi.nwodcombat.charactercreator.steps.AttrSettingStep;
import com.emi.nwodcombat.charactercreator.steps.SetMentalSkillsStep;
import com.emi.nwodcombat.charactercreator.steps.SkillCategoriesStep;
import com.emi.nwodcombat.combat.DynamicCombatFragment;

import java.util.ArrayList;
import java.util.List;

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
        getSupportActionBar().setTitle(null);

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
            loadNewCharacterWizard();
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

    private void loadCombatFragment() {
        final DynamicCombatFragment dynamicCombatFragment = DynamicCombatFragment.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, dynamicCombatFragment).commit();
    }

    private void loadNewCharacterWizard() {
        List<Fragment> fragmentList = new ArrayList<>();

        AttrCategoriesStep attrCategoriesStep = new AttrCategoriesStep();
        AttrSettingStep attrSettingStep = new AttrSettingStep();
        SkillCategoriesStep skillCategoriesStep = new SkillCategoriesStep();
        SetMentalSkillsStep mentalSkillsStep = new SetMentalSkillsStep();

        final NewCharacterPagerFragment newCharacterPagerFragment = NewCharacterPagerFragment.newInstance(fragmentList);

        attrCategoriesStep.setPagerMaster(newCharacterPagerFragment);
        attrSettingStep.setPagerMaster(newCharacterPagerFragment);
        skillCategoriesStep.setPagerMaster(newCharacterPagerFragment);
        mentalSkillsStep.setPagerMaster(newCharacterPagerFragment);

        fragmentList.add(attrCategoriesStep);
        fragmentList.add(attrSettingStep);
        fragmentList.add(skillCategoriesStep);
        fragmentList.add(mentalSkillsStep);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, newCharacterPagerFragment).commit();
    }
}
