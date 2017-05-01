package com.entregoya.msngr.ui.score.comments.model

import com.entregoya.msngr.entity.CommentEntity
import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.request.statistic.CommentsListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers


class CommentsListRequest {
    companion object {
        const val END_POINT = "messenger/statistics/comments"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String): Call<CommentsListResponse>
    }

    interface CommentsListRequestListener {
        fun onSuccessCommentsListRequest(resultList: Array<CommentEntity>)
        fun onFailureCommentsListRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String, listener: CommentsListRequestListener?) {
        ApiCreator.server.create(Request::class.java)
                .parameters(token)
                .enqueue(object : Callback<CommentsListResponse> {
                    override fun onFailure(call: Call<CommentsListResponse>?, t: Throwable?) {
                        listener?.onFailureCommentsListRequest(null, null)
                    }

                    override fun onResponse(call: Call<CommentsListResponse>?, response: Response<CommentsListResponse>?) {
                        if (response?.body() != null)
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> listener?.onSuccessCommentsListRequest(payload)
                                    else->listener?.onFailureCommentsListRequest(code,message)
                                }
                            }
                        else listener?.onFailureCommentsListRequest(null, null)
                    }
                })
    }

}