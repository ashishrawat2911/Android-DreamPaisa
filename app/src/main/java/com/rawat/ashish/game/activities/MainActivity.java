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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.fragments.AdvertiseWithUsFragment;
import com.rawat.ashish.game.fragments.BestOffersFragment;
import com.rawat.ashish.game.fragments.HomeFragment;
import com.rawat.ashish.game.fragments.HomeMainFragment;
import com.rawat.ashish.game.fragments.MyOrderFragment;
import com.rawat.ashish.game.fragments.NetworkFragment;
import com.rawat.ashish.game.fragments.NotificationFragment;
import com.rawat.ashish.game.fragments.SupportFragment;
import com.rawat.ashish.game.model.UserDetails;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeMainFragment.OnButtonClickListener {
    Fragment fragment = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    TextView username, userId, number;
    ProgressBar progressBar;
    APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAPIService = APIClient.getClient().create(APIService.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userId = headerView.findViewById(R.id.nav_header_userId);
        username = headerView.findViewById(R.id.nav_header_userName);
        number = headerView.findViewById(R.id.nav_header_userNumber);
        progressBar = headerView.findViewById(R.id.nav_header_progressBar);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        fragmentTransaction(new HomeFragment());
        if (sharedPreferences.getInt(MyConstants.LOAD_USER_PROFILE, 0) == 1) {
            setUserProfile();
        } else loadProfileData();
    }

    private void setUserProfile() {
        userId.setVisibility(View.VISIBLE);
        username.setVisibility(View.VISIBLE);
        number.setVisibility(View.VISIBLE);
        userId.setText("User Id : " + sharedPreferences.getString(MyConstants.USER_ID, ""));
        username.setText(sharedPreferences.getString(MyConstants.USER_NAME, ""));
        number.setText(sharedPreferences.getString(MyConstants.NUMBER, ""));
        progressBar.setVisibility(View.GONE);

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

        switch (id) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                fragmentTransaction(fragment);
                break;
            case R.id.nav_offers:
                fragment = new BestOffersFragment();
                fragmentTransaction(fragment);
                break;
            case R.id.nav_payment_request:
                startActivity(new Intent(MainActivity.this, PaymentRequestActivity.class));
                break;
            case R.id.nav_my_order:
                fragment = new MyOrderFragment();
                fragmentTransaction(fragment);
                break;
            case R.id.nav_advertise_with_us:
                fragment = new AdvertiseWithUsFragment();
                fragmentTransaction(fragment);
                break;
            case R.id.nav_networks:
                fragment = new NetworkFragment();
                fragmentTransaction(fragment);
                break;
            case R.id.nav_support:
                fragment = new SupportFragment();
                fragmentTransaction(fragment);
                break;
            case R.id.nav_notification:
                fragment = new NotificationFragment();
                fragmentTransaction(fragment);
                break;
            case R.id.nav_wallet:
                startActivity(new Intent(MainActivity.this, WalletActivity.class));
                break;
            case R.id.nav_refer_and_earn:
                startActivity(new Intent(MainActivity.this, ReferAndEarnActivity.class));
                break;
            case R.id.nav_game:
                startActivity(new Intent(MainActivity.this, GameActivity.class));
                break;
            case R.id.nav_logout:
                sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.putBoolean(MyConstants.LOGGED_IN, false);
                sharedPreferencesEditor.putInt(MyConstants.LOAD_USER_PROFILE, 0);
                sharedPreferencesEditor.putInt(MyConstants.Earned_POINTS,0);
                sharedPreferencesEditor.apply();
                startActivity(new Intent(MainActivity.this, MainLoginActivity.class));
                finish();
                break;
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

    @Override
    public void onButtonSelected(int id) {
        Fragment fragment = null;
        switch (id) {
            case 1:
                startActivity(new Intent(this, WalletActivity.class));
                break;
            case 2:

                break;
            case 3:
                startActivity(new Intent(this, GameActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, ReferAndEarnActivity.class));

                break;
        }
    }

    private void loadProfileData() {
        mAPIService.getUser(sharedPreferences.getString(MyConstants.USER_ID, null)).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                setUserProfileSharedPreferences(response.body().getResult().getMobile(),
                        response.body().getResult().getFirstName() + " " + response.body().getResult().getLastName(),
                        response.body().getResult().getReferralCode());
                Toast.makeText(MainActivity.this, response.body().getResult().getMobile() + "\n" + response.body().getResult().getFirstName() + " " + response.body().getResult().getLastName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUserProfileSharedPreferences(String mobile, String s, String referralCode) {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(MyConstants.USER_NAME, s);
        sharedPreferencesEditor.putString(MyConstants.NUMBER, mobile);
        sharedPreferencesEditor.putInt(MyConstants.LOAD_USER_PROFILE, 1);
        sharedPreferencesEditor.putString(MyConstants.MY_REFERRAL_CODE, referralCode);
        sharedPreferencesEditor.apply();
        setUserProfile();
    }
}
