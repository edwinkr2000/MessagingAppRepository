package com.netspacekenya.leftie.messagingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.parse.ParseUser;

import messagingapp.datastorage.AddFriendTask;

/**
 * Created by Edwin on 14-Apr-15.
 */
public class ContactDialogFragment extends android.support.v4.app.DialogFragment {
    String [] dialogList= {"Add Friend", "View Profile", "View Conversation", "Remove Friend", "Block Contact"};
    ContactDialogListener listener;
    int selectedUserPosition;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog  = new AlertDialog.Builder(getActivity());
        dialog.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,dialogList), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int clicked) {
                dialog.dismiss();
                if(clicked==0) {

                    selectedUserPosition = getArguments().getInt("position");
                    ParseUser selectedUser = AllContactsFragment.allContacts.get(selectedUserPosition);
                    AddFriendTask fTask = new AddFriendTask(getActivity(), selectedUser);
                    fTask.execute();

                }

            }
        });

        return dialog.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ContactDialogListener) activity;
    }
}
