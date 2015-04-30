package messagingapp.datastorage;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.netspacekenya.leftie.messagingapp.AppConstants;
import com.parse.ParseUser;

/**
 * Created by Edwin on 24-Apr-15.
 */
public class AddFriendTask extends AsyncTask<Object, Void, String> {
    Context context;
    ParseUser friend;
    DBops dbops;
    CloudOps cloudops;

    public AddFriendTask(Context context, ParseUser friend) {
        this.context = context;
        this.friend = friend;
        dbops = new DBops(context);
        cloudops = new CloudOps();
    }

    @Override
    protected String doInBackground(Object... params) {
        if(cloudops.addFriend(friend)){
            dbops.saveFriend(friend);

        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, "Successfully added " + friend.getString(AppConstants.KEY_FIRST_NAME) + " as friend", Toast.LENGTH_LONG).show();

    }
}
