package messagingapp.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.netspacekenya.leftie.messagingapp.AppConstants;
import com.netspacekenya.leftie.messagingapp.Message;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 24-Apr-15.
 */
public class DBops {
    DataBaseOpenHelper helper;
    Context context;

    public DBops(Context context){
        this.context = context;
        helper = new DataBaseOpenHelper(context);



    }

    ///save friend
    public boolean saveFriend(ParseUser friend){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues val  = new ContentValues();
        boolean hasSucceeded;
        db.beginTransaction();
        val.put(AppConstants.COLUMN_FRIEND_ID, friend.getObjectId());
        val.put(AppConstants.COLUMN_FIRST_NAME, friend.getString(AppConstants.KEY_FIRST_NAME));
        val.put(AppConstants.COLUMN_LAST_NAME, friend.getString(AppConstants.KEY_LAST_NAME));
        val.put(AppConstants.COLUMN_FRIEND_STATUS, friend.getString(AppConstants.KEY_STATUS));
        val.put(AppConstants.COLUMN_P_PIC_ID, friend.getString(AppConstants.KEY_PROFILE_PICTURE));
        try{
            db.insert(AppConstants.FRIENDS_TABLE, null, val);
            hasSucceeded = true;
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
            hasSucceeded = false;
        }
        db.endTransaction();
        db.close();
        return hasSucceeded;
    }
    ///remove friend
    public boolean removeFriend(ParseUser friend) {
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean hasSucceeded;
        db.beginTransaction();
        try{
            db.delete(AppConstants.FRIENDS_TABLE, String.format("%s=%s",AppConstants.COLUMN_FRIEND_ID,friend.getObjectId()), null);
            hasSucceeded = true;
            db.setTransactionSuccessful();
        }catch (Exception e){
            hasSucceeded = false;
            e.printStackTrace();

        }
        db.endTransaction();
        db.close();
        return hasSucceeded;
    }

    ///get friendslist

