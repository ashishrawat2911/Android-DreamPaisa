package com.rawat.ashish.game.gameActivities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rawat.ashish.game.R;

public class SpinWheelActivity extends AppCompatActivity {
    Toast toast;
    ImageView spinWheelImageView,spinWheelAdImageView;
    CountDownTimer adCountDownTimer, gifCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_wheel);
        setTitle(" Spin Wheel");
        spinWheelImageView = findViewById(R.id.spin_wheel_image_view);
        spinWheelAdImageView = findViewById(R.id.spinWheelAdImageView);
        spinWheelAdImageView.setImageResource(R.drawable.ads);
        spinWheelImageView.setImageResource(R.drawable.spin_wheel);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adCountDownTimer != null && gifCountDownTimer != null) {
            adCountDownTimer.cancel();
            gifCountDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void spinWheel(View view) {
        loadGif();
    }

    public void adSpin(View view) {
        adCountDownTimer = new CountDownTimer(5000, 999) {
            public void onTick(long millisUntilFinished) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(SpinWheelActivity.this, "" + (millisUntilFinished + 1) / 1000, Toast.LENGTH_SHORT);
                toast.show();

            }

            public void onFinish() {

                Toast.makeText(SpinWheelActivity.this, "Game played", Toast.LENGTH_SHORT).show();
                (findViewById(R.id.spinWheelTextView)).setEnabled(true);
            }
        }.start();
    }

    public void loadGif() {
        Glide.with(SpinWheelActivity.this).load(R.raw.spinwheel).into(spinWheelImageView);
        gifCountDownTimer = new CountDownTimer(5000, 999) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                spinWheelImageView.setImageResource(R.drawable.spin_wheel);
                (findViewById(R.id.spinResult)).setVisibility(View.VISIBLE);
            }
        }.start();
    }
}
