package entrego.com.android.web.model.response.common

/**
 * Created by bertalt on 05.12.16.
 */
class FieldErrorResponse(val field: String, val message:String) {

    override fun toString(): String {
        return "FieldErrorResponse(field='$field', message='$message')"
    }

}