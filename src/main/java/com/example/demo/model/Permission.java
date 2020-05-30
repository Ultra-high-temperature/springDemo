package com.example.demo.model;


import java.util.Date;

public class Permission {
    int id;
    Date bantime;
    int permissions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBantime() {
        return bantime;
    }

    public void setBantime(Date beantime) {
        this.bantime = beantime;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }
}
