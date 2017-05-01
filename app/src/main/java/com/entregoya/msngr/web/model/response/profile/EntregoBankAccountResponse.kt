package com.entregoya.msngr.web.model.response.profile

import com.entregoya.msngr.ui.account.profile.account.model.AccountEntity
import com.entregoya.msngr.web.model.response.EntregoResult

class EntregoBankAccountResponse(code: Int?,
                                 message: String?,
                                 val payload: AccountEntity) : EntregoResult(code, message) {

}