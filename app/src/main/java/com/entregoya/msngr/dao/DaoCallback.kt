package com.entregoya.msngr.dao


interface DaoCallback<in V> {
    fun onResult(code: BaseDaoContract.DaoStates, result: V?)
}