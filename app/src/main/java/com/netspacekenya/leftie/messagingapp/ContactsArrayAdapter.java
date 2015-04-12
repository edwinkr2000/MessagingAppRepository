package com.netspacekenya.leftie.messagingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Edwin on 11-Apr-15.
 */
public class ContactsArrayAdapter extends ArrayAdapter {
    String[] list;
    Context context;

    public ContactsArrayAdapter(Context context, int resource, String[] list) {
        super(context, resource, list);
        this.list=list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView =inflater.inflate(R.layout.contact_view, parent, false);
         TextView tv= (TextView) rootView.findViewById(R.id.contact_tv);
        tv.setText(list[position]);
        return rootView;
    }

}
