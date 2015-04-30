package messagingapp.datastorage;

import android.content.Context;
import android.os.AsyncTask;

import com.parse.ParseUser;

/**
 * Created by Edwin on 25-Apr-15.
 */
public class RemoveFriendTask extends AsyncTask<Object, Void, String> {
    Context context;
    ParseUser friend;
    DBops dbops;
    CloudOps cloudops;

    public RemoveFriendTask(Context context, ParseUser friend) {
        this.context = context;
        this.friend = friend;
        dbops = new DBops(context);
        cloudops = new CloudOps();
    }

    @Override
    protected String doInBackground(Object... params) {
        if(cloudops.removeFriend(friend)){
            dbops.removeFriend(friend);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
