package messagingapp.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.netspacekenya.leftie.messagingapp.AppConstants;

/**
 * Created by Edwin on 21-Apr-15.
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "CORE_DATABASE";
    static final int DB_VERSION = 1;

    ///Friend's table

    public DataBaseOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateFriendsTableQuery());
        db.execSQL(getCreateMessagesTableQuery());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private String getCreateFriendsTableQuery(){

                return "CREATE TABLE " + AppConstants.FRIENDS_TABLE +
                        " (" + AppConstants.COLUMN_FRIEND_ID + " TEXT PRIMARY KEY," +
                        AppConstants.COLUMN_FIRST_NAME + " TEXT," +
                        AppConstants.COLUMN_LAST_NAME + " TEXT," +
                       AppConstants.COLUMN_P_PIC_ID+ " TEXT," +
                       AppConstants.COLUMN_FRIEND_STATUS+ " TEXT" + ")";
    }
    private String getCreateMessagesTableQuery(){
        return "CREATE TABLE " + AppConstants.MESSAGES_TABLE +
                " (" + BaseColumns._ID + " TEXT PRIMARY KEY," +
                AppConstants.COLUMN_SENDER_ID + " TEXT," +
                AppConstants.COLUMN_RECIPIENT_ID + " TEXT," +
                AppConstants.COLUMN_TEXT + " TEXT," +
                AppConstants.COLUMN_MEDIA_ID +" TEXT,"+
                AppConstants.COLUMN_IS_READ+" TEXT)";
    }
}
