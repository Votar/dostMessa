package com.entregoya.msngr.ui.account.profile.account.model

import com.entregoya.msngr.web.api.ApiContract
import com.entregoya.msngr.web.api.ApiCreator
import com.entregoya.msngr.web.api.EntregoApi
import com.entregoya.msngr.web.model.response.EntregoResult
import com.entregoya.msngr.web.model.response.profile.EntregoBankAccountResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


class BankAccountChangeRequest {
    companion object {
        const val END_POINT = "messenger/user/change/bankDetails"
    }

    interface Request {
        @Headers(EntregoApi.CONTENT_JSON)
        @POST(END_POINT)
        fun parameters(@Header(EntregoApi.TOKEN) token: String, @Body body: AccountEntity): Call<EntregoBankAccountResponse>
    }

    interface BankAccountChangeRequestListener {
        fun onSuccessBankAccountChangeRequest(updatedAccount: AccountEntity)
        fun onFailureBankAccountChangeRequest(code: Int?, message: String?)
    }

    fun executeAsync(token: String,
                     account: AccountEntity,
                     listener: BankAccountChangeRequestListener?) {
        ApiCreator.server.create(Request::class.java)
                .parameters(token, account)
                .enqueue(object : Callback<EntregoBankAccountResponse> {
                    override fun onFailure(call: Call<EntregoBankAccountResponse>?, t: Throwable?) {
                        listener?.onFailureBankAccountChangeRequest(null, null)
                    }

                    override fun onResponse(call: Call<EntregoBankAccountResponse>?, response: Response<EntregoBankAccountResponse>?) {
                        if (response?.body() != null)
                            response.body().apply {
                                when (code) {
                                    ApiContract.RESPONSE_OK -> listener?.onSuccessBankAccountChangeRequest(account)
                                    else -> listener?.onFailureBankAccountChangeRequest(code, message)
                                }
                            }
                        else listener?.onFailureBankAccountChangeRequest(null, null)
                    }

                })


    }

    fun executeAsync(token: String,
                     swift: String,
                     account: String,
                     bank: String,
                     name: String,
                     listener: BankAccountChangeRequestListener?) {
        executeAsync(token, AccountEntity(swift, account, bank, name), listener)
    }

}