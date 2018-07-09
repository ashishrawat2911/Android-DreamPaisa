package com.rawat.ashish.game.adaptors;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.fragments.HomeFragment;
import com.rawat.ashish.game.fragments.WalletFragment;
import com.rawat.ashish.game.fragments.MainPlayEarnFragment;
import com.rawat.ashish.game.fragments.ReferAndEarnFragment;

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
                return new MainPlayEarnFragment();
            case 1:
                return new ReferAndEarnFragment();
            case 2:
                return new WalletFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return mContext.getResources().getString(R.string.play_game);
            case 1:
                return mContext.getResources().getString(R.string.refer_and_earn);
            case 2:
                return mContext.getResources().getString(R.string.wallet);

        }
        return null;
    }
}
