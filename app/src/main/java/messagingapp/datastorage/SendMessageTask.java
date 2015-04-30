package messagingapp.datastorage;

import android.os.AsyncTask;

import com.netspacekenya.leftie.messagingapp.AppConstants;
import com.netspacekenya.leftie.messagingapp.ConversationActivity;
import com.netspacekenya.leftie.messagingapp.Message;
import com.parse.ParseObject;

/**
 * Created by Edwin on 24-Apr-15.
 */
public class SendMessageTask extends AsyncTask<Object, Void, String> {
    ConversationActivity context;
    Message message;
    DBops dbops;
    CloudOps cloudops;


    String friend_id;

    public SendMessageTask(ConversationActivity context, Message message, String friend_id) {
        this.context = context;
        this.message = message;


        this.friend_id = friend_id;
        dbops = new DBops(context);
        cloudops = new CloudOps();
    }

    @Override
    protected String doInBackground(Object... params) {
        ParseObject m = new ParseObject(AppConstants.CLASS_MESSAGE);
        //set message as read
        message.setRead(true);
        m.put(AppConstants.KEY_SENDER_ID, message.getSender_id());
        m.put(AppConstants.KEY_RECIPIENT_ID, message.getRecipient_id());
        m.put(AppConstants.KEY_MESSAGE_TEXT, message.getText());


        message.setMessage_id(m.getObjectId());
        if(cloudops.saveMessageToCloud(m)) {
            dbops.saveMessage(message);
            cloudops.pushMessage(message);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        context.refresh();

    }
}
