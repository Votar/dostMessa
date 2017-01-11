package entrego.com.android.ui.main.accept.presenter

import android.view.View
import entrego.com.android.ui.main.accept.view.IAcceptDeliveryView

/**
 * Created by bertalt on 10.01.17.
 */
interface IAcceptDeliveryPresenter {
    fun onStart(view: IAcceptDeliveryView)
    fun onStop()
    fun acceptDelivery(id:Int)
    fun declineDelivery(id:Int)
}

