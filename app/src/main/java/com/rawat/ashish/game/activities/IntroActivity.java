package com.rawat.ashish.game.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.rawat.ashish.game.R;


public class IntroActivity extends AppIntro {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Add slides
        addSlide(AppIntroFragment.newInstance("Create a network", "Refer this app to your friends and family.", R.drawable.ads, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("Play", "Convert the refer amount by playing games.", R.drawable.ads, Color.DKGRAY));
        addSlide(AppIntroFragment.newInstance("Redeem", "When you earn ₹ 100 you can redeem them.", R.drawable.ads, Color.DKGRAY));
        showSkipButton(false);
        showStatusBar(false);
        setProgressButtonEnabled(true);
    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
