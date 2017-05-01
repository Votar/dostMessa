package com.entregoya.msngr.web.model.response.statistic

import com.entregoya.msngr.web.model.response.EntregoResult

class EntregoScoreResponse (code: Int?,
                            message:String?,
                            val payload : EntregoScoreEntity) : EntregoResult(code, message){
}