package com.entregoya.msngr.web.model.response.statistic

import com.entregoya.msngr.entity.ScoreEntity

class EntregoScoreEntity(val best: ScoreEntity,
                         val canceled: Int,
                         val declined: Int,
                         val average: Float,
                         val completed: Int) {
}