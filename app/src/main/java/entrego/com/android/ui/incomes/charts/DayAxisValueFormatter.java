package entrego.com.android.ui.incomes.charts;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Calendar;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter implements IAxisValueFormatter {

    protected String[] mDaysOfWeek = new String[]{
            "sun", "mon", "tue", "wed", "thu", "fri", "sat"
    };

    public DayAxisValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        int index = (int)value%7;

        return mDaysOfWeek[index];
    }

}
