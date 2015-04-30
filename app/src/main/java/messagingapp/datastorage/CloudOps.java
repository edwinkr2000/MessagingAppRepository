package messagingapp.datastorage;

import com.netspacekenya.leftie.messagingapp.AppConstants;
import com.netspacekenya.leftie.messagingapp.Message;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 24-Apr-15.
 */
public class CloudOps {
    ParseUser currentUser;

    public CloudOps(){
        this.currentUser = ParseUser.getCurrentUser();
    }

    ///use this method in asynctask
    public boolean saveMessageToCloud(ParseObject m){
        boolean hasSucceeded;

        try {
            m.save();
            hasSucceeded = true;
        } catch (ParseException e) {
            e.printStackTrace();
            hasSucceeded = false;
        }
        return hasSucceeded;
    }
    ///get count of all received messages
    public int getAllReceivedMessages(){
        int x=-1;
        ParseQuery<ParseObject> mQuery = new ParseQuery<ParseObject>(AppConstants.CLASS_MESSAGE);
        mQuery.whereEqualTo(AppConstants.KEY_RECIPIENT_ID, ParseUser.getCurrentUser().getObjectId());
        try {
            x = mQuery.count();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return x;

    }
    ///get all unread messages
    public List<Message> getUnreadMessages(int readMessages){
        List<Message> mList = new ArrayList<>();
        int count = this.getAllReceivedMessages();
        ParseQuery<ParseObject> mQuery = new ParseQuery<>(AppConstants.CLASS_MESSAGE);
        mQuery.whereEqualTo(AppConstants.KEY_RECIPIENT_ID, currentUser.getObjectId());
        mQuery.addDescendingOrder(AppConstants.KEY_CREATED_AT);
        mQuery.orderByDescending(AppConstants.KEY_CREATED_AT);

        if(count!=-1){
            ///success counting all received msgs
            mQuery.setLimit(count-readMessages);
            List<ParseObject> list=null;
            try {
                list = mQuery.find();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(list!=null){
                //success getting messages
                for(ParseObject o :list){
                    String sender_id = o.getString(AppConstants.KEY_SENDER_ID);
                    String recipient_id = o.getString(AppConstants.KEY_SENDER_ID);
                    String text = o.getString(AppConstants.KEY_MESSAGE_TEXT);
                    String media_id = "";
                    Message m = new Message(text, sender_id, recipient_id, media_id);
                    m.setMessage_id(o.getObjectId());
                    if(!mList.contains(m))
                    mList.add(m);

                }
                return mList;
            }
            else{
                ///error getting msgs
                return null;
            }
        }
        else {
            return  null;
        }

    }
    ///add friend
    public boolean addFriend(ParseUser friend){

        boolean hasSucceeded;
        ParseRelation<ParseUser> r  = currentUser.getRelation(AppConstants.KEY_FRIEND_RELATION);
        r.add(friend);
        try {
            currentUser.save();
            hasSucceeded = true;
        } catch (ParseException e) {
            e.printStackTrace();
            hasSucceeded = false;
        }
        return hasSucceeded;
    }
    ///remove friend
    public boolean removeFriend(ParseUser friend){
        boolean hasSucceeded;
        ParseRelation<ParseUser> r = currentUser.getRelation(AppConstants.KEY_FRIEND_RELATION);
        r.remove(friend);
        try {
            currentUser.save();
            hasSucceeded = true;
        } catch (ParseException e) {
            e.printStackTrace();
            hasSucceeded = false;
        }
        return hasSucceeded;
    }


    ///push message
    public boolean pushMessage(Message message)  {
        boolean hasSucceeded;
        ParseQuery<ParseInstallation> query  = ParseInstallation.getCurrentInstallation().getQuery();

        query.whereEqualTo(AppConstants.KEY_CURRENT_USER, message.getRecipient_id());


        ParsePush p = new ParsePush();

        p.setMessage(message.getText());
        p.setQuery(query);
        p.sendInBackground();
        hasSucceeded = true;

        return hasSucceeded;
    }

}
