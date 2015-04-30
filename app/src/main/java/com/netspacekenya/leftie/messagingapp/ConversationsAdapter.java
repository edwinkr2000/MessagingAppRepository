package com.netspacekenya.leftie.messagingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;
import java.util.List;

import messagingapp.datastorage.DBops;

/**
 * Created by Edwin on 16-Apr-15.
 */
public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.Holder> {

    List<Conversation> processedConversationList;
    ParseUser currentUser;
    Context context;
    static List<String> holder;
    DBops dBops;
    BadgeView badge;

    public ConversationsAdapter(Context context) {
        this.context = context;
        dBops = new DBops(context);
        currentUser = ParseUser.getCurrentUser();
        processedConversationList = getConversations();


    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_view, parent, false);

        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.name_tv.setText(processedConversationList.get(position).getTitle());
        holder.last_text_tv.setText(processedConversationList.get(position).getLastText());

        int unRead = processedConversationList.get(position).getUnreadMessages();

            badge = new BadgeView(context, holder.badgeContainer);
            badge.setText(String.valueOf(unRead));
            badge.show();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conversationIntent = new Intent(context, ConversationActivity.class);
                conversationIntent.putExtra("clicked userName", processedConversationList.get(position).getUser_id());
                dBops.readMessages(processedConversationList.get(position).getUser_id());
                context.startActivity(conversationIntent);


            }
        });

    }
    public void refresh(){
        this.processedConversationList = getConversations();
    }

    @Override
    public int getItemCount() {
        return this.processedConversationList.size();
    }


    private List<Conversation> getConversations(){
        holder = new ArrayList<>();
        List<Conversation> cList  = new ArrayList<>();

        /*
        This code below is huge; for processing messages to batches of conversations,,,
        BOOOYAH!!!!! :) :) :) :) :)
         */
        Conversation c;
         for(Message m : this.dBops.getAllMessages()){
           if((!holder.contains(m.getSender_id())) && !holder.contains(m.getRecipient_id())){
                c = new Conversation();
               if(m.getSender_id().equals(ParseUser.getCurrentUser().getObjectId())){
                   c.setTitle(dBops.getNamesOfFriend(m.getRecipient_id())[0]);
                   c.setUser_id(m.getRecipient_id());
                   c.setLastText(dBops.getLastText(m.getRecipient_id()));
//                   if(!m.isRead()) c.setUnreadMessages(c.getUnreadMessages()+1);
                   holder.add(m.getRecipient_id());
               }
               else{
                   c.setTitle(dBops.getNamesOfFriend(m.getSender_id())[0]);
                   c.setUser_id(m.getSender_id());
                   c.setLastText(dBops.getLastText(m.getSender_id()));
//                   if(!m.isRead()) c.setUnreadMessages(c.getUnreadMessages()+1);
                   holder.add(m.getSender_id());
               }

               cList.add(c);
           }

        }

        return cList;
    }

    public static class Holder extends RecyclerView.ViewHolder{
        TextView name_tv;
        View itemView;
        TextView last_text_tv;
        CircleImageView iv;
        TextView badgeContainer;
        public Holder( View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.name_tv = (TextView) itemView.findViewById(R.id.sender_tv);
            this.last_text_tv = (TextView) itemView.findViewById(R.id.last_text_tv);
            this.iv = (CircleImageView) itemView.findViewById(R.id.sender_iv);
            this.badgeContainer= (TextView) itemView.findViewById(R.id.badgeContainer);
           }
    }
}
