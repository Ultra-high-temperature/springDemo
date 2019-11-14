package com.example.demo.exception;

public enum CustomErrorCode implements ICustomErrorCode{
    UNKNOWN_ERROR(2100,"未知异常"),
    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不换个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行评论"),
    NO_LOGIN(2003,"未登录，不能进行评论,请先登录"),
    TYPE_PARAM_WRONG(2004,"评论类型错误"),
    COMMENT_NOT_FOUND(2005,"评论不存在"),
    ;

    private Integer code;
    private String message;

    CustomErrorCode(Integer code, String message) {
        this.message=message;
        this.code=code;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
