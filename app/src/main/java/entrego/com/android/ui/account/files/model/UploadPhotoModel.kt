package entrego.com.android.ui.account.files.model

import android.graphics.Bitmap
import android.util.Base64
import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.profile.UploadPhotoBody
import entrego.com.android.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset


object UploadPhotoModel {

    fun sendPicture(token: String, picture: Bitmap, pictureCategory: EntregoFileCategory, listener: UploadPhotoListener?) {

        val baos = ByteArrayOutputStream()
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
        val byteArray = baos.toByteArray()
        val photoEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
        var uploadFileRequest: Call<EntregoResult>? = null

        val requestBody = UploadPhotoBody(photoEncoded)
        when (pictureCategory) {
            EntregoFileCategory.DRIVER_LICENCE -> {
                uploadFileRequest = ApiCreator.server.create(EntregoApi.UploadDriverLicence::class.java)
                        .postDriverLicence(token, requestBody)
            }
            EntregoFileCategory.PERSON_LICENCE -> {
                uploadFileRequest = ApiCreator.server.create(EntregoApi.UploadPersonLicence::class.java)
                        .postPersonLicence(token, requestBody)
            }
            EntregoFileCategory.USER_PHOTO -> {
                uploadFileRequest = ApiCreator.server.create(EntregoApi.UploadUserPhoto::class.java)
                        .postUserPhoto(token, requestBody)

            }
            else -> TODO()
        }

        uploadFileRequest.enqueue(object : Callback<EntregoResult> {
            override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                response?.body()?.apply {
                    when (code) {
                    0 -> listener?.successUploadFile()
                    else -> listener?.failureUploadFile(code, null)
                }
                }
            }

            override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                listener?.failureUploadFile(null, null)
            }
        })


    }
}