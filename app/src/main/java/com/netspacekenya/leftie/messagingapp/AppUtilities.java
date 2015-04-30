package com.netspacekenya.leftie.messagingapp;

import android.os.Environment;

/**
 * Created by Edwin on 15-Apr-15.
 */
public  class AppUtilities {

    public static boolean isExternalStorageAvailable(){
        String extStorageState = Environment.getExternalStorageState();
        if(extStorageState.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
            return false;

    }

}
