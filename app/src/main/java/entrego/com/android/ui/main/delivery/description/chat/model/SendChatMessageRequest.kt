package entrego.com.android.ui.main.delivery.description.chat.model

import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.request.chat.ChatMessageBody
import entrego.com.android.web.model.response.EntregoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

class SendChatMessageRequest {

    companion object {
        private const val END_POINT = "common/chat/order"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body message: ChatMessageBody): Call<EntregoResult>
    }

    interface ResponseListener {
        fun onSuccessResponse()
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, message: ChatMessageBody, listener: ResponseListener?): Call<EntregoResult> {
        val request = ApiCreator.server
                .create(Request::class.java)
                .parameters(token, message)

        request.enqueue(object : Callback<EntregoResult> {
            override fun onFailure(call: Call<EntregoResult>?, t: Throwable?) {
                listener?.onFailureResponse(null, t?.message)
            }

            override fun onResponse(call: Call<EntregoResult>?, response: Response<EntregoResult>?) {
                if (response != null)
                    response.body()?.apply {
                        when (code) {
                            0 -> listener?.onSuccessResponse()
                            else -> listener?.onFailureResponse(code, this.message)
                        }
                    }
                else
                    listener?.onFailureResponse(null, null)
            }
        })

        return request
    }


}