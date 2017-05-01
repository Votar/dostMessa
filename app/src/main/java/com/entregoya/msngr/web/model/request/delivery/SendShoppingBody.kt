package com.entregoya.msngr.web.model.request.delivery

class SendShoppingBody(val order: Long,
                       val amount: Float,
                       val receipt: String) {
}