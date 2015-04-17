package com.netspacekenya.leftie.messagingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Edwin on 03-Apr-15.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
    FragmentManager fm;


    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;

    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MessagesFragment();
            case 1:
                return new FriendsFragment();

            case 2:
                AllContactsFragment frag = new AllContactsFragment();


                return new AllContactsFragment();
            case 3:
                return new MiscFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
