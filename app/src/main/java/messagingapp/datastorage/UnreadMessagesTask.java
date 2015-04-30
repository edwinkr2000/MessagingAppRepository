package messagingapp.datastorage;

import android.content.Context;
import android.os.AsyncTask;

import com.netspacekenya.leftie.messagingapp.Message;
import com.netspacekenya.leftie.messagingapp.MessagesFragment;

import java.util.List;

/**
 * Created by Edwin on 27-Apr-15.
 */
public class UnreadMessagesTask extends AsyncTask<Object, Void, String> {
    Context context;
    DBops dBops;
    CloudOps cloudOps;
    MessagesFragment frag;
    public UnreadMessagesTask(Context context, MessagesFragment frag) {
        this.context = context;
        this.frag = frag;
        dBops = new DBops(context);
        cloudOps = new CloudOps();

    }


    @Override
    protected String doInBackground(Object... params) {
        List<Message> readMessages = dBops.getAllReceivedMessages();
        List<Message> unreadMessages = cloudOps.getUnreadMessages(readMessages.size());
        if(unreadMessages!=null) {
            for (Message m : unreadMessages) {
                dBops.saveMessage(m);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        frag.refresh();

    }
}
