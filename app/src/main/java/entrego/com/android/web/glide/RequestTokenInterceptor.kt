package entrego.com.android.web.glide

import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.AccessToken
import okhttp3.Interceptor
import okhttp3.Response

class RequestTokenInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()
        val newRequest = request?.newBuilder()!!
                .addHeader(EntregoApi.TOKEN, AccessToken.getToken())
                .build();
        return chain?.proceed(newRequest)!!
    }

}