package entrego.com.android.ui.incomes

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import entrego.com.android.R
import entrego.com.android.entity.IncomeEntity
import entrego.com.android.storage.preferences.EntregoStorage
import entrego.com.android.ui.incomes.charts.DayAxisValueFormatter
import entrego.com.android.ui.incomes.charts.EaringAxisValueFormatter
import entrego.com.android.ui.incomes.charts.XYMarkerView
import entrego.com.android.ui.incomes.details.IncomesDetailsActivity
import entrego.com.android.ui.incomes.history.HistoryServiceActivity
import entrego.com.android.ui.incomes.presenter.IIncomesPresenter
import entrego.com.android.ui.incomes.presenter.IncomesPresenter
import entrego.com.android.ui.incomes.view.IncomesView
import entrego.com.android.util.loading
import entrego.com.android.util.snackSimple
import kotlinx.android.synthetic.main.fragment_incomes.*
import java.util.*

class IncomesFragment : Fragment(), IncomesView {


    var datesOffset = 0
    var isThisWeel = true
    var mProgress: ProgressDialog? = null
    val mPresenter: IIncomesPresenter = IncomesPresenter()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        mProgress = ProgressDialog(context)
        val view = inflater?.inflate(R.layout.fragment_incomes, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        setupCharts()
        val token = EntregoStorage(context).getToken()
        mPresenter.onStart(token, this)
        mPresenter.requestRange(datesOffset)
        incomes_history_card.setOnClickListener { mPresenter.startHistoryServicesActivity(activity, datesOffset) }
        incomes_chart_ll.setOnClickListener { startIncomesDetailsActivity() }
        incomes_bars_btn_back.setOnClickListener {
            modifyOffset(1)
        }
        incomes_bars_btn_next.setOnClickListener {
            modifyOffset(-1)
        }
    }

    fun modifyOffset(value: Int) {

        if (datesOffset == 0 && value < 0) return
        datesOffset += value
        if (datesOffset == 0) {
            isThisWeel = true
            incomes_bars_btn_next.visibility = View.GONE
        } else {
            isThisWeel = false
            incomes_bars_btn_next.visibility = View.VISIBLE
        }
        mProgress?.loading()
        mPresenter.requestRange(datesOffset)
    }

    private fun startIncomesDetailsActivity() {
        val intent = Intent(context, IncomesDetailsActivity::class.java)
        startActivity(intent)
    }

    fun setupCharts() {
        incomes_weekly_chart?.setDrawBarShadow(false)
        incomes_weekly_chart?.setDrawValueAboveBar(true)
        incomes_weekly_chart?.setMaxVisibleValueCount(7)
        incomes_weekly_chart?.axisRight?.setDrawAxisLine(false)
        incomes_weekly_chart?.setPinchZoom(false)
        incomes_weekly_chart?.setDrawGridBackground(false)


        val custom = EaringAxisValueFormatter()

        val leftAxis = incomes_weekly_chart.getAxisLeft()
        leftAxis.setLabelCount(3, false)
        leftAxis.setValueFormatter(custom)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setSpaceTop(15f)
        leftAxis.setAxisMinimum(0f) // this replaces setStartAtZero(true)

        val rightAxis = incomes_weekly_chart.getAxisRight()
        rightAxis.setDrawAxisLine(false)
        rightAxis.setDrawLabels(false)
        rightAxis.setDrawGridLines(false)

        val mv = XYMarkerView(context)
        mv.setChartView(incomes_weekly_chart) // For bounds control
        incomes_weekly_chart.setMarker(mv) // Set the marker to the chart
        incomes_weekly_chart.getLegend().setEnabled(false)
    }

    override fun onBuildCharts(incomes: List<IncomeEntity>) {
        mProgress?.dismiss()
        setData(incomes)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
    }

     private fun setData(incomes: List<IncomeEntity>) {

        var estimatedPay = 0.0
        val daysList = incomes.map {
            estimatedPay += it.revenue
            it.day
        }
        incomes_estimated_pay_value.text = "$" + String.format("%.2f", estimatedPay)
        val xAxisFormatter: IAxisValueFormatter = DayAxisValueFormatter(daysList)

        incomes_weekly_chart.getDescription().setEnabled(false)
        val xAxis = incomes_weekly_chart.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setDrawGridLines(false)
        xAxis.setGranularity(1f) // only intervals of 1 day
        xAxis.setLabelCount(daysList.count())
        xAxis.setValueFormatter(xAxisFormatter)

        val yVals = ArrayList<BarEntry>()
        var maxEaring = 0f
        var maxIndex: Int = 0
        for (i: Int in 0..incomes.lastIndex) {
            val profit = incomes[i].revenue.toFloat()
            if (maxEaring < profit) {
                maxIndex = i
                maxEaring = profit
            }
            yVals.add(BarEntry(i.toFloat(), profit))
        }
        val dataSets = ArrayList<IBarDataSet>()
        val defaultColor = resources.getColor(R.color.colorDarkGrey)
        for (i in 0..yVals.size - 2) {
            if (maxIndex == i) {
                val max = BarDataSet(listOf(yVals[i]), "")
                max.color = defaultColor
                dataSets.add(max)
            } else {
                val default = BarDataSet(listOf(yVals[i]), "")
                default.setColor(defaultColor, 120)
                dataSets.add(default)
            }
        }
        val last = BarDataSet(listOf(yVals.last()), "")
        if (isThisWeel) {
            val color = resources.getColor(R.color.colorPrimary)
            last.color = color
            incomes_this_week_tv.visibility = View.VISIBLE
            incomes_diapasone_dates.visibility = View.GONE
        } else {
            incomes_this_week_tv.visibility = View.GONE
            last.setColor(defaultColor, 120)
            incomes_diapasone_dates.visibility = View.VISIBLE
            incomes_diapasone_from.text = incomes.first().day
            incomes_diapasone_to.text = incomes.last().day
        }
        dataSets.add(last)
        val data = BarData(dataSets)
        data.setValueTextSize(10f)
        data.barWidth = 0.9f
        incomes_weekly_chart.data = data
        incomes_weekly_chart.invalidate()
    }

    override fun onShowMessage(message: String?) {
        view?.snackSimple(message)
    }

    override fun onShowMessage(idString: Int) {
        view?.snackSimple(getString(idString))
    }

    override fun hideProgress() {
        mProgress?.dismiss()
    }


}