package com.rawat.ashish.game.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.fragments.HomeFragment;
import com.rawat.ashish.game.fragments.NetworkFragment;
import com.rawat.ashish.game.fragments.ProfileFragment;
import com.rawat.ashish.game.fragments.ReferAndEarnFragment;
import com.rawat.ashish.game.fragments.WalletFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        fragmentTransaction(new HomeFragment());
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            fragmentTransaction(fragment);
        } else if (id == R.id.nav_networks) {
            fragment = new NetworkFragment();
            fragmentTransaction(fragment);
        } else if (id == R.id.nav_wallet) {
            fragment = new WalletFragment();
            fragmentTransaction(fragment);
        } else if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
            fragmentTransaction(fragment);

        } else if (id == R.id.nav_refer_and_earn) {
            fragment = new ReferAndEarnFragment();
            fragmentTransaction(fragment);
        } else if (id == R.id.nav_logout) {
            sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(MyConstants.LOGGED_IN, false);
            sharedPreferencesEditor.apply();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fragmentTransaction(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_container, fragment);
            ft.commit();
        }
    }
}
