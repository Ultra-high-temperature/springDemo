package com.example.demo.enums;

public enum NotificationTypeEnum {
    REPLAY_QUESTION(1,"回复了问题"),
    REPLAY_COMMENT(2,"回复了回复"),
    ;
    private int Type;
    private String name;

    public int getType() {
        return Type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int status, String name) {
        this.Type = status;
        this.name = name;
    }
}
