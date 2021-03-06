package com.entregoya.msngr.ui.incomes.presenter

import android.content.Context
import android.content.Intent
import com.entregoya.msngr.R
import com.entregoya.msngr.binding.Delivery
import com.entregoya.msngr.entity.IncomeEntity
import com.entregoya.msngr.ui.incomes.history.HistoryServiceActivity
import com.entregoya.msngr.ui.incomes.model.IncomesModel
import com.entregoya.msngr.ui.incomes.view.IncomesView
import com.entregoya.msngr.ui.main.home.model.DeliveryRequest
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Days
import java.util.*

class IncomesPresenter : IIncomesPresenter {

    var mView: IncomesView? = null
    var token: String = ""
    override fun onStart(token: String, view: IncomesView) {
        mView = view
        this.token = token
    }

    override fun onStop() {
        mView = null
    }

    override fun requestRange(offset: Int) {
        val params = getTimesOfRange(offset)
        val timeZone = TimeZone.getDefault().id

        val mListener = object : IncomesModel.ResponseListener {
            override fun onSuccessGetIncomes(incomes: List<IncomeEntity>) {
                if (incomes.isNotEmpty())
                    mView?.onBuildCharts(incomes)
                else {
                    mView?.hideProgress()
                    mView?.onShowMessage(R.string.nothing_to_show)
                }
            }

            override fun onFailureGetIncomes(code: Int?, message: String?) {
                mView?.hideProgress()
                mView?.onShowMessage(message)
            }
        }
        IncomesModel.request(token, params, timeZone, mListener)
    }

    fun getTimesOfRange(offset: Int): Pair<Long, Long> {
        val zeroDay = DateTime.now(DateTimeZone.UTC).withMillis(0)
        val now = DateTime.now(DateTimeZone.UTC)
        val numberOfDay = now.dayOfWeek().get() - 1
        if (offset == 0) {
            val rangeOfDays = now.minusDays(numberOfDay)
            val dateTo = rangeOfDays.plusDays(6)
            val from = Days.daysBetween(zeroDay, rangeOfDays)
            val to = Days.daysBetween(zeroDay, dateTo)
            return Pair(from.days.toLong(), to.days.toLong())
        } else {
            val dateTo = now.minusDays((numberOfDay + 1) + 7 * (offset - 1))
            val dateFrom = dateTo.minusDays(6)
            val to = Days.daysBetween(zeroDay, dateTo)
            val from = Days.daysBetween(zeroDay, dateFrom)
            return Pair(from.days.toLong(), to.days.toLong())
        }
    }

    override fun startHistoryServicesActivity(context: Context, offset: Int) {
        val intent = Intent(context, HistoryServiceActivity::class.java)
        val range = getTimesOfRange(offset)
        intent.putExtra(HistoryServiceActivity.KEY_FROM, range.first)
        intent.putExtra(HistoryServiceActivity.KEY_TO, range.second)
        context.startActivity(intent)
    }
}