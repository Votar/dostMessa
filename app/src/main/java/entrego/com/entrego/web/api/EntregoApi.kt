package entrego.com.entrego.web.api

import com.google.gson.JsonElement
import entrego.com.entrego.storage.model.UserProfileModel
import entrego.com.entrego.storage.model.UserVehicleModel
import entrego.com.entrego.ui.main.account.vehicle.edit.model.UserVehicle
import entrego.com.entrego.web.model.request.auth.AuthBody
import entrego.com.entrego.web.model.request.common.ChangePasswordRequest
import entrego.com.entrego.web.model.response.EntregoResult
import entrego.com.entrego.web.model.request.registration.RegistrationBody
import entrego.com.entrego.web.model.response.profile.EntregoResultGetProfile
import entrego.com.entrego.web.model.response.profile.EntregoResultGetVehicle
import entrego.com.entrego.web.model.response.registration.EntregoResultRegistration
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by bertalt on 29.11.16.
 */
object EntregoApi {


    const val BASE_URL = "http://10.0.2.2:8080/mobile-gateway/"
    const val CONTENT_JSON = "content-type: application/json"
    const val TOKEN = "x-auth-token"

    object REQUESTS {
        const val AUTH = "login"
        const val REGISTRATION = "messenger/user/register"
        const val PROFILE = "messenger/user"
        const val VEHICLE = "messenger/user/vehicle"
        const val CHANGE_PROFILE ="messenger/user/change"
        const val CHANGE_PROFILE_PASSWORD="messenger/user/change/password"

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
        fun updateVehicle(@Header(TOKEN) token: String, @Body body: UserVehicleModel): Call<EntregoResultGetVehicle>
    }


    interface UpdateProfile {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.CHANGE_PROFILE)
        fun updateProfile(@Header(TOKEN) token: String, @Body body: UserProfileModel): Call<EntregoResultGetProfile>
    }

    interface UpdateProfilePassword {
        @Headers(CONTENT_JSON)
        @POST(REQUESTS.CHANGE_PROFILE_PASSWORD)
        fun updateProfile(@Header(TOKEN) token: String, @Body body: ChangePasswordRequest): Call<EntregoResult>
    }





}