    public List<ParseObject> getAllFriendsList(){
        List<ParseObject> fList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(AppConstants.FRIENDS_TABLE, new String[]{
                AppConstants.COLUMN_FRIEND_ID, AppConstants.COLUMN_FIRST_NAME, AppConstants.COLUMN_LAST_NAME, AppConstants.COLUMN_P_PIC_ID, AppConstants.COLUMN_FRIEND_STATUS
        }, null, null, null, null, null);
        db.beginTransaction();
        if(c.moveToFirst()){
            do{
                ParseObject u =  ParseUser.createWithoutData("ParseUser", c.getString(0));


//                u.setObjectId(c.getString(0));
                u.put(AppConstants.KEY_FIRST_NAME, c.getString(1));
                u.put(AppConstants.KEY_LAST_NAME, c.getString(2));
                if(c.getString(3)!=null)
                u.put(AppConstants.KEY_PROFILE_PICTURE, c.getString(3));
                if(c.getString(4)!=null)
                u.put(AppConstants.KEY_STATUS, c.getString(4));
                fList.add( u);
            }while (c.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        return fList;
    }
    //get names of friend
    public String [] getNamesOfFriend(String objectId){
        String [] names = new String[2];
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + AppConstants.COLUMN_FIRST_NAME + ", " + AppConstants.COLUMN_LAST_NAME +" FROM " +AppConstants.FRIENDS_TABLE + " WHERE " +AppConstants.COLUMN_FRIEND_ID + " = \'" + objectId
                + "\' ", null);
        if(c.moveToFirst()){
            names[0] = c.getString(0);
            names[1] = c.getString(1);
        }
        return names;
    }
    ///save message
    public boolean saveMessage(Message message) {
        boolean hasSucceeded;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(BaseColumns._ID, message.getMessage_id());
        val.put(AppConstants.COLUMN_IS_READ, message.isRead());
        val.put(AppConstants.COLUMN_SENDER_ID, message.getSender_id());
        val.put(AppConstants.COLUMN_RECIPIENT_ID, message.getRecipient_id());
        val.put(AppConstants.COLUMN_TEXT, message.getText());
        val.put(AppConstants.COLUMN_MEDIA_ID, "");
        val.put(AppConstants.COLUMN_IS_READ, message.isRead());
        db.beginTransaction();
        try{
            db.insert(AppConstants.MESSAGES_TABLE, null, val);
            hasSucceeded=true;
            db.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
            hasSucceeded=false;
        }
        db.endTransaction();
        db.close();

        return hasSucceeded;
    }
    public boolean deleteMessage(Message m){
        boolean hasSucceeded;
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try{
            db.delete(AppConstants.MESSAGES_TABLE, String.format("%s=%s",BaseColumns._ID,String.valueOf(m.getMessage_id())), null);
            hasSucceeded = true;
            db.setTransactionSuccessful();
        }catch (Exception e){
            hasSucceeded = false;
            e.printStackTrace();
        }

        db.endTransaction();
        db.close();
        return hasSucceeded;
    }

    ///get conversation list
    public List<Message> getConversationForFriend(String objectID){
       List<Message> mList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        db.beginTransaction();
        Cursor c = db.rawQuery("SELECT * FROM " +AppConstants.MESSAGES_TABLE + " WHERE " +AppConstants.COLUMN_RECIPIENT_ID + " = \'" + objectID
                + "\' OR " + AppConstants.COLUMN_SENDER_ID + " = \'" + objectID + "\'", null);
        if(c.moveToFirst()){
            do{
                Message m = new Message(c.getString(3), c.getString(1), c.getString(2), c.getString(4));
                m.setMessage_id(c.getString(0));
                mList.add(m);
            }
            while(c.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return mList;

    }

    ///get all messages
    public List<Message> getAllMessages() {
        ArrayList<Message> mList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        db.beginTransaction();
        Cursor c = db.query(AppConstants.MESSAGES_TABLE, new String[]{
                BaseColumns._ID, AppConstants.COLUMN_SENDER_ID, AppConstants.COLUMN_RECIPIENT_ID, AppConstants.COLUMN_TEXT, AppConstants.COLUMN_MEDIA_ID, AppConstants.COLUMN_IS_READ
        }, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {

                Message m = new Message(c.getString(3), c.getString(1), c.getString(2), c.getString(4));
                m.setRead(Boolean.getBoolean(c.getString(5)));

                m.setMessage_id(c.getString(0));
                mList.add(m);

            } while (c.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return mList;
    }
    ///get all received messages
    public List<Message> getAllReceivedMessages(){
        List<Message> mList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        db.beginTransaction();
        Cursor c = db.rawQuery("SELECT * FROM " +AppConstants.MESSAGES_TABLE + " WHERE " +AppConstants.COLUMN_RECIPIENT_ID + " = \'" + ParseUser.getCurrentUser().getObjectId()
                + "\' ", null);
        if(c.moveToFirst()){
            do{
                Message m = new Message(c.getString(3), c.getString(1), c.getString(2), c.getString(4));
                m.setMessage_id(c.getString(0));
                m.setRead(Boolean.getBoolean(c.getString(5)));
                mList.add(m);
            }
            while(c.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return mList;

    }
    //get last text with friend
    public String getLastText(String recipientID) {
        String s = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        db.beginTransaction();
        Cursor c = db.rawQuery("SELECT " + AppConstants.COLUMN_TEXT + " FROM " + AppConstants.MESSAGES_TABLE + " WHERE " + AppConstants.COLUMN_SENDER_ID + " = \'" + recipientID + "\'" + " OR " + AppConstants.COLUMN_RECIPIENT_ID + " = \'" + recipientID + "\'", null);
        if (c.moveToLast()) {
            db.setTransactionSuccessful();
            s = c.getString(0);
        }
        db.endTransaction();
        db.close();
        return s;
    }
    ///set msgs with certain friend as read called after opening conversation
    public void readMessages(String friendID){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        ContentValues val = new ContentValues();
        val.put(AppConstants.COLUMN_IS_READ, true);
        db.update(AppConstants.MESSAGES_TABLE, val, AppConstants.COLUMN_SENDER_ID + " = \'" +friendID+"\'", null);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

}
