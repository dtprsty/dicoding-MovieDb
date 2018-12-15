package com.example.prasetyo.moviedb.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.prasetyo.moviedb.ui.nowPlaying.NowPlayingFragment;
import com.example.prasetyo.moviedb.ui.popular.PopularFragment;
import com.example.prasetyo.moviedb.ui.upcoming.UpcomingFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UpcomingFragment tab1 = new UpcomingFragment();
                return tab1;
            case 1:
                NowPlayingFragment tab2 = new NowPlayingFragment();
                return tab2;
            case 2:
                PopularFragment tab3 = new PopularFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}