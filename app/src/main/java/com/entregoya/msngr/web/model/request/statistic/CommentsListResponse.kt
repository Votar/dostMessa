package com.entregoya.msngr.web.model.request.statistic

import com.entregoya.msngr.entity.CommentEntity
import com.entregoya.msngr.web.model.response.EntregoResult

class CommentsListResponse(code: Int?,
                           message: String?,
                           val payload: Array<CommentEntity>) : EntregoResult(code, message) {
}