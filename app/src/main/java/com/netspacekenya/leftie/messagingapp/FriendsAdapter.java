package com.netspacekenya.leftie.messagingapp;

import android.content.Intent;
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
 * Created by Edwin on 13-Apr-15.
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.Holder> {
    List<ParseUser> friendsList;
    FragmentActivity activity;
    public FriendsAdapter(List<ParseUser> friendsList, FragmentActivity activity) {
        this.activity = activity;
        this.friendsList = friendsList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view, parent, false);

        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.tv.setText(friendsList.get(position).getUsername());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conversationIntent = new Intent(activity, ConversationActivity.class);
                ParseUser clickedUser = friendsList.get(position);
                conversationIntent.putExtra("clicked userName",clickedUser.getUsername());
                activity.startActivity(conversationIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        TextView tv;
        ImageView iv;
        View v;
        public Holder(View itemView) {
            super(itemView);
            this.v = itemView;
            tv = (TextView) itemView.findViewById(R.id.contact_tv);
            iv = (ImageView) itemView.findViewById(R.id.contact_iv);
        }
    }
}
