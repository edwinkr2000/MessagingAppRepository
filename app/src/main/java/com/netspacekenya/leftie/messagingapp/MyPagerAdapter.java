package com.netspacekenya.leftie.messagingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Edwin on 03-Apr-15.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
    FragmentManager fm;
    ParseQuery<ParseUser> parseQuery;
    String [] list;


    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        parseQuery = ParseUser.getQuery();
        parseQuery.orderByAscending(AppConstants.KEY_USERNAME);
        parseQuery.setLimit(10);



            try {
                List<ParseUser> users = parseQuery.find();
                list = new String[users.size()];
                int counter = 0 ;
                for(ParseUser u : users){
                    list[counter] = u.getUsername();
                    counter++;
                }


            } catch (ParseException e) {
                e.printStackTrace();

        }
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MessagesFragment();
            case 1:
              ContactsFragment frag = new ContactsFragment();
                Bundle b = new Bundle();
                b.putStringArray("list", list);
                frag.setArguments(b);
                return frag;
            case 2:
                return new MiscFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
