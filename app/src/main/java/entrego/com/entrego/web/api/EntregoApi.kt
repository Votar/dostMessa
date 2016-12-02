package entrego.com.entrego.web.api

import com.google.gson.JsonElement
import entrego.com.entrego.web.model.request.auth.AuthBody
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
        const val GET_PROFILE = "messenger/user"
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
        @GET(REQUESTS.GET_PROFILE)
        fun getProfile(@Header(TOKEN) token: String): Call<EntregoResultGetProfile>
    }

    interface GetVehicle {
        @Headers(CONTENT_JSON)
        @GET(REQUESTS.GET_PROFILE)
        fun getVehicle(@Header(TOKEN) token: String): Call<EntregoResultGetVehicle>
    }





}