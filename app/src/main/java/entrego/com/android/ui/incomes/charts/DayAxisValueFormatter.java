package entrego.com.android.ui.incomes.charts;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import org.joda.time.format.DateTimeFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayAxisValueFormatter implements IAxisValueFormatter {

    protected List<String> mDaysOfWeek;
    public DayAxisValueFormatter(List<String> values) {
        mDaysOfWeek  = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        try {
            String dateInString = mDaysOfWeek.get((int) value);
        String template = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(template, Locale.getDefault());
        Date dateInDate = null;
            dateInDate = formatter.parse(dateInString);
            String result = DateTimeFormat.forPattern("EEE").print(dateInDate.getTime());
            return  result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        catch (IndexOutOfBoundsException ex){
            return "";
        }
        return "";
    }
}
