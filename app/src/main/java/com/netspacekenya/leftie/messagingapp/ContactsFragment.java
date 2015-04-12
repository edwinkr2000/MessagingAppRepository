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
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 03-Apr-15.
 */
public class ContactsFragment extends Fragment{

    View rootView;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ParseQuery<ParseUser> parseQuery;
    boolean succeeded = false;
    List<ParseUser> emptyList;
    ProgressBar bar;


    @Override
    public View onCreateView(LayoutInflater inflater,  final ViewGroup container,  Bundle savedInstanceState) {



        rootView = inflater.inflate(R.layout.contacts_layout, container, false);

        if(savedInstanceState==null) {
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            bar = (ProgressBar) rootView.findViewById(R.id.bar);
            layoutManager = new LinearLayoutManager(getActivity());
            emptyList = new ArrayList<ParseUser>();
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new ContactsAdapter(emptyList));
            getAllContacts();

          }

            return rootView;

    }
    private boolean getAllContacts(){

        parseQuery = ParseUser.getQuery();
        parseQuery.orderByDescending(AppConstants.KEY_USERNAME);
        parseQuery.setLimit(20);

        parseQuery.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                if(e==null && parseUsers!=null){
                    ///success
                    adapter = new ContactsAdapter(parseUsers);
                    ///remove current user from list
                    parseUsers.remove(ParseUser.getCurrentUser());


                    recyclerView.setAdapter(adapter);
                    succeeded=true;
                }
                else{
                    Toast.makeText(getActivity(), "Error loading contacts from cloud", Toast.LENGTH_LONG).show();
                }
            }
        });
        bar.setVisibility(View.GONE);
        return succeeded;

    }

    @Override
    public void onResume() {
        super.onResume();
        getAllContacts();
    }
}
