package com.netspacekenya.leftie.messagingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import messagingapp.datastorage.DBops;

/**
 * Created by Edwin on 13-Apr-15.
 */
public class FriendsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView friendsRecyclerView;
    RecyclerView.Adapter adapter;
    SwipeRefreshLayout friendsSwipe;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friends_layout, container, false);
        friendsRecyclerView = (RecyclerView) rootView.findViewById(R.id.friends_recycler_view);
        friendsSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.friends_swipe);
        friendsSwipe.setOnRefreshListener(this);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ///first read list from db
        DBops db = new DBops(getActivity());

        adapter = new FriendsAdapter(db.getAllFriendsList(), getActivity());
        friendsRecyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }
    private void refreshList(){
        DBops db = new DBops(getActivity());
        adapter = new FriendsAdapter(db.getAllFriendsList(), getActivity());
        friendsRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onRefresh() {
        refreshList();
        friendsSwipe.setRefreshing(false);

    }
}
