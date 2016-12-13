package entrego.com.entrego.ui.main.delivery.description.cancel

import android.os.Handler
import android.os.Looper
import android.support.annotation.Nullable
import entrego.com.entrego.storage.model.binding.DeliveryInstance

/**
 * Created by bertalt on 13.12.16.
 */
object CancelDelivery {

    interface CancelDeliveryListener {
        fun onSuccessCancel()
        fun onFailureCancel(message: String)
    }

    var isRequested: Boolean = false

    fun executeAsync(token: String, reason: String, @Nullable listener: CancelDeliveryListener?) {

        if (isRequested)
            return

        isRequested = true
        Handler(Looper.getMainLooper()).postDelayed({
            DeliveryInstance.getInstance().update(null)
            listener?.onSuccessCancel()
            isRequested = false
        }, 2000)


    }

}