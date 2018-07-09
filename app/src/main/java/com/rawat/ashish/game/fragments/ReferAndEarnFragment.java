package com.rawat.ashish.game.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rawat.ashish.game.R;

public class ReferAndEarnFragment extends Fragment {
    Button inviteFriends;

    public ReferAndEarnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_refer_and_earn, container, false);
        inviteFriends = rootView.findViewById(R.id.inviteFriendsButton);
        inviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String packageName = getActivity().getPackageName();
                Intent appShareIntent = new Intent(Intent.ACTION_SEND);
                appShareIntent.setType("text/plain");
                String extraText = "Hey! Check out this amazing game app\nwhere you can play and earn money.\n";
                extraText += "https://play.google.com/store/apps/details?id=" + packageName;
                appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(appShareIntent);
            }
        });
        return rootView;
    }


}