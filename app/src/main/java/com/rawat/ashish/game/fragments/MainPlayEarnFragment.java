package com.rawat.ashish.game.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.activities.SpinWheelActivity;


public class MainPlayEarnFragment extends Fragment {
    TextView spinWheel;


    public MainPlayEarnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View roootView = inflater.inflate(R.layout.fragment_main_play_earn, container, false);

        spinWheel = roootView.findViewById(R.id.main_spin_wheel_text_view);
        spinWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinWheelActivity();
            }
        });
        return roootView;
    }


    private void spinWheelActivity() {
        Intent spinWheelIntent = new Intent(getContext(), SpinWheelActivity.class);
        startActivity(spinWheelIntent);
    }


}