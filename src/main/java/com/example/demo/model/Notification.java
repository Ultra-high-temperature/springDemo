package com.example.demo.model;

public class Notification {
    private long id;
    private long notifier;
    private long receiver;
    private long outerId;
    private int type;
    private long gmt_create;
    private long status;

    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNotifier() {
        return notifier;
    }

    public void setNotifier(long notifier) {
        this.notifier = notifier;
    }

    public long getOuterId() {
        return outerId;
    }

    public void setOuterId(long outerId) {
        this.outerId = outerId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
