package entrego.com.android.ui.incomes.history.presenter

import entrego.com.android.entity.HistoryServicesPreviewEntity
import entrego.com.android.ui.incomes.history.model.HistoryServicesModel
import entrego.com.android.ui.incomes.history.view.IHistoryServiceView
import entrego.com.android.util.Logger
import entrego.com.android.util.toDateTimeLong
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Days
import java.util.*

class HistoryServicePresenter : IHistoryServicePresenter {
    var mView: IHistoryServiceView? = null
    var mToken: String = ""
    val mRefreshHistoryListener = object : HistoryServicesModel.GetHistoryServices {
        override fun onSuccessGetHistory(resultList: List<HistoryServicesPreviewEntity>) {
            Logger.logd(resultList.toString())
            val now = DateTime.now()
            val todaysService = ArrayList<HistoryServicesPreviewEntity>()
            val recentServices = ArrayList<HistoryServicesPreviewEntity>()
            for (next in resultList) {
                if ((Days.daysBetween(now, next.completed.toDateTimeLong()).days == 0))
                    todaysService.add(next)
                else
                    recentServices.add(next)
            }

            if (todaysService.count() > 0)
                mView?.buildTodayServices(todaysService)
            else
                mView?.showEmptyTodayServicesList()
            if (recentServices.count() > 0)
                mView?.buildRecentServices(recentServices)
            else
                mView?.showEmptyRecentServicesList()
        }

        override fun onFailureGetHistory(message: String?) {
            mView?.showEmptyRecentServicesList()
            mView?.showEmptyTodayServicesList()
        }
    }

    fun getTimesOfRange(offset: Int): Pair<Long, Long> {
        val zeroDay = DateTime.now(DateTimeZone.UTC).withMillis(0)
        val now = DateTime.now()
        val numberOfDay = now.dayOfWeek().get()
        if (offset == 0) {
            val rangeOfDays = now.minusDays(numberOfDay)
            val to = Days.daysBetween(zeroDay, now)
            val from = Days.daysBetween(zeroDay, rangeOfDays)
            return Pair(from.days.toLong(), to.days.toLong())
        } else {
            val dateTo = now.minusDays((numberOfDay + 1) + 7 * (offset - 1))
            val dateFrom = dateTo.minusDays(6)
            val to = Days.daysBetween(zeroDay, dateTo)
            val from = Days.daysBetween(zeroDay, dateFrom)
            return Pair(from.days.toLong(), to.days.toLong())
        }
    }

    override fun onCreate(view: IHistoryServiceView, token: String) {
        mView = view
        mToken = token

        val range = getTimesOfRange(0)

        HistoryServicesModel.refresh(mToken, range, mRefreshHistoryListener)
//        HistoryServicesModel.getServiceForToday(mToken, null)

//        Handler().postDelayed({
//            val todayList = HistoryServicesModel.getServiceForToday()
//            if (todayList.isEmpty())
//                mView?.showEmptyTodayServicesList()
//            else
//                mView?.buildTodayServices(todayList)
//            val recentList = HistoryServicesModel.getServiceRecent()
//
//            if (recentList.isEmpty())
//                mView?.showEmptyRecentServicesList()
//            else
//                mView?.buildRecentServices(HistoryServicesModel.getServiceRecent())
//        }, 2000)
    }

    override fun onDestroy() {
        mView = null
    }

    override fun refreshHistory(token: String) {
//        HistoryServicesModel.refresh(token, mRefreshHistoryListener)
    }
}