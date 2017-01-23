package entrego.com.android.ui.account.files.model

/**
 * Created by Admin on 16.01.2017.
 */
interface UploadPhotoListener {
    fun successUploadFile()
    fun failureUploadFile(code: Int?, message: Int?)
}