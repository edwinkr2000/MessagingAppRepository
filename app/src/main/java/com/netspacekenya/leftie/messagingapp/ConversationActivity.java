package com.netspacekenya.leftie.messagingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edwin on 14-Apr-15.
 */
public class ConversationActivity extends ActionBarActivity {
    EditText message_et;
    RecyclerView messages_rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<String> messageList;
    static int mPointer=0;
    InputMethodManager imm;
    String recipientUserName;
    ParseUser currentUser;


    public final int TAKE_PHOTO_REQUESTCODE=001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.conversation_layout);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        currentUser = ParseUser.getCurrentUser();
        imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        recipientUserName =  getIntent().getStringExtra("clicked userName");

        messageList = new ArrayList<String>();
        message_et = (EditText) findViewById(R.id.message_et);
        messages_rv = (RecyclerView) findViewById(R.id.messages_rv);

        layoutManager = new LinearLayoutManager(this);


        messages_rv.setLayoutManager(layoutManager);
        adapter = new MessagesAdapter(messageList);
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
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File myPhotoDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                                                                                getResources().getString(R.string.app_name));
        if(!myPhotoDir.exists()){
            //dir doesn't exist, create new folder
           if(!myPhotoDir.mkdir()){
               //error creating dirs
               return false;
           }
        }


        File photo = new File(myPhotoDir, "img_"+new SimpleDateFormat("yyyyMMdd_HHmmSS", Locale.getDefault()).format(new Date())+".jpg");

        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(photoIntent, TAKE_PHOTO_REQUESTCODE);
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TAKE_PHOTO_REQUESTCODE && resultCode==RESULT_OK){
            Toast.makeText(this, "Photo successfully taken", Toast.LENGTH_SHORT).show();

        }
    }
    public void onSendButtonClicked(View v){
        String message = message_et.getText().toString().trim();
        if(!message.equals("")){
            saveMessageToCloud(message);
            message_et.setText("");
            imm.hideSoftInputFromWindow(message_et.getWindowToken(), 0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.conversation_layout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void saveMessageToCloud(final String message){

        ParseObject messageObject = new ParseObject(AppConstants.CLASS_MESSAGE);
        messageObject.put(AppConstants.KEY_SENDER_ID,currentUser.getUsername());
        messageObject.put(AppConstants.KEY_RECIPIENT_ID,recipientUserName);
        messageObject.put(AppConstants.KEY_MESSAGE, message);

        messageObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    ///success
                    messageList.add(message);
                    adapter.notifyDataSetChanged();
                }
                else{
                    ///fail
                    Toast.makeText(ConversationActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });

    }
    private boolean loadMessagesFromCloud(){


        return false;
    }
}
