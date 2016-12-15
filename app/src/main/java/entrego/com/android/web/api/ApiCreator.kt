package entrego.com.android.web.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by bertalt on 29.11.16.
 */
object ApiCreator {
    val server: Retrofit

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //Logger interceptor
        val client = OkHttpClient.Builder()
        client.addInterceptor(loggingInterceptor)

        server = Retrofit.Builder()
                .baseUrl(EntregoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
    }



}