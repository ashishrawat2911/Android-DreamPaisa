package com.rawat.ashish.game.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.activities.WithDrawActivity;


public class WalletFragment extends Fragment {
    Button redeemButton;

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_wallet, container, false);
        redeemButton = rootView.findViewById(R.id.redeemButton);
        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redeemIntent = new Intent(getActivity(), WithDrawActivity.class);
                startActivity(redeemIntent);
            }
        });

        return rootView;
    }


}
