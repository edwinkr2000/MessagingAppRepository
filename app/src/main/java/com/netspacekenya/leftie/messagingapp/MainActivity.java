package com.netspacekenya.leftie.messagingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Edwin on 03-Apr-15.
 */
public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, ContactDialogListener{

    ViewPager pager;
    ParseRelation<ParseUser> parseRelation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        pager = (ViewPager) findViewById(R.id.pager);
        setUpTabs(getSupportActionBar());
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                getSupportActionBar().setSelectedNavigationItem(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
       pager.setCurrentItem(0);

        ///load friends relation



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.log_out){
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        ///success
                        Toast.makeText(MainActivity.this, "Successfully logged out", Toast.LENGTH_LONG).show();
                        Intent logIn = new Intent(MainActivity.this, LogInActivity.class);
                        logIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        logIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logIn);
                    }
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
    private void setUpTabs(ActionBar actionBar){
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab messagesTab = actionBar.newTab().setContentDescription("Messages").setIcon(R.drawable.ic_action_email);
        ActionBar.Tab contactsTab = actionBar.newTab().setContentDescription("Friends").setIcon(R.drawable.ic_action_group);
        ActionBar.Tab addFriendTab  = actionBar.newTab().setContentDescription("All Contacts").setIcon(R.drawable.ic_action_add_group);
        ActionBar.Tab miscTab = actionBar.newTab().setContentDescription("Misc.").setIcon(R.drawable.ic_action_attachment);
        messagesTab.setTabListener(this);
        contactsTab.setTabListener(this);
        addFriendTab.setTabListener(this);
        miscTab.setTabListener(this);
        actionBar.addTab(messagesTab);
        actionBar.addTab(contactsTab);
        actionBar.addTab(addFriendTab);
        actionBar.addTab(miscTab);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        switch(tab.getContentDescription().toString()){
            case "Messages" :
                pager.setCurrentItem(0);
                break;
            case "Friends" :
                pager.setCurrentItem(1);
                break;
            case "All Contacts" :
                pager.setCurrentItem(2);
                break;
            case "Misc." :
                pager.setCurrentItem(3);
                break;


        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    @Override
    public void onContactDialogOptionSelected(int dialogOptionSelected, int parseUserPosition) {
        parseRelation = ParseUser.getCurrentUser().getRelation(AppConstants.KEY_FRIEND_RELATION);

        final ParseUser selectedUser = AllContactsFragment.allContacts.get(parseUserPosition);
        switch (dialogOptionSelected){
            case 0:
                //add user as friend

                parseRelation.add(selectedUser);

                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            //part 1 success
                        }
                    }
                });
                selectedUser.getRelation(AppConstants.KEY_FRIEND_RELATION).add(ParseUser.getCurrentUser());
                selectedUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            ///successfully created both relations
                            Toast.makeText(MainActivity.this, "Successfully added "
                                    +selectedUser.getUsername() + " as friend" ,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                break;

            case 1:
            case 2:
            case 3:
                ///remove friend
                parseRelation.remove(selectedUser);
                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){

                        }
                        else {
                            ///fail
                            Toast.makeText(MainActivity.this, "Error removing " + selectedUser.getUsername() +" as friend", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ParseRelation<ParseUser> rel = selectedUser.getRelation(AppConstants.KEY_FRIEND_RELATION);

                rel.remove(ParseUser.getCurrentUser());
                selectedUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){

                            ///success
                            Toast.makeText(MainActivity.this, "Success removing " + selectedUser.getUsername() +
                                       " as friend", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


                break;
            case 4:

        }

    }
}