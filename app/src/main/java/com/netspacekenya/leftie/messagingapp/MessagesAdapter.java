package com.netspacekenya.leftie.messagingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Edwin on 15-Apr-15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.Holder> {
    List<String> messageList;
    public MessagesAdapter(List messageList) {
        this.messageList=messageList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view, parent, false);

        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv.setText(messageList.get(position));

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        TextView tv;
        public Holder(View itemView) {
            super(itemView);
            this.tv = (TextView) itemView.findViewById(R.id.message_tv);
        }
    }
}