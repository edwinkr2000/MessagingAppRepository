package com.netspacekenya.leftie.messagingapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Edwin on 03-Apr-15.
 */
public class ContactsFragment extends ListFragment{

    View rootView;
    FrameLayout container_view;
    String[] list;

    @Override
    public View onCreateView(LayoutInflater inflater,  final ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.contacts_layout, container, false);

        list = getArguments().getStringArray("list");
        if(list!=null) setListAdapter(new ContactsArrayAdapter(getActivity(), R.layout.contacts_layout, list));

//        setListAdapter(new ContactsArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list));




        return rootView;
    }
}
