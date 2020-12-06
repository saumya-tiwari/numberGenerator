package com.assignment.numberGenerator.domain;

public class Violation {


    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    private  String fieldName;

    private  String message;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
