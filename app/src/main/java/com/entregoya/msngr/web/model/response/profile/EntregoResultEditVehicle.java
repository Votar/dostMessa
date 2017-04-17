package com.entregoya.msngr.web.model.response.profile;

import com.entregoya.msngr.storage.model.UserVehicleModel;
import com.entregoya.msngr.web.model.response.common.FieldErrorResponse;


public class EntregoResultEditVehicle {

    int code;
    String message;
    UserVehicleModel payload;
    FieldErrorResponse[] fields;

    public EntregoResultEditVehicle(int code, String message, UserVehicleModel payload, FieldErrorResponse[] fields) {
        this.code = code;
        this.message = message;
        this.payload = payload;
        this.fields = fields;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public UserVehicleModel getPayload() {
        return payload;
    }

    public FieldErrorResponse[] getFields() {
        return fields;
    }
}
