package com.netspacekenya.leftie.messagingapp;

/**
 * Created by Edwin on 21-Apr-15.
 */
public class Message {
    private String text;
    private String sender_id;
    private String recipient_id;
    private String media_id;
    private String message_id;
    private boolean isRead = false;

    public Message(){

    }
    public Message(String text){
        this.text = text;

    }
    public Message(String text, String sender_id, String recipient_id){
        this.text = text;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        }
    public Message(String text, String sender_id, String recipient_id, String media_id){
        this.text = text;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.media_id = media_id;
    }
    public String getMessage_id(){return message_id;}
    public void setMessage_id(String message_id){this.message_id = message_id;}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public boolean isRead(){return isRead;}

    public void read(){this.isRead = true;}
    public void setRead(boolean b) {this.isRead = b;}
}
