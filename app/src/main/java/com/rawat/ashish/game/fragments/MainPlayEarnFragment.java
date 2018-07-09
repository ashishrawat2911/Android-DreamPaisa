package com.rawat.ashish.game.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.gameActivities.CoinTossActivity;
import com.rawat.ashish.game.gameActivities.HitWicketActivity;
import com.rawat.ashish.game.gameActivities.SpinWheelActivity;


public class MainPlayEarnFragment extends Fragment {
    TextView spinWheel, hitWicket, coinToss ;


    public MainPlayEarnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View roootView = inflater.inflate(R.layout.fragment_main_play_earn, container, false);

        coinToss = roootView.findViewById(R.id.main_coin_toss_text_view);
        spinWheel = roootView.findViewById(R.id.main_spin_wheel_text_view);
        hitWicket = roootView.findViewById(R.id.main_hit_wicket_text_view);
        coinToss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coinTossActivity();
            }
        });
        spinWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinWheelActivity();
            }
        });
        hitWicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitWicketActivity();
            }
        });
        return roootView;
    }


    private void spinWheelActivity() {
        Intent spinWheelIntent = new Intent(getContext(), SpinWheelActivity.class);
        startActivity(spinWheelIntent);
    }

    private void coinTossActivity() {
        Intent coinTossIntent = new Intent(getContext(), CoinTossActivity.class);
        startActivity(coinTossIntent);
    }

    private void hitWicketActivity() {
        Intent hitWicketIntent = new Intent(getContext(), HitWicketActivity.class);
        startActivity(hitWicketIntent);
    }


}