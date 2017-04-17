package com.entregoya.msngr.web.api

import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonElement
import com.entregoya.msngr.storage.model.UserProfileModel
import com.entregoya.msngr.storage.model.UserVehicleModel
import com.entregoya.msngr.ui.account.vehicle.edit.model.UserVehicle
import com.entregoya.msngr.web.model.request.auth.AuthBody
import com.entregoya.msngr.web.model.request.auth.RestorePasswordBody
import com.entregoya.msngr.web.model.request.common.ChangePasswordBody
import com.entregoya.msngr.web.model.request.delivery.ChangeStatusBody
import com.entregoya.msngr.web.model.request.delivery.EntregoResultGetDelivery
import com.entregoya.msngr.web.model.request.delivery.FinishDeliveryBody
import com.entregoya.msngr.web.model.request.profile.UploadPhotoBody
import com.entregoya.msngr.web.model.response.EntregoResult
import com.entregoya.msngr.web.model.request.registration.RegistrationBody
import com.entregoya.msngr.web.model.response.delivery.EntregoResultStatusChanged
import com.entregoya.msngr.web.model.response.delivery.EntregoResultHistoryDelivery
import com.entregoya.msngr.web.model.response.profile.*
import com.entregoya.msngr.web.model.response.registration.EntregoResultRegistration
import retrofit2.Call
import retrofit2.http.*

object EntregoApi {

    const val BASE_URL = "http://62.149.12.54/mobile-gateway-1.0.0-SNAPSHOT/"
    const val CONTENT_JSON = "content-type: application/json"
    const val TOKEN = "x-auth-token"

    object REQUESTS {
        const val AUTH = "login"
        const val REGISTRATION = "messenger/user/register"
        const val PROFILE = "messenger/user"
        const val VEHICLE = "messenger/user/vehicle"
        const val CHANGE_PROFILE = "messenger/user/change"
        const val CHANGE_PROFILE_PASSWORD = "messenger/user/change/password"
        const val GET_DELIVERY = "messenger/delivery"
        const val POST_LOCATION = "messenger/user/track"
        const val POST_CANCEL_DELIVERY = "messenger/delivery/{id}/cancel"
        const val POST_ACCEPT_DELIVERY = "messenger/delivery/{id}/accept"
        const val POST_DECLINE_DELIVERY = "messenger/delivery/{id}/decline"
        const val POST_CHANGE_STATUS = "messenger/delivery/{id}/status"
        const val POST_DRIVER_LICENCE = "messenger/user/change/driverLicense"
        const val POST_PERSON_LICENCE = "messenger/user/change/id"
        const val POST_USER_PHOTO = "messenger/user/change/photo"
        const val POST_FINISH_DELIVERY = "messenger/delivery/{id}/finish"
        const val POST_POST_RESTORE_PASSWORD = ""
        const val GET_USER_PHOTO = BASE_URL + "messenger/image/photo"
        const val GET_DRIVER_LICENCE = BASE_URL + "messenger/image/license"
        const val GET_ID_PHOTO =  BASE_URL + "messenger/image/id"
    }

    interface Authorization {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.AUTH)
        fun auth(@Body body: AuthBody): Call<EntregoResult>
    }

    interface Registration {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.REGISTRATION)
        fun registration(@Body body: RegistrationBody): Call<EntregoResultRegistration>
    }

    interface GetProfile {
        @Headers(CONTENT_JSON)
        @GET(REQUESTS.PROFILE)
        fun getProfile(@Header(TOKEN) token: String): Call<EntregoResultGetProfile>
    }

    interface GetVehicle {
        @Headers(CONTENT_JSON)
        @GET(REQUESTS.VEHICLE)
        fun getVehicle(@Header(TOKEN) token: String): Call<EntregoResultGetVehicle>
    }


    interface UpdateVehicle {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.VEHICLE)
        fun updateVehicle(@Header(TOKEN) token: String, @Body body: UserVehicleModel): Call<EntregoResultEditVehicle>
    }


    interface UpdateProfile {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.CHANGE_PROFILE)
        fun updateProfile(@Header(TOKEN) token: String, @Body body: UserProfileModel): Call<EntregoResultEditProfile>
    }

    interface UpdateProfilePassword {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.CHANGE_PROFILE_PASSWORD)
        fun updateProfile(@Header(TOKEN) token: String, @Body body: ChangePasswordBody): Call<EntregoResultEditPassword>
    }


    interface GetDelivery {
        @Headers(CONTENT_JSON)
        @GET(REQUESTS.GET_DELIVERY)
        fun getDelivery(@Header(TOKEN) token: String): Call<EntregoResultGetDelivery>
    }

    interface PostLocation {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.POST_LOCATION)
        fun postLocation(@Header(TOKEN) token: String, @Body location: LatLng): Call<EntregoResult>
    }

    interface CancelDelivery {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.POST_CANCEL_DELIVERY)
        fun cancelDelivery(@Header(TOKEN) token: String, @Path("id") deliveryId: Long): Call<EntregoResult>
    }

    interface AcceptDelivery {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.POST_ACCEPT_DELIVERY)
        fun acceptDelivery(@Header(TOKEN) token: String, @Path("id") deliveryId: Long): Call<EntregoResultStatusChanged>
    }

    interface DeclineDelivery {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.POST_DECLINE_DELIVERY)
        fun declineDelivery(@Header(TOKEN) token: String, @Path("id") deliveryId: Long): Call<EntregoResult>
    }

    interface ChangeStatus {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.POST_CHANGE_STATUS)
        fun changeStatus(@Header(TOKEN) token: String, @Path("id") deliveryId: Long, @Body body: ChangeStatusBody): Call<EntregoResultStatusChanged>
    }

    interface UploadDriverLicence {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.POST_DRIVER_LICENCE)
        fun postDriverLicence(@Header(TOKEN) token: String, @Body body: UploadPhotoBody): Call<EntregoResult>
    }

    interface UploadPersonLicence {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.POST_PERSON_LICENCE)
        fun postPersonLicence(@Header(TOKEN) token: String, @Body body: UploadPhotoBody): Call<EntregoResult>
    }

    interface UploadUserPhoto {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.POST_USER_PHOTO)
        fun postUserPhoto(@Header(TOKEN) token: String, @Body body: UploadPhotoBody): Call<EntregoResult>
    }

    interface FinishDelivery {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.POST_FINISH_DELIVERY)
        fun finishDelivery(@Header(TOKEN) token: String, @Path("id") deliveryId: Long, @Body body: FinishDeliveryBody): Call<EntregoResult>
    }

    interface RestorePassword {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.POST_POST_RESTORE_PASSWORD)
        fun restorePassword(@Body body: RestorePasswordBody): Call<EntregoResult>
    }
}