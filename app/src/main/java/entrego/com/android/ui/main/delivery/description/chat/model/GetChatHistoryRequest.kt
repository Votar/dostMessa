package entrego.com.android.ui.main.delivery.description.chat.model

import entrego.com.android.web.api.ApiCreator
import entrego.com.android.web.api.EntregoApi
import entrego.com.android.web.model.response.chat.EntregoGetChatHistoryResponse
import entrego.com.android.web.socket.model.ChatSocketMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

class GetChatHistoryRequest {

    companion object {
        private const val END_POINT = "common/chat/order/{id}"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Path("id") orderId: Long): Call<EntregoGetChatHistoryResponse>
    }

    interface ResponseListener {
        fun onSuccessResponse(reslutList: List<ChatSocketMessage>)
        fun onFailureResponse(code: Int?, message: String?)
    }

    fun requestAsync(token: String, orderId: Long, listener: ResponseListener?): Call<EntregoGetChatHistoryResponse> {
        val request = ApiCreator.server
                .create(Request::class.java)
                .parameters(token, orderId)

        request.enqueue(object : Callback<EntregoGetChatHistoryResponse> {
            override fun onFailure(call: Call<EntregoGetChatHistoryResponse>?, t: Throwable?) {
                listener?.onFailureResponse(null, t?.message)
            }

            override fun onResponse(call: Call<EntregoGetChatHistoryResponse>?, response: Response<EntregoGetChatHistoryResponse>?) {
                if (response != null)
                    response.body().apply {
                        when (code) {
                            0 -> listener?.onSuccessResponse(payload.toList())
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