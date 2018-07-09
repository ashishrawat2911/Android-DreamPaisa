package com.rawat.ashish.game.gameActivities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rawat.ashish.game.R;

public class CoinTossActivity extends AppCompatActivity {
    Toast toast;
    ImageView coinToassImageView,coinAdImageView;

    CountDownTimer adCountDownTimer, gifCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_toss);
        setTitle(" Toss Coin");
        coinToassImageView = findViewById(R.id.coin_toss_image_view);
        coinAdImageView=findViewById(R.id.spinWheelAdImageView);
        coinAdImageView.setImageResource(R.drawable.ads);
        coinToassImageView.setImageResource(R.drawable.coin_toss);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adCountDownTimer != null && gifCountDownTimer != null) {
            adCountDownTimer.cancel();
            gifCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adCountDownTimer != null && gifCountDownTimer != null) {
            adCountDownTimer.cancel();
            gifCountDownTimer.cancel();
        }
    }

    public void coinToss(View view) {
        loadGif();

    }

    public void adCoin(View view) {
        adCountDownTimer = new CountDownTimer(5000, 999) {

            public void onTick(long millisUntilFinished) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(CoinTossActivity.this, "" + (millisUntilFinished + 1) / 1000, Toast.LENGTH_SHORT);
                toast.show();

            }

            public void onFinish() {

                Toast.makeText(CoinTossActivity.this, "Game played", Toast.LENGTH_SHORT).show();
                (findViewById(R.id.coinTossTextView)).setEnabled(true);
            }
        }.start();
    }

    public void loadGif() {

        Glide.with(CoinTossActivity.this).load(R.raw.cointoss).into(coinToassImageView);
        gifCountDownTimer = new CountDownTimer(5000, 999) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                coinToassImageView.setImageResource(R.drawable.coin_toss);
                (findViewById(R.id.coinResult)).setVisibility(View.VISIBLE);
            }
        }.start();
    }
}
