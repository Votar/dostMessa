package com.entregoya.msngr.ui.account.history.model

import com.entregoya.msngr.dao.BaseDaoContract
import com.entregoya.msngr.dao.DaoCallback
import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.storage.preferences.EntregoStorage

object DeliveryHistoryDao : BaseDaoContract<Array<DeliveryModel>> {

    override var value: Array<DeliveryModel>? = null

    override fun getAsync(listener: DaoCallback<Array<DeliveryModel>>?) {
        listener?.onResult(BaseDaoContract.DaoStates.PROGRESS_RESPONSE, value)

        val token = EntregoStorage.getToken()
        DeliveryHistoryRequest.requestDeliveryHistory(
                token,
                object : DeliveryHistoryRequest.GetDeliveryHistoryListener {
                    override fun onFailureGetDeliveryHistory(code: Int?, message: String?) {
                        listener?.onResult(BaseDaoContract.DaoStates.FINAL_RESPONSE, value)
                    }

                    override fun onSuccessGetDeliveryHistory(resultArray: Array<DeliveryModel>) {
                        value = resultArray
                        listener?.onResult(BaseDaoContract.DaoStates.FINAL_RESPONSE, value)
                    }
                })
    }
}