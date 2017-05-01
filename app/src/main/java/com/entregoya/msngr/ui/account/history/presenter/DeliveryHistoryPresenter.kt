package com.entregoya.msngr.ui.account.history.presenter

import com.entregoya.msngr.dao.BaseDaoContract
import com.entregoya.msngr.dao.DaoCallback
import com.entregoya.msngr.mvp.presenter.BaseMvpPresenter
import com.entregoya.msngr.storage.model.DeliveryModel
import com.entregoya.msngr.storage.preferences.EntregoStorage
import com.entregoya.msngr.ui.account.history.DeliveryHistoryContract
import com.entregoya.msngr.ui.account.history.model.DeliveryHistoryDao
import com.entregoya.msngr.ui.account.history.model.DeliveryHistoryRequest

class DeliveryHistoryPresenter : BaseMvpPresenter<DeliveryHistoryContract.View>(),
        DeliveryHistoryContract.Presenter {

    val mToken = EntregoStorage.getToken()
    val mDaoResponse = object : DaoCallback<Array<DeliveryModel>> {
        override fun onResult(code: BaseDaoContract.DaoStates, result: Array<DeliveryModel>?) {
            when (code) {
                BaseDaoContract.DaoStates.FINAL_RESPONSE -> {
                    mView?.hideProgress()
                    result?.apply {
                        if (isNotEmpty()) {
                            mView?.showHistoryList(this)
                        } else
                            mView?.showEmptyView()
                    }
                }
                BaseDaoContract.DaoStates.PROGRESS_RESPONSE -> {
                    mView?.showProgress()
                    result?.apply {
                        if (isNotEmpty())
                            mView?.showHistoryList(this)
                        else
                            mView?.showEmptyView()
                    }
                }
                BaseDaoContract.DaoStates.ERROR -> {
                    mView?.hideProgress()
                    mView?.showError(null)
                }
            }
        }
    }

    override fun attachView(view: DeliveryHistoryContract.View) {
        super.attachView(view)
    }

    override fun requestHistoryList() {
        DeliveryHistoryDao.getAsync(mDaoResponse)
//        DeliveryHistoryRequest.requestDeliveryHistory(mToken, mGetDeliveryHistoryListener)
    }

    val mGetDeliveryHistoryListener = object : DeliveryHistoryRequest.GetDeliveryHistoryListener {
        override fun onSuccessGetDeliveryHistory(resultArray: Array<DeliveryModel>) {
            mView?.hideProgress()

            if (resultArray.count() > 0)
                mView?.showHistoryList(resultArray)
            else
                mView?.showEmptyView()
        }

        override fun onFailureGetDeliveryHistory(code: Int?, message: String?) {
            mView?.hideProgress()
            mView?.showEmptyView()
            mView?.showMessage(message)

        }

    }
}