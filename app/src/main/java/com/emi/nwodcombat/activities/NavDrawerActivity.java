package com.emi.nwodcombat.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.R;
import com.emi.nwodcombat.charactercreator.CharacterCreatorPagerFragment;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;
import com.emi.nwodcombat.charactercreator.steps.AttrCategoriesStep;
import com.emi.nwodcombat.charactercreator.steps.AttrSettingStep;
import com.emi.nwodcombat.charactercreator.steps.PersonalInfoStep;
import com.emi.nwodcombat.charactercreator.steps.SkillCategoriesStep;
import com.emi.nwodcombat.charactercreator.steps.SkillsSetMentalStep;
import com.emi.nwodcombat.charactercreator.steps.SkillsSetPhysicalStep;
import com.emi.nwodcombat.charactercreator.steps.SkillsSetSocialStep;
import com.emi.nwodcombat.charactercreator.steps.SummaryStep;
import com.emi.nwodcombat.characterlist.CharacterListFragment;
import com.emi.nwodcombat.combat.DynamicCombatFragment;
import com.emi.nwodcombat.fragments.SettingsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NavDrawerActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar) Toolbar toolbar;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(null);

        realm = Realm.getInstance(this);

        loadCharacterList();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            loadSettingsFragment();
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
        } else if (id == R.id.nav_gallery) {
            loadCharacterList();
        } else if (id == R.id.nav_slideshow) {
            loadCombatFragment();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadCharacterList() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentById(R.id.flContent) instanceof CharacterListFragment) {
            return;
        }
        fragmentManager.beginTransaction().replace(R.id.flContent,  CharacterListFragment.newInstance()).commit();
    }

    private void loadCombatFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentById(R.id.flContent) instanceof DynamicCombatFragment) {
            return;
        }
        fragmentManager.beginTransaction().replace(R.id.flContent, DynamicCombatFragment.newInstance()).commit();
    }

    private void loadNewCharacterWizard() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentById(R.id.flContent) instanceof CharacterCreatorPagerFragment) {
            return;
        }

        List<PagerStep> fragmentList = new ArrayList<>();

        PersonalInfoStep personalInfoStep = new PersonalInfoStep();
        AttrCategoriesStep attrCategoriesStep = new AttrCategoriesStep();
        AttrSettingStep attrSettingStep = new AttrSettingStep();
        SkillCategoriesStep skillCategoriesStep = new SkillCategoriesStep();
        SkillsSetMentalStep mentalSkillsStep = new SkillsSetMentalStep();
        SkillsSetPhysicalStep physicalSkillsStep = new SkillsSetPhysicalStep();
        SkillsSetSocialStep socialSkillsStep = new SkillsSetSocialStep();
        SummaryStep summaryStep = new SummaryStep();

        final CharacterCreatorPagerFragment characterCreatorPagerFragment = CharacterCreatorPagerFragment.newInstance(fragmentList, summaryStep);

        personalInfoStep.setPagerMaster(characterCreatorPagerFragment);
        attrCategoriesStep.setPagerMaster(characterCreatorPagerFragment);
        attrSettingStep.setPagerMaster(characterCreatorPagerFragment);
        skillCategoriesStep.setPagerMaster(characterCreatorPagerFragment);
        mentalSkillsStep.setPagerMaster(characterCreatorPagerFragment);
        physicalSkillsStep.setPagerMaster(characterCreatorPagerFragment);
        socialSkillsStep.setPagerMaster(characterCreatorPagerFragment);
        summaryStep.setPagerMaster(characterCreatorPagerFragment);

        fragmentList.add(personalInfoStep);
        fragmentList.add(attrCategoriesStep);
        fragmentList.add(attrSettingStep);
        fragmentList.add(skillCategoriesStep);
        fragmentList.add(mentalSkillsStep);
        fragmentList.add(physicalSkillsStep);
        fragmentList.add(socialSkillsStep);
        fragmentList.add(summaryStep);

        fragmentManager.beginTransaction().replace(R.id.flContent, characterCreatorPagerFragment).addToBackStack(Constants.TAG_FRAG_CHARACTER_CREATOR_PAGER).commit();
    }

    private void loadSettingsFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new SettingsFragment())
                .addToBackStack(Constants.TAG_FRAG_SETTINGS).commit();
    }

    public void onCharacterCreatorFinish() {
        clearBackStack();
        loadCombatFragment();
    }

    private void clearBackStack() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStackImmediate(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    protected void setToolbarTitle(String title) {
        TextView txtToolbarTitle = (TextView) findViewById(R.id.toolbar).getRootView().findViewById(R.id.txtToolbarTitle);

        txtToolbarTitle.setText(title);
    }

    public String getToolbarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
