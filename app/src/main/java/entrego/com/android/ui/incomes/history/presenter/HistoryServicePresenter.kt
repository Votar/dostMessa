package entrego.com.android.ui.incomes.history.presenter

import entrego.com.android.entity.HistoryServicesPreviewEntity
import entrego.com.android.storage.model.DeliveryModel
import entrego.com.android.ui.incomes.history.model.HistoryServiceDetailsModel
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
                if (now.withTimeAtStartOfDay().isEqual(next.completed.toDateTimeLong().withTimeAtStartOfDay()))
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

    val mHistoryServiceDetails = object : HistoryServiceDetailsModel.GetHistoryServicesDetailsListener {
        override fun onSuccessGetHistory(result: DeliveryModel) {
            mView?.showDetailsHistoryService(result)
            mView?.hideProgress()
        }

        override fun onFailureGetHistory(code: Int?, message: String?) {

            mView?.hideProgress()
        }

    }



    override fun requestServiceDetailsById(id: Int) {
        mView?.showProgress()
        HistoryServiceDetailsModel.request(mToken, id, mHistoryServiceDetails)
    }

    override fun onCreate(view: IHistoryServiceView, token: String, range: Pair<Long, Long>) {
        mView = view
        mToken = token
        HistoryServicesModel.refresh(mToken, range, mRefreshHistoryListener)
    }

    override fun onDestroy() {
        mView = null
    }

    override fun refreshHistory(token: String) {

    }
}