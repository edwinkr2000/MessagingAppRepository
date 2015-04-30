package com.netspacekenya.leftie.messagingapp;

/**
 * Created by Edwin on 23-Apr-15.
 */
public class Conversation {
    String title;
    String lastText;
    String pPic;
    String user_id;

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    int unreadMessages = 0;

    public Conversation(String title, String lastText, String pPic) {
        this.title = title;
        this.lastText = lastText;
        this.pPic = pPic;
    }
    public Conversation(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastText() {
        return lastText;
    }

    public void setLastText(String lastText) {
        this.lastText = lastText;
    }

    public String getpPic() {
        return pPic;
    }

    public void setpPic(String pPic) {
        this.pPic = pPic;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
