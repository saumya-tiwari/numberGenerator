package com.assignment.numberGenerator.exception;

public class ApiException  extends Exception{

    private String code;
    private String message;

    public ApiException(String message, String code, Throwable e) {
        super(message,e);
        this.code = code;
    }
}
