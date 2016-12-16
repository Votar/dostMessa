package entrego.com.android.ui.account.history.model

import android.content.Context
import android.os.Handler

/**
 * Created by bertalt on 16.12.16.
 */
object DeliveryHistoryModel {

    interface GetDeliveryHistory {
        fun onSuccessGetDeliveryHistory()
        fun onFailureGetDeliveryHistory(message: String?)
    }

    fun requestDeliveryHistory(token: String, listener: GetDeliveryHistory?) {

        Handler().postDelayed({ listener?.onSuccessGetDeliveryHistory() }, 1500)

    }
}