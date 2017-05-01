package com.entregoya.msngr.ui.account.profile.account.model

import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.response.profile.EntregoBankAccountResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

class GetBankAccountRequest {
    companion object {
        const val END_POINT = "messenger/user/bankDetails"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @GET(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String): Call<EntregoBankAccountResponse>
    }

    interface GetBankAccountRequestListener {
        fun onSuccessGetBankAccountRequest(account: AccountEntity)
        fun onFailureGetBankAccountRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String, listener: GetBankAccountRequestListener?) {
        ApiCreator.server.create(Request::class.java)
                .parameters(token)
                .enqueue(object : Callback<EntregoBankAccountResponse> {
                    override fun onResponse(call: Call<EntregoBankAccountResponse>?, response: Response<EntregoBankAccountResponse>?) {
                        if (response?.body() != null)
                            response.body()?.apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> {
                                        listener?.onSuccessGetBankAccountRequest(payload)
                                        //TODO: develop better decision
                                        EntregoStorage.saveBankAccount(payload)
                                    }
                                    else -> listener?.onFailureGetBankAccountRequest(code, message)
                                }
                            }
                        else listener?.onFailureGetBankAccountRequest(null, null)
                    }

                    override fun onFailure(call: Call<EntregoBankAccountResponse>?, t: Throwable?) {
                        listener?.onFailureGetBankAccountRequest(null, null)
                    }

                })
    }

}