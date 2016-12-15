package entrego.com.android.ui.sign.model

import android.os.Handler
import android.os.Looper
import android.support.annotation.Nullable

/**
 * Created by bertalt on 13.12.16.
 */
object SendSignRequest {

    interface SendSignListener {
        fun onSuccessSendSign()
        fun onFailureSendSign(message:String)
    }

    fun executeAsync(token: String, @Nullable listener: SendSignListener?) {

        Handler(Looper.getMainLooper()).postDelayed({ listener?.onSuccessSendSign()}, 2000)
    }


}