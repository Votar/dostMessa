package entrego.com.entrego.web.model.response.profile;

import entrego.com.entrego.storage.model.UserProfileModel;
import entrego.com.entrego.web.model.response.common.FieldErrorResponse;

/**
 * Created by bertalt on 05.12.16.
 */

public class EntregoResultEditPassword {

    int code;
    String message;
    FieldErrorResponse[] fields;

    public EntregoResultEditPassword(int code, String message, FieldErrorResponse[] fields) {
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
}
