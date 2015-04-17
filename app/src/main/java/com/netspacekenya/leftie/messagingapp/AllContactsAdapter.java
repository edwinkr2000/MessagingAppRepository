package com.netspacekenya.leftie.messagingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.Holder>  {
     List<ParseUser> contactsList;
     FragmentActivity activity;


    public AllContactsAdapter(FragmentActivity activity, List<ParseUser> contactsList) {
        this.activity = activity;
        this.contactsList = contactsList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view, parent, false);


        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.tv.setText(contactsList.get(position).getUsername().toString());
        holder.iv.setImageResource(R.drawable.ic_action_person);
        holder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ContactDialogFragment dialogFrag = new ContactDialogFragment();
                Bundle b = new Bundle();
                b.putInt("position", position);
                dialogFrag.setArguments(b);
                dialogFrag.show(activity.getSupportFragmentManager(),"Options");
                return true;
            }
        });
      }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }


    public static class Holder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;
        View v;

        public Holder(View itemView) {
            super(itemView);
            v = itemView;
            tv = (TextView) itemView.findViewById(R.id.contact_tv);
            iv = (ImageView) itemView.findViewById(R.id.contact_iv);
        }

    }
}
