package com.netspacekenya.leftie.messagingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.List;

import messagingapp.datastorage.DBops;
import messagingapp.datastorage.SendMessageTask;

/**
 * Created by Edwin on 14-Apr-15.
 */
public class ConversationActivity extends ActionBarActivity {
    EditText message_et;
    RecyclerView messages_rv;
    RecyclerView.LayoutManager layoutManager;
    MessagesAdapter adapter;
    List<Message> messageList;
    InputMethodManager imm;
    String recipientid;
    ParseUser currentUser;
    DBops dBops;


    public final int TAKE_PHOTO_REQUESTCODE=001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.conversation_layout);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        currentUser = ParseUser.getCurrentUser();
        imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        recipientid=  getIntent().getStringExtra("clicked userName");

        ///get list from db
        dBops = new DBops(this);
        messageList = dBops.getConversationForFriend(recipientid);
        message_et = (EditText) findViewById(R.id.message_et);
        messages_rv = (RecyclerView) findViewById(R.id.messages_rv);

        layoutManager = new LinearLayoutManager(this);


        messages_rv.setLayoutManager(layoutManager);
        adapter = new MessagesAdapter(this, messageList);
        messages_rv.setAdapter(adapter);

    }
    public void onCameraButtonClicked(View v){

        if(AppUtilities.isExternalStorageAvailable()) {
           if(!takePhoto()){
               Toast.makeText(this, "Error taking photo", Toast.LENGTH_SHORT).show();
           }
        }
        else{
            ///Ext. storage unavailable
            Toast.makeText(this, "Please ensure storage is available", Toast.LENGTH_SHORT).show();
        }

    }
    private boolean takePhoto(){
   return false;
    }

    public void onSendButtonClicked(View v){
        String message = message_et.getText().toString().trim();
        if(!message.equals("")){
            sendTextMessage(message);

           }
        message_et.setText("");
        imm.hideSoftInputFromWindow(message_et.getWindowToken(), 0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.conversation_layout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void sendTextMessage(String text){

        Message m = new Message(text, currentUser.getObjectId(), recipientid);
        SendMessageTask mTask = new SendMessageTask(this, m, recipientid);
        mTask.execute();
    }
    public void refresh(){
        adapter.setData(new DBops(this).getConversationForFriend(recipientid));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TAKE_PHOTO_REQUESTCODE && resultCode==RESULT_OK){
            Toast.makeText(this, "Photo successfully taken", Toast.LENGTH_SHORT).show();

        }
    }

}
