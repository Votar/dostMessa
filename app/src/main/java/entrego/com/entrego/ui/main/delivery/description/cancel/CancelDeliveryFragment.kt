package entrego.com.entrego.ui.main.delivery.description.cancel

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import entrego.com.entrego.R
import entrego.com.entrego.storage.preferences.EntregoStorage
import entrego.com.entrego.util.UserMessageUtil
import kotlinx.android.synthetic.main.cancel_delivery_fragment.*

/**
 * Created by bertalt on 13.12.16.
 */
class CancelDeliveryFragment : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        isCancelable = false
        return inflater?.inflate(R.layout.cancel_delivery_fragment, container, false)

    }


    override fun onStart() {
        super.onStart()

        val reasons = this.resources.getStringArray(R.array.cancel_reasons)
        cancel_delivery_recycler_reasons.adapter = ReasonsAdapter(reasons.toList(), mReasonClickListener)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        cancel_delivery_recycler_reasons.layoutManager = layoutManager
    }

    val mReasonClickListener = object : ReasonsAdapter.OnReasonClicked {
        override fun onClicked(reason: String) {
            val token = EntregoStorage(context).getToken()
            CancelDelivery.executeAsync(token, reason, mResultCancelListener)


        }

    }


    val mResultCancelListener = object : CancelDelivery.CancelDeliveryListener {
        override fun onSuccessCancel() {

            UserMessageUtil.show(context, "GC")
            dismiss()
        }

        override fun onFailureCancel(message: String) {
            UserMessageUtil.show(context, message)
        }


    }
}