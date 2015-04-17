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
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 03-Apr-15.
 */
public class MessagesFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ProgressBar bar;

    ParseUser currentUser= ParseUser.getCurrentUser();
    ParseQuery<ParseObject> conversationsQuery;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.messages_layout, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.conversations_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        bar = (ProgressBar) rootView.findViewById(R.id.conversations_progress_bar);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter( new ConversationsAdapter(new ArrayList<ParseObject>()));

        return rootView;
    }
    private void loadConversations(){
        conversationsQuery = new ParseQuery<ParseObject>(AppConstants.CLASS_MESSAGE);
        conversationsQuery.addDescendingOrder(AppConstants.KEY_CREATED_AT);
        conversationsQuery.whereEqualTo(AppConstants.KEY_RECIPIENT_ID, currentUser.getUsername());
        conversationsQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messageObjects, ParseException e) {
                bar.setVisibility(View.GONE);
                if(e==null && messageObjects!=null){
                    ///success
                    recyclerView.setAdapter(new ConversationsAdapter(messageObjects));
                }
                else{
                    ///fail
                    Toast.makeText(getActivity(), "Error loading conversations", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(ParseUser.getCurrentUser()!=null) loadConversations();

    }
}
