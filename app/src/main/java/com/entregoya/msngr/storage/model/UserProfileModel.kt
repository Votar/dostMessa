package com.entregoya.msngr.storage.model

class UserProfileModel(
        val id: Long,
        val email: String,
        val name: String,
        val phone: EntregoPhoneModel) {
    val userPicUrl: String
        get() = "http://1.media.collegehumor.cvcdn.com/14/45/7d51a082762115b56229fc6b741c1438.jpg"


    override fun toString(): String {
        return "UserProfileModel(email='$email', name='$name', phone=$phone)"
    }
}