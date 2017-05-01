package com.entregoya.msngr.ui.account.profile.account.model

data class AccountEntity(
        val swift: String,
        val account: String,
        val bank: String,
        val name: String) {
}