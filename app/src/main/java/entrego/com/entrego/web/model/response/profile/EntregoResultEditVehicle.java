package entrego.com.entrego.web.model.response.profile;

import entrego.com.entrego.storage.model.UserVehicleModel;
import entrego.com.entrego.web.model.response.common.FieldErrorResponse;

/**
 * Created by bertalt on 05.12.16.
 */

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
