package com.entregoya.msngr.web.fcm.models


class SendFcmBody(val push: String, val platform: String = "ANDROID") {
}