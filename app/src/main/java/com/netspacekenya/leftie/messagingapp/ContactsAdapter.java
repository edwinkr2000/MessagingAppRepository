package com.netspacekenya.leftie.messagingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Edwin on 12-Apr-15.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.Holder> {
    List<ParseUser> contactsList;

    public ContactsAdapter(List<ParseUser> contactsList) {
        this.contactsList = contactsList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view, parent, false);

        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv.setText(contactsList.get(position).getUsername().toString());
        holder.iv.setImageResource(R.drawable.ic_action_person);

    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;
        public Holder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.contact_tv);
            iv = (ImageView) itemView.findViewById(R.id.contact_iv);

        }
    }
}
