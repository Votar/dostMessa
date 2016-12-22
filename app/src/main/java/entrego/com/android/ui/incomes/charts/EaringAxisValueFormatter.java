package entrego.com.android.ui.incomes.charts;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class EaringAxisValueFormatter implements IAxisValueFormatter
{

    private DecimalFormat mFormat;

    public EaringAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.00");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " $";
    }
}
