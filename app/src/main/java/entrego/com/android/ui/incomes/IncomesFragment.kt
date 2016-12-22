package entrego.com.android.ui.incomes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import entrego.com.android.R
import entrego.com.android.ui.incomes.charts.DayAxisValueFormatter
import entrego.com.android.ui.incomes.charts.EaringAxisValueFormatter
import entrego.com.android.ui.incomes.charts.XYMarkerView
import kotlinx.android.synthetic.main.fragment_incomes.*
import java.util.*

/**
 * Created by bertalt on 21.12.16.
 */
class IncomesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        val view = inflater?.inflate(R.layout.fragment_incomes, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        setupCharts()
    }

    fun setupCharts() {
        incomes_weekly_chart?.setDrawBarShadow(false)
        incomes_weekly_chart?.setDrawValueAboveBar(true)

        incomes_weekly_chart?.setMaxVisibleValueCount(7)

        incomes_weekly_chart.axisRight.setDrawAxisLine(false)
        incomes_weekly_chart.setPinchZoom(false)

        incomes_weekly_chart.setDrawGridBackground(false)

        val xAxisFormatter: IAxisValueFormatter = DayAxisValueFormatter()
        incomes_weekly_chart.getDescription().setEnabled(false)
        val xAxis = incomes_weekly_chart.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setDrawGridLines(false)
        xAxis.setGranularity(1f) // only intervals of 1 day
        xAxis.setLabelCount(7)
        xAxis.setValueFormatter(xAxisFormatter)

        val custom = EaringAxisValueFormatter()

        val leftAxis = incomes_weekly_chart.getAxisLeft()
        leftAxis.setLabelCount(3, false)
        leftAxis.setValueFormatter(custom)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setSpaceTop(15f)
        leftAxis.setAxisMinimum(0f) // this replaces setStartAtZero(true)
//
        val rightAxis = incomes_weekly_chart.getAxisRight()
        rightAxis.setDrawAxisLine(false)
        rightAxis.setDrawLabels(false)
        rightAxis.setDrawGridLines(false)

        val mv = XYMarkerView(context)
        mv.setChartView(incomes_weekly_chart) // For bounds control
        incomes_weekly_chart.setMarker(mv) // Set the marker to the chart
        incomes_weekly_chart.getLegend().setEnabled(false)
//        incomes_weekly_chart.getAxisRight()
        setData(3, 50f)

    }

    private fun setData(count: Int, range: Float) {

        val yVals = ArrayList<BarEntry>()

        var maxEaring = 0f
        var maxIndex: Int = 0
        for (i: Int in 0..count) {
            val mult = range + 1
            val earing = (Math.random() * mult).toFloat()
            if (maxEaring < earing) {
                maxIndex = i
                maxEaring = earing
            }
            yVals.add(BarEntry(i.toFloat(), earing))
        }

        val dataSets = ArrayList<IBarDataSet>()

        for (i in 0..yVals.size - 2) {
            if (maxIndex.equals(i)) {
                val max = BarDataSet(listOf(yVals[i]), "")
                val color = resources.getColor(R.color.colorDarkGrey)
                max.setColor(color)
                dataSets.add(max)
            } else {
                val default = BarDataSet(listOf(yVals[i]), "")
                val color = resources.getColor(R.color.colorDarkGrey)
                default.setColor(color, 120)
                dataSets.add(default)
            }
        }

        val last = BarDataSet(listOf(yVals.last()), "")
        val color = resources.getColor(R.color.colorPrimary)
        last.setColor(color)
        dataSets.add(last)

        val data = BarData(dataSets)

        data.setValueTextSize(10f)
        data.barWidth = 0.9f

        incomes_weekly_chart.setData(data)

    }
}