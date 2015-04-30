package com.netspacekenya.leftie.messagingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.File;

/**
 * Created by Edwin on 07-Apr-15.
 */
public class SignUpActivity extends ActionBarActivity {
    EditText first_name_et;
    EditText last_name_et;
    EditText email_et;
    EditText password_et;
    Button sign_up_button;
    ImageView ppic_iv;
    TextView choose_pic_tv;
    boolean pictureSelected;
    Uri pic_uri;
    byte[] photoBytes;
    ProgressBar ppic_bar;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.sign_up_layout);

        first_name_et = (EditText) findViewById(R.id.first_name_tv);
        last_name_et = (EditText) findViewById(R.id.last_name_tv);
        email_et = (EditText) findViewById(R.id.email_et);
        password_et = (EditText) findViewById(R.id.password_et);
        sign_up_button = (Button) findViewById(R.id.sign_up_button);
        ppic_iv = (ImageView) findViewById(R.id.ppic_iv);
        choose_pic_tv = (TextView) findViewById(R.id.choose_pic_tv);
        ppic_bar = (ProgressBar) findViewById(R.id.ppic_bar);

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();

                if(email.isEmpty()||password.isEmpty()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignUpActivity.this);
                    dialog.setTitle(R.string.log_in_error_title);
                    dialog.setMessage("Please check your email and password details, and try signing up again");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.create();
                    dialog.show();

                }
                else{
                    setSupportProgressBarIndeterminate(true);
                    setSupportProgressBarIndeterminateVisibility(true);

                    final ParseUser user = new ParseUser();
                    user.setEmail(email);
                    user.setUsername(email.substring(0, email.indexOf('@')));
                    user.setPassword(password);
                    user.put(AppConstants.KEY_FIRST_NAME, first_name_et.getText().toString());
                    user.put(AppConstants.KEY_LAST_NAME, last_name_et.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){
                                ///success
                                //save ppic
                                byte[] p = getProfilePicture();
                                if(p!=null){
                                    user.put(AppConstants.KEY_PROFILE_PICTURE, new ParseFile(p));
                                    user.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if(e==null){
                                                ///ppic successfully saved; save uri
                                                 prefs = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
                                                SharedPreferences.Editor editor = prefs.edit();
                                                editor.putString("profile picture", pic_uri.toString());
                                                editor.commit();
                                                Toast.makeText(SignUpActivity.this, "Success creating account, redirecting to inbox!", Toast.LENGTH_LONG).show();
                                                Intent inboxIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                                inboxIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                inboxIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(inboxIntent);

                                            }
                                            else{
                                                Toast.makeText(SignUpActivity.this, "Error Signing up, make sure you've a working internet connection", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                                else {

                                    Toast.makeText(SignUpActivity.this, "Success creating account, redirecting to inbox!", Toast.LENGTH_LONG).show();
                                    Intent inboxIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                    inboxIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    inboxIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(inboxIntent);
                                }

                            }
                            else{
                                AlertDialog.Builder dialog = new AlertDialog.Builder(SignUpActivity.this);
                                dialog.setTitle(R.string.log_in_error_title);
                                dialog.setMessage(e.getMessage());
                                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.create();
                                dialog.show();


                            }

                        }
                    });
                }
            }
        });

    }
    public void onChooseProfilePictureButtonClicked(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] cameraOptions = {"Choose from gallery", "Take Picture"};
        builder.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cameraOptions), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which){
                    case 0 :
                        openGallery();
                        break;
                    case 1 :
                        takePhoto();
                        break;
                }
            }
        });
        builder.create();
        builder.show();

    }
    private boolean takePhoto(){
        if(AppUtilities.isExternalStorageAvailable()) {
            Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File photoDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getResources().getString(R.string.app_name));
            if(photoDir.exists()){
                ///exists
                File photo = new File(photoDir , "ppic.jpg");
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                startActivityForResult(photoIntent, AppConstants.TAKE_PHOTO_REQUESTCODE);
                return true;
            }
            else{
                ///dir doesnt exist, create
                if(!photoDir.mkdirs()){
                    return false;
                }
                else{
                    File photo = new File(photoDir ,"ppic.jpg");
                    photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                    startActivityForResult(photoIntent, AppConstants.TAKE_PHOTO_REQUESTCODE);
                    return true;
                }
            }
        }
         return false;

    }
    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, AppConstants.GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ppic_bar.setVisibility(View.VISIBLE);

        if(requestCode==AppConstants.TAKE_PHOTO_REQUESTCODE && resultCode==RESULT_OK){
//            ///add photo to gallery
//            Intent scan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            scan.setData(data.getData());
//            sendBroadcast(scan);
            pic_uri = data.getData();
            photoBytes = FileHelper.getByteArrayFromFile(this, pic_uri);
            pictureSelected = true;
            ppic_iv.setImageURI(data.getData());
            ppic_bar.setVisibility(View.GONE);
        }
        else if(requestCode==AppConstants.GALLERY_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            pic_uri = data.getData();
            photoBytes = FileHelper.getByteArrayFromFile(this, pic_uri);
            pictureSelected = true;
            ppic_iv.setImageURI(data.getData());
            ppic_bar.setVisibility(View.GONE);

        }
    }
    private byte[] getProfilePicture(){
        byte[] reduced=null;
        if(pictureSelected && pic_uri!=null && photoBytes!=null){
            reduced = FileHelper.reduceImageForUpload(photoBytes);

        }
        else{
            Log.e("photo error", "error taking profile picture");
        }
        return reduced;
    }
}
