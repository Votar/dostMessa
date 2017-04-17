package com.entregoya.msngr.web.model.response

/**
 * Created by Admin on 16.01.2017.
 */
interface CommonResponseListener {
    fun onSuccessResponse()
    fun onFailureResponse(code:Int?, message:String?)
}