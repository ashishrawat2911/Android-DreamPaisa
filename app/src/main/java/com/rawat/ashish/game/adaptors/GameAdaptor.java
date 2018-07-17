package com.rawat.ashish.game.adaptors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.fragments.BestOffersFragment;
import com.rawat.ashish.game.fragments.DailyUpdateFragment;
import com.rawat.ashish.game.fragments.HomeFragment;
import com.rawat.ashish.game.fragments.HomeMainFragment;
import com.rawat.ashish.game.fragments.MainPlayEarnFragment;
import com.rawat.ashish.game.fragments.NetworkFragment;
import com.rawat.ashish.game.fragments.ProductsFragment;

public class GameAdaptor extends FragmentPagerAdapter {

    private HomeFragment mContext;

    public GameAdaptor(FragmentManager fm, HomeFragment context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeMainFragment();
            case 1:
                return new NetworkFragment();
            case 2:
                return new ProductsFragment();
            case 3:
                return new BestOffersFragment();
            case 4:
                return new DailyUpdateFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return mContext.getResources().getString(R.string.home);
            case 1:
                return mContext.getResources().getString(R.string.my_networks);
            case 2:
                return mContext.getResources().getString(R.string.products);
            case 3:
                return mContext.getResources().getString(R.string.best_offers);
            case 4:
                return mContext.getResources().getString(R.string.daily_updates);

        }
        return null;
    }
}
