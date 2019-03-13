package com.example.roshan.chatapplication.Model;

/**
 * Created by Roshan on 11-03-2019.
 */

public class Messages {
    public String message;
    public int id;
    public String fromUserId;
    public String toUserId;
    public String createdDateTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromUSerId() {
        return fromUserId;
    }

    public void setFromUSerId(String fromUSerId) {
        this.fromUserId = fromUSerId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
    // public String timestamp;

    public String getText() {
        return message;
    }

    public void setText(String text) {
        this.message = text;
    }

    public String getTimestamp() {
        return createdDateTime;
    }

    public void setTimestamp(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}

