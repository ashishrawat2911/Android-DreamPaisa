package com.rawat.ashish.game.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rawat.ashish.game.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiseWithUsFragment extends Fragment {


    public AdvertiseWithUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advertise_with_us, container, false);
    }

}
