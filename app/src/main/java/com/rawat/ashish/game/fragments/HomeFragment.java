package com.rawat.ashish.game.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.rawat.ashish.game.R;
import com.rawat.ashish.game.adaptors.GameAdaptor;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private SmartTabLayout mSmartTabLayout;
    private ViewPager mViewPager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSmartTabLayout = (SmartTabLayout) view.findViewById(R.id.tab_view_pager_fav);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager_fav);
        mViewPager.setAdapter(new GameAdaptor(getChildFragmentManager(), this));
        mSmartTabLayout.setViewPager(mViewPager);
        return view;
    }
}
