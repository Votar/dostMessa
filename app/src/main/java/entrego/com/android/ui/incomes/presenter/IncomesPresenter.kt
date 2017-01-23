package entrego.com.android.ui.incomes.presenter

import android.content.Context
import android.content.Intent
import entrego.com.android.R
import entrego.com.android.entity.IncomeEntity
import entrego.com.android.ui.incomes.history.HistoryServiceActivity
import entrego.com.android.ui.incomes.model.IncomesModel
import entrego.com.android.ui.incomes.view.IncomesView
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

    override fun startHistoryServicesActivity(context: Context, offset: Int) {
        val intent = Intent(context, HistoryServiceActivity::class.java)
        val range = getTimesOfRange(offset)
        intent.putExtra(HistoryServiceActivity.KEY_FROM, range.first)
        intent.putExtra(HistoryServiceActivity.KEY_TO, range.second)
        context.startActivity(intent)
    }
}