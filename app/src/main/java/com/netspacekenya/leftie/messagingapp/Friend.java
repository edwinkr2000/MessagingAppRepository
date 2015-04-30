package com.netspacekenya.leftie.messagingapp;

/**
 * Created by Edwin on 21-Apr-15.
 */
public class Friend {
    private String friendID;
    private String firstName;
    private String lastName;
    private String pPicID;
    private String status;

    public Friend(String friendID, String firstName, String lastName, String pPicID, String status) {
        this.friendID = friendID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pPicID = pPicID;
        this.status = status;
    }

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getpPicID() {
        return pPicID;
    }

    public void setpPicID(String pPicID) {
        this.pPicID = pPicID;
    }
}
