package com.example.demo.dto;


import com.example.demo.model.User;

public class NotificationDTO {
    private long id;
    private long gmt_create;
    private long status;
    private String outerTitle;
    private int outerId;
    private int type;

//    private long id;
//    private long notifier; //***
//    private long receiver;
//    private long outerId;
//    private int type;
//    private long gmt_create;
//    private long status;

    private User notifier;//***

    public int getOuterId() {
        return outerId;
    }

    public void setOuterId(int outerId) {
        this.outerId = outerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public User getNotifier() {
        return notifier;
    }

    public void setNotifier(User notifier) {
        this.notifier = notifier;
    }

    public String getOuterTitle() {
        return outerTitle;
    }

    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
