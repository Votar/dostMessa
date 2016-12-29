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
                    "Keep calm",
                    "Kindness",
                    "Punctuality"
            )
            )
        },2000)
    }

}