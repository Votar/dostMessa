package entrego.com.android.ui.score.tips.model

import android.os.Handler

/**
 * Created by bertalt on 29.12.16.
 */
object TipsController {

    interface GetTipsListener {
        fun onSuccessGetTips(list: List<String>)
        fun onFailureGetTips(code: Int?, message: String?)
    }

    fun requestTipsAsync(listener: GetTipsListener?){
        Handler().postDelayed({
            listener?.onSuccessGetTips(listOf(
                    "Remember to keep your app up to date",
                    "Wear the uniform correctly",
                    "Keep a clean personal image",
                    "Use proper shoes",
                    "Use your raincoat if it rains to\n keep the good personal image",
                    "Move shipments with care",
                    "Kindness",
                    "Punctuality",
                    "Delivery of shipments respecting the agreed time",
                    "Proactive attitude in case of delay to comply \n the delivery schedule",
                    "Keep your vehicle clean",
                    "Drive safely"
            )
            )
        },2000)
    }

}