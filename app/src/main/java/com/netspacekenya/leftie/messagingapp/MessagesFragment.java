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
import messagingapp.datastorage.UnreadMessagesTask;

/**
 * Created by Edwin on 03-Apr-15.
 */
public class MessagesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ConversationsAdapter adapter;
    SwipeRefreshLayout all_messages_swipe;
    DBops dBops;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.messages_layout, container, false);
        all_messages_swipe = (SwipeRefreshLayout)rootView.findViewById(R.id.all_messages_swipe);
        all_messages_swipe.setOnRefreshListener(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.conversations_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        dBops = new DBops(getActivity());
        adapter = new ConversationsAdapter(getActivity());

        recyclerView.setAdapter(adapter);


        return rootView;
    }

    @Override
    public void onRefresh() {
        new UnreadMessagesTask(getActivity(), this).execute();

    }
    public void refresh(){
        adapter.refresh();
        adapter.notifyDataSetChanged();
        all_messages_swipe.setRefreshing(false);
    }
}
