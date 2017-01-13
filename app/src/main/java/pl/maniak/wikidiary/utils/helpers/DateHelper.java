package pl.maniak.wikidiary.utils.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

    private static SimpleDateFormat simpleDateWithDayNameFormat = new SimpleDateFormat("dd.MM.yyyy, EEEE");
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");


    public static String getCurrentDateString() {
        Calendar newDate = Calendar.getInstance();
        return simpleDateWithDayNameFormat.format(newDate.getTime());
    }

    public static String parseDateToStringWithDayName(Date date) {
        return simpleDateWithDayNameFormat.format(date);
    }

    public static String parseDateToString(Date date) {
        return simpleDateFormat.format(date);
    }

    public static String getOnlyDayFromDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        return simpleDateFormat.format(date);
    }

    public static Date getDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTimeZone(TimeZone.getDefault());
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return c.getTime();
    }

}