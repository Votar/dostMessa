package com.entregoya.msngr.web.model.response.profile;

import com.entregoya.msngr.storage.model.UserProfileModel;
import com.entregoya.msngr.web.model.response.common.FieldErrorResponse;

/**
 * Created by bertalt on 05.12.16.
 */

public class EntregoResultEditProfile {

    int code;
    String message;
    UserProfileModel payload;
    FieldErrorResponse[] fields;

    public EntregoResultEditProfile(int code, String message, UserProfileModel payload, FieldErrorResponse[] fields) {
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

    public UserProfileModel getPayload() {
        return payload;
    }

    public FieldErrorResponse[] getFields() {
        return fields;
    }
}
