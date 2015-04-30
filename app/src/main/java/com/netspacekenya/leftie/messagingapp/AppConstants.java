package com.netspacekenya.leftie.messagingapp;

/**
 * Created by Edwin on 10-Apr-15.
 */
public final class AppConstants {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_FRIEND_RELATION = "friend";
    public static final String CLASS_MESSAGE="Message";
    public static final String KEY_SENDER_ID="sender_id";
    public static final String KEY_RECIPIENT_ID="recipient_id";
    public static final String KEY_MESSAGE_TEXT = "message_text";

    public static final String KEY_MESSAGE_TYPE="message_type";
    public static final String KEY_MESSAGE="message";
    public static final String KEY_CREATED_AT="createdAt";
    public static final String KEY_FIRST_NAME="first_name";
    public static final String KEY_LAST_NAME ="last_name";
    public static final String KEY_PROFILE_PICTURE= "profile_picture";

    ///DB
    public static final String FRIENDS_TABLE = "FRIENDS_TABLE";
    public static final String COLUMN_FRIEND_ID= "FRIEND_ID";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_P_PIC_ID = "P_PIC_ID";
    public static final String COLUMN_FRIEND_STATUS="FRIEND_STATUS";

    public static final String MESSAGES_TABLE = "MESSAGES_TABLE";
    public static final String COLUMN_TEXT = "MESSAGE_TEXT";
    public static final String COLUMN_SENDER_ID = "SENDER_ID";
    public static final String COLUMN_RECIPIENT_ID = "RECIPIENT_ID";
    public static final String COLUMN_MEDIA_ID = "MEDIA_ID";

    public static final int TAKE_PHOTO_REQUESTCODE = 111;
    public static final int GALLERY_REQUEST_CODE = 222;
    public static final String TYPE_IMAGE = "image";

    ///CLOUD
    public static final String KEY_MESSAGING_CHANNEL = "MESSAGING_CHANNEL";
    public static final String KEY_CURRENT_USER = "CURRENT_USER";
    public static final String KEY_STATUS = "current_status";
    public static final String COLUMN_IS_READ = "is_read";
}
