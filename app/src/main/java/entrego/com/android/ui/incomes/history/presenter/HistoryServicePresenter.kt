package entrego.com.android.ui.incomes.history.presenter

import android.os.Handler
import entrego.com.android.binding.HistoryServiceBinding
import entrego.com.android.ui.incomes.history.model.HistoryServicesModel
import entrego.com.android.ui.incomes.history.view.IHistoryServiceView

class HistoryServicePresenter : IHistoryServicePresenter {
    var mView: IHistoryServiceView? = null
    val mRefreshHistoryListener = object : HistoryServicesModel.GetHistoryServices {
        override fun onSuccessGetHistory(resultList: List<HistoryServiceBinding>) {

        }

        override fun onFailureGetHistory(message: String?) {

        }
    }

    override fun onCreate(view: IHistoryServiceView) {
        mView = view
        Handler().postDelayed({
            val todayList = HistoryServicesModel.getServiceForToday()
            if (todayList.isEmpty())
                mView?.showEmptyTodayServicesList()
            else
                mView?.buildTodayServices(todayList)
            val recentList = HistoryServicesModel.getServiceRecent()

            if (recentList.isEmpty())
                mView?.showEmptyRecentServicesList()
            else
                mView?.buildRecentServices(HistoryServicesModel.getServiceRecent())
        }, 2000)
    }

    override fun onDestroy() {
        mView = null
    }

    override fun refreshHistory(token: String) {
        HistoryServicesModel.refresh(token, mRefreshHistoryListener)
    }
}