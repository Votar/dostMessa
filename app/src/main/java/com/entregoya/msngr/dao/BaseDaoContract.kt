package com.entregoya.msngr.dao

interface BaseDaoContract<V : Any> {
    enum class DaoStates {
        FINAL_RESPONSE,
        PROGRESS_RESPONSE,
        ERROR
    }

    var value: V?
    fun getAsync(listener: DaoCallback<V>?) {

    }
}