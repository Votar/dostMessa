package entrego.com.android.web.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiCreator {
    val server: Retrofit

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //Logger interceptor
        val client = OkHttpClient.Builder()
        client.addInterceptor(loggingInterceptor)
        client.readTimeout(1, TimeUnit.MINUTES)
        client.writeTimeout(1, TimeUnit.MINUTES)
        client.connectTimeout(1, TimeUnit.MINUTES)

        server = Retrofit.Builder()
                .baseUrl(EntregoApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
    }


}