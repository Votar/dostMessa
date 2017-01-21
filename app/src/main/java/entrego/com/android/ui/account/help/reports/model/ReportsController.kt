package entrego.com.android.ui.account.help.reports.model

import android.os.Handler
import android.text.method.DateTimeKeyListener
import org.joda.time.DateTime
import java.util.*


object ReportsController {

    interface GetReportsListener {
        fun onSuccessGet(reports: List<ReportEntity>)
        fun onFailureGet(code: Int?, message: String?)
    }

    val reportsList: ArrayList<ReportEntity>

    init {
        reportsList = ArrayList()
        reportsList.add(ReportEntity(1, "Service", "Pending", "Service", "The user asked me to take some boxes to his address when he got to the point help them down to the door of the building and the client asked me to pass them to the building, I told him that we had not allowed it, but he was annoyed and He began to claim that it was my job. Without further closing the door and did not mention anything!", null, "31/12/2016"))
        reportsList.add(ReportEntity(3, "Service", "Resolved", "Service", "The user asked me to take some boxes to his address when he got to the point help them down to the door of the building and the client asked me to pass them to the building, I told him that we had not allowed it, but he was annoyed and He began to claim that it was my job. Without further closing the door and did not mention anything", "Hi Heather, we regret that your trip has had this situation, lamentable- not all our customers are 5-star users.\n\n" +
                "We appreciate your professionalism and hope that these incidents do not happen again, you responded well and took the appropriate measures.", DateTime.now().toLocalDate().toString()))
    }

    fun getReportsAsync(listener: GetReportsListener) {
        Handler().postDelayed({ listener.onSuccessGet(reportsList) }, 1500)
    }
}