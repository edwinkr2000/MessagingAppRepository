package com.netspacekenya.leftie.messagingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Edwin on 07-Apr-15.
 */
public class LogInActivity extends Activity{
    EditText email_et;
    EditText password_et;
    Button log_in_button;
    TextView sign_up_tv;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.log_in_layout);
        email_et = (EditText) findViewById(R.id.email_et);
        password_et = (EditText) findViewById(R.id.password_et);
        log_in_button = (Button) findViewById(R.id.log_in_button);
        sign_up_tv = (TextView) findViewById(R.id.sign_up_tv);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailAdress = email_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();

                if(emailAdress.isEmpty()||password.isEmpty()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LogInActivity.this);
                    dialog.setTitle(R.string.log_in_error_title);
                    dialog.setMessage("Please check your email and password, and try logging in");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    ParseUser user = new ParseUser();


                    user.logInInBackground(emailAdress, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            progressBar.setVisibility(View.GONE);

                            if(e==null){
                                //success
                                Intent inboxIntent  = new Intent(LogInActivity.this, MainActivity.class);
                                inboxIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                inboxIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(inboxIntent);

                            }
                            else {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(LogInActivity.this);
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
        sign_up_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
