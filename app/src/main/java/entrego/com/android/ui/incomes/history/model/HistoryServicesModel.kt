package entrego.com.android.ui.incomes.history.model

import android.content.Context
import android.os.Handler
import com.android.annotations.Nullable
import entrego.com.android.binding.HistoryServiceBinding
import java.util.*

/**
 * Created by bertalt on 22.12.16.
 */
object HistoryServicesModel {

    interface GetHistoryServices {
        fun onSuccessGetHistory(resultList: List<HistoryServiceBinding>)
        fun onFailureGetHistory(@Nullable message: String?)
    }

    fun refresh(token: String, @Nullable listener: GetHistoryServices?) {

        Handler().postDelayed({
            listener?.onSuccessGetHistory(emptyList())
        }, 2000)
    }

    fun getServiceForToday(): List<HistoryServiceBinding> {

        val list: ArrayList<HistoryServiceBinding> = ArrayList()
        for (i in 0..8)
            list.add(HistoryServiceBinding(0, "8:30pm", "Shipments", 19.40f))
        return list
    }

    fun getServiceRecent(): List<HistoryServiceBinding> {

        val list: ArrayList<HistoryServiceBinding> = ArrayList()
        for (i in 0..8)
            list.add(HistoryServiceBinding(0, "8:30pm", "Shipments", 19.40f))
        return list
    }
}