package entrego.com.android.ui.account.help.reports.model

import android.os.Handler
import java.util.*

/**
 * Created by bertalt on 26.12.16.
 */
object ReportsController {

    interface GetReportsListener {
        fun onSuccessGet(reports: List<ReportModel>)
        fun onFailureGet(code: Int?, message: String?)
    }

    val reportsList: ArrayList<ReportModel>

    init {
        reportsList = ArrayList()
        reportsList.add(ReportModel(1, "Service", "Pending", "Service", "Алё, пошли кофе пить!", null, "31/12/2016"))
        reportsList.add(ReportModel(2, "Service", "Resolved", "Service", "Короч, электричество кончилось!", "Перезагрузите котельню", "31/12/2016"))
        reportsList.add(ReportModel(3, "Service", "Resolved", "Service", "Короч, электричество кончилось!", "Перезагрузите котельню", "31/12/2016"))
    }

    fun getReportsAsync(listener: GetReportsListener) {
        Handler().postDelayed({ listener.onSuccessGet(reportsList) }, 2000)
    }
}