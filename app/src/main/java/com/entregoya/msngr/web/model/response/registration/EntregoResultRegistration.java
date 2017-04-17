package com.entregoya.msngr.web.model.response.registration;

import java.util.Arrays;

import com.entregoya.msngr.web.model.response.common.FieldErrorResponse;


public class EntregoResultRegistration {

    private int code;
    private String message;
    private FieldErrorResponse[] fields;


    public EntregoResultRegistration(int code, String message, FieldErrorResponse[] fields) {
        this.code = code;
        this.message = message;
        this.fields = fields;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public FieldErrorResponse[] getFields() {
        return fields;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFields(FieldErrorResponse[] fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "EntregoResultRegistration{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", fields=" + Arrays.toString(fields) +
                '}';
    }
}
