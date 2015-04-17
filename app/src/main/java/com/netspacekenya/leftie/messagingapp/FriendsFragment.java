package com.netspacekenya.leftie.messagingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 13-Apr-15.
 */
public class FriendsFragment extends Fragment {
    RecyclerView friendsRecyclerView;
    RecyclerView.Adapter adapter;
    ProgressBar bar;
    List<ParseUser> emptyList;
    ParseRelation<ParseUser> relation;
    ParseQuery<ParseUser> relationQuery;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends_layout, container, false);
        friendsRecyclerView = (RecyclerView) rootView.findViewById(R.id.friends_recycler_view);
        bar = (ProgressBar) rootView.findViewById(R.id.friends_progress_bar);

        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ///init empty list for recyclerview.adapter while waiting for list from cloud

        emptyList = new ArrayList<ParseUser>();
        adapter = new FriendsAdapter(emptyList, getActivity());
        friendsRecyclerView.setAdapter(adapter);


        return rootView;
    }
    private void loadFriendsList(){
        relation = ParseUser.getCurrentUser().getRelation(AppConstants.KEY_FRIEND_RELATION);
        relationQuery = relation.getQuery();

        relationQuery.orderByAscending(AppConstants.KEY_USERNAME);
        relationQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                bar.setVisibility(View.GONE);

                if(e==null && parseUsers!=null){
                    ///success
                    adapter = new FriendsAdapter(parseUsers, getActivity());
                    friendsRecyclerView.setAdapter(adapter);
                    Toast.makeText(getActivity(), "Successfully loaded friends list", Toast.LENGTH_SHORT).show();
                }
                else{
                    ///fail
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(ParseUser.getCurrentUser()!=null) loadFriendsList();
    }
}
