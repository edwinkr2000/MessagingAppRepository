package com.netspacekenya.leftie.messagingapp;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Edwin on 15-Apr-15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.Holder> {
    List<Message> messageList;
    Context context;
    public MessagesAdapter(Context context, List<Message> messageList) {
        this.messageList=messageList;
        this.context = context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view, parent, false);

        return new Holder(rootView);
    }

    public void setData(List<Message> messageList){
        this.messageList = messageList;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv.setText(messageList.get(position).getText());
        if(!messageList.get(position).getSender_id().equals(ParseUser.getCurrentUser().getObjectId())){

            ///outgoing
            holder.tv.setBackgroundDrawable(context.getResources().getDrawable((context.getResources().getIdentifier("com.netspacekenya.leftie.messagingapp:drawable/speech_bubble_orange", null, null))));

        }
        else {
            //incoming
            holder.tv.setBackgroundDrawable(context.getResources().getDrawable(context.getResources().getIdentifier("com.netspacekenya.leftie.messagingapp:drawable/speech_bubble_green", null, null)));
            holder.container.setGravity(GravityCompat.END);


        }


    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        TextView tv;

        RelativeLayout container;
        public Holder(View itemView) {
            super(itemView);
            this.container = (RelativeLayout) itemView.findViewById(R.id.message_container);
            this.tv = (TextView) itemView.findViewById(R.id.message_tv);

        }
    }
}