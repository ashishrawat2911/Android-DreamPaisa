package com.rawat.ashish.game.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;

public class MainLoginActivity extends AppCompatActivity {
    Fragment fragment = null;
    LinearLayout loginSignLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        //Instantiate layout
        loginSignLinearLayout = findViewById(R.id.loginAndSignLinearLayout);
        loginSignLinearLayout.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainLoginActivity.this);
        if (sharedPreferences.getBoolean(MyConstants.FIRST_TIME_LAUNCH, true)) {
            startActivity(new Intent(MainLoginActivity.this, IntroActivity.class));
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(MyConstants.FIRST_TIME_LAUNCH, false);
            sharedPreferencesEditor.apply();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    // Belong to login button in xml file
    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    // Belong to sign in button in xml file
    public void signIn(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();

    }


    private void fragmentTransaction(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.loginAndSignContainer, fragment);
            ft.commit();
        }
    }

}
