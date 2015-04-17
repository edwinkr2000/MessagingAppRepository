package com.netspacekenya.leftie.messagingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Edwin on 16-Apr-15.
 */
public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.Holder> {
    List<ParseObject> conversationList;

    ParseUser currentUser;

    public ConversationsAdapter(List<ParseObject> conversationList) {
        this.conversationList=conversationList;
        currentUser = ParseUser.getCurrentUser();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_view, parent, false);

        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.name_tv.setText((CharSequence) conversationList.get(position).get(AppConstants.KEY_SENDER_ID));
        holder.last_text_tv.setText((CharSequence)conversationList.get(position).get(AppConstants.KEY_MESSAGE));

    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }


    public static class Holder extends RecyclerView.ViewHolder{
        TextView name_tv;
        TextView last_text_tv;
        CircleImageView iv;
        public Holder(View itemView) {
            super(itemView);
            this.name_tv = (TextView) itemView.findViewById(R.id.sender_tv);
            this.last_text_tv = (TextView) itemView.findViewById(R.id.last_text_tv);
            this.iv = (CircleImageView) itemView.findViewById(R.id.sender_iv);
        }
    }
}
