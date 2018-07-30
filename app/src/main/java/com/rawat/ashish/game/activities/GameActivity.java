package com.rawat.ashish.game.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private static final float fac = 36;
    APIService mAPIService;
    Button button;
    ImageView wheel;
    TextView result, totalResult;
    Random r;
    TextView redeem;
    int degree = 0;
    int degree_old = 0;
    int totalcoins = 0;
    int resultcoin = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        totalResult = findViewById(R.id.totalResult);
        redeem = findViewById(R.id.redeemResult);
        button = (Button) findViewById(R.id.spinGameButton);
        wheel = (ImageView) findViewById(R.id.spinGameImageView);
        result = (TextView) findViewById(R.id.spinResultTextView);
        mAPIService = APIClient.getClient().create(APIService.class);
        setTotalPoints();
        r = new Random();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree_old = degree % 360;
                degree = r.nextInt(3600) + 7200;
                RotateAnimation rotate = new RotateAnimation(degree_old, degree, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int a = currentNo(360 - (degree % 360));
                        updateSEarnedCash(a);
                        result.setText("Result:" + a);
                        totalcoins += a / 100;
                        setTotalPoints();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                wheel.startAnimation(rotate);


            }
        });

    }

    private void updateSEarnedCash(int a) {
        sharedPreferencesEditor = sharedPreferences.edit();
        int s = sharedPreferences.getInt(MyConstants.Earned_POINTS, 0);
        sharedPreferencesEditor.putInt(MyConstants.Earned_POINTS, s + a);
        sharedPreferencesEditor.apply();

    }


    public void setTotalPoints() {
        totalResult.setText(sharedPreferences.getInt(MyConstants.Earned_POINTS, 0) + " Points");
    }

    private int currentNo(int degrees) {
        int text = 0;

        if (degrees >= 0 && degrees < fac) {
            text = 1;
        }
        if (degrees >= fac && degrees < (fac * 2)) {
            text = 2;
        }
        if (degrees >= fac * 2 && degrees < (fac * 3)) {
            text = 3;
        }
        if (degrees >= fac * 3 && degrees < (fac * 4)) {
            text = 4;
        }
        if (degrees >= fac * 4 && degrees < fac * 5) {
            text = 5;
        }
        if (degrees >= fac * 5 && degrees < fac * 6) {
            text = 6;
        }
        if (degrees >= fac * 6 && degrees < fac * 7) {
            text = 7;
        }
        if (degrees >= fac * 7 && degrees < fac * 8) {
            text = 8;
        }
        if (degrees >= fac * 8 && degrees < fac * 9) {
            text = 9;
        }
        if (degrees >= fac * 9 && degrees < fac * 10) {
            text = 10;
        }
        return text;
    }
}
