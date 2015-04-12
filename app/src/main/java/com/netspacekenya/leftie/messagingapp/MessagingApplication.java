package com.netspacekenya.leftie.messagingapp;

import android.app.Application;
import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by Edwin on 07-Apr-15.
 */
public class MessagingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "6ZqP5trRAAZLoWZO02t7SpCgmkUqYNZ5LcKKN2Iw", "UetvXEQf02lrm9pLPYt0L3pjxVCPF6VXfChqXWcU");

        ParseUser currentUser = ParseUser.getCurrentUser();

        //Check if user was logged in
        if(currentUser==null){
            ///not logged in
            Intent logInIntent = new Intent(this, LogInActivity.class);
            logInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            logInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logInIntent);
            return;

        }
        else{
            ///logged in
            Intent inboxIntent = new Intent(this, MainActivity.class);
            inboxIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            inboxIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(inboxIntent);
            return;
        }
    }
}
