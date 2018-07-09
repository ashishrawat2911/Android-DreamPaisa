package com.rawat.ashish.game.gameActivities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rawat.ashish.game.R;

public class HitWicketActivity extends AppCompatActivity {
    Toast toast;
    ImageView hitWicketImageView, hitWicketAdImageView;
    CountDownTimer adCountDownTimer, gifCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hit_wicket);

        setTitle(" Hit The Wicket");

        hitWicketImageView = findViewById(R.id.hit_wicket_image_view);
        hitWicketAdImageView = findViewById(R.id.spinWheelAdImageView);
        hitWicketAdImageView.setImageResource(R.drawable.ads);
        hitWicketImageView.setImageResource(R.drawable.hit_wicket);
    }

    public void hitWicket(View view) {
        loadGif();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (adCountDownTimer != null && gifCountDownTimer != null) {
            adCountDownTimer.cancel();
            gifCountDownTimer.cancel();
        }
    }

    public void adWicket(View view) {
        adCountDownTimer = new CountDownTimer(5000, 999) {

            public void onTick(long millisUntilFinished) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(HitWicketActivity.this, "" + (millisUntilFinished + 1) / 1000, Toast.LENGTH_SHORT);
                toast.show();
            }


            public void onFinish() {
                Toast.makeText(HitWicketActivity.this, "Game played", Toast.LENGTH_SHORT).show();
                (findViewById(R.id.hitWicketTextView)).setEnabled(true);
            }
        }.start();
    }

    public void loadGif() {
        Glide.with(HitWicketActivity.this).load(R.raw.hitwicket).into(hitWicketImageView);
        gifCountDownTimer = new CountDownTimer(5000, 999) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                hitWicketImageView.setImageResource(R.drawable.hit_wicket);
                (findViewById(R.id.hitResult)).setVisibility(View.VISIBLE);


            }
        }.start();
    }
}
