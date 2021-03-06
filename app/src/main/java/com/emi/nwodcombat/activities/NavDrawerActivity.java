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

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.characterlist.CharacterListFragment;
import com.emi.nwodcombat.characterwizard.CharacterWizardFragment;
import com.emi.nwodcombat.combat.DynamicCombatFragment;
import com.emi.nwodcombat.fragments.SettingsFragment;
import com.emi.nwodcombat.tools.BusProvider;
import com.emi.nwodcombat.tools.Constants;
import com.emi.nwodcombat.tools.Events;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NavDrawerActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    private ActionBarDrawerToggle toggle;

    private Bus bus;

//    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ButterKnife.bind(this);
//        unbinder = ButterKnife.bind(this);

        bus = BusProvider.getInstance();
        bus.register(this);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        loadCharacterList();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unbinder.unbind();
//    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
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
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_camera:
                loadNewCharacterWizard();
                break;
            case R.id.nav_gallery:
                loadCharacterList();
                break;
            case R.id.nav_slideshow:
                loadCombatFragment();
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:

                break;
        }
        return true;
    }

    private void loadCharacterList() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentById(R.id.flContent) instanceof CharacterListFragment) {
            return;
        }
        fragmentManager.beginTransaction().replace(R.id.flContent,  CharacterListFragment.newInstance()).addToBackStack(null).commit();
    }

    private void loadCombatFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentById(R.id.flContent) instanceof DynamicCombatFragment) {
            return;
        }
        fragmentManager.beginTransaction().replace(R.id.flContent, DynamicCombatFragment.newInstance()).addToBackStack(null).commit();
    }

    //VSM this is memory consuming, use a better approach. Maybe a ViewPager is the best option.
    private void loadNewCharacterWizard() {
        CharacterWizardFragment fragment = new CharacterWizardFragment();

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentById(R.id.flContent) instanceof CharacterWizardFragment) {
            return;
        }

        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(
            Constants.TAG_FRAG_CHARACTER_CREATOR_PAGER).commit();
    }

    private void loadSettingsFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new SettingsFragment())
                .addToBackStack(Constants.TAG_FRAG_SETTINGS).commit();
    }

    public void onCharacterCreatorFinish() {
        loadCharacterList();
    }

    public String getToolbarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Subscribe
    public void onCharacterCreatorWizardClosing(Events.WizardClose event) {
        loadCharacterList();
    }
}
