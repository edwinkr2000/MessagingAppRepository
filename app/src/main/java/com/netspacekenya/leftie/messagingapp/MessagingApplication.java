package com.netspacekenya.leftie.messagingapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * Created by Edwin on 07-Apr-15.
 */
public class MessagingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "6ZqP5trRAAZLoWZO02t7SpCgmkUqYNZ5LcKKN2Iw", "UetvXEQf02lrm9pLPYt0L3pjxVCPF6VXfChqXWcU");
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access while disabling public write access.
         defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);


    }
}
