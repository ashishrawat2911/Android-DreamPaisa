package com.rawat.ashish.game.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.UpdatedBalance;
import com.rawat.ashish.game.model.UserDetails;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameActivity extends AppCompatActivity {
    private static final float fac = 36;
    APIService mAPIService;
    Button button, redeem;
    ImageView wheel;
    TextView totalResult;
    Random r;
    float netEarn;
    AlertDialog alertDialog;
    int degree = 0;
    int PLAY_TIMES = 0;
    int degree_old = 0;
    int totalcoins = 0;
    boolean startGame = false;
    SharedPreferences sharedPreferences;
    ProgressDialog progress;
    SharedPreferences.Editor sharedPreferencesEditor;
    String referCash, earnedCash, netBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        totalResult = findViewById(R.id.totalResult);
        redeem = findViewById(R.id.redeemResult);
        button = (Button) findViewById(R.id.spinGameButton);
        wheel = (ImageView) findViewById(R.id.spinGameImageView);

        mAPIService = APIClient.getClient().create(APIService.class);
        alertDialog = new AlertDialog.Builder(
                this).create();
        r = new Random();
        loadProgressBar();
        loadUserDetails();
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameActivity.this, WalletActivity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startGame) {
                    spinWheel();
                } else {
                    AlertDialog("low refer cash", "You cannot play game");
                }
            }
        });

    }

    private void spinWheel() {
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
                // updateSEarnedCash(a);
                PLAY_TIMES++;

                Toast.makeText(GameActivity.this, Float.toString((float) a / 100), Toast.LENGTH_SHORT).show();
                totalResult.setText("Result:" + a);
                netEarn += (float) a / 100;
                //setTotalPoints();
                updateCash();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        wheel.startAnimation(rotate);

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

    private void loadUserDetails() {
        mAPIService.getUser(sharedPreferences.getString(MyConstants.USER_ID, null)).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                earnedCash = response.body().getResult().getEarnedCash();
                referCash = response.body().getResult().getReferCash();
                netBalance = response.body().getResult().getNetBalance();
                progress.dismiss();
                if (Float.parseFloat(response.body().getResult().getReferCash()) > 0.0f) {
                    startGame = true;
                } else AlertDialog("low refer cash", "You cannot play game");

            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(GameActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void AlertDialog(String title, String message) {
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(R.drawable.ic_action_cancel);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
            }
        });
        alertDialog.show();
    }

    private void updateCash() {


        mAPIService.updateCash(sharedPreferences.getString(MyConstants.USER_ID, null), referCash, earnedCash + netEarn, netBalance).enqueue(new Callback<UpdatedBalance>() {
            @Override
            public void onResponse(Call<UpdatedBalance> call, Response<UpdatedBalance> response) {
                Toast.makeText(GameActivity.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
                updateSEarnedCash(0);

            }

            @Override
            public void onFailure(Call<UpdatedBalance> call, Throwable t) {
                Toast.makeText(GameActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProgressBar() {
        progress = new ProgressDialog(this);
        progress.setMessage("Loading data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

    }
}
