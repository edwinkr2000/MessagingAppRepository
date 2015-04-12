package com.netspacekenya.leftie.messagingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Edwin on 07-Apr-15.
 */
public class SignUpActivity extends ActionBarActivity {
    EditText first_name_et;
    EditText last_name_et;
    EditText email_et;
    EditText password_et;
    Button sign_up_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_up_layout);

        first_name_et = (EditText) findViewById(R.id.first_name_tv);
        last_name_et = (EditText) findViewById(R.id.last_name_tv);
        email_et = (EditText) findViewById(R.id.email_et);
        password_et = (EditText) findViewById(R.id.password_et);
        sign_up_button = (Button) findViewById(R.id.sign_up_button);

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();

                if(email.isEmpty()||password.isEmpty()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignUpActivity.this);
                    dialog.setTitle(R.string.log_in_error_title);
                    dialog.setMessage("Please check your email and password details, and try signing up");
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
                    setProgressBarIndeterminateVisibility(true);

                    ParseUser user = new ParseUser();
                    user.setEmail(email);
                    user.setUsername(email.substring(0, email.indexOf('@')));
                    user.setPassword(password);
//                    user.put("first_name", first_name_et.getText());
//                    user.put("last_name", last_name_et.getText());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            setProgressBarIndeterminateVisibility(false);

                            if(e==null){
                                ///success
                                Toast.makeText(SignUpActivity.this, "Success creating account, redirecting to inbox!", Toast.LENGTH_LONG).show();
                                Intent inboxIntent  = new Intent(SignUpActivity.this, MainActivity.class);
                                inboxIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                inboxIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(inboxIntent);
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
}
