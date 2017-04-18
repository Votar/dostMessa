package com.entregoya.msngr.storage.model

class UserProfileModel(
        val id: Long,
        val email: String,
        val name: String,
        val phone: EntregoPhoneModel) {


    override fun toString(): String {
        return "UserProfileModel(email='$email', name='$name', phone=$phone)"
    }
}