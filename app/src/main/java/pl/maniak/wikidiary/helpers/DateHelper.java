package pl.maniak.wikidiary.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy, EEEE");


    public static String getCurrentDateString() {
        Calendar newDate = Calendar.getInstance();
        return simpleDateFormat.format(newDate.getTime());
    }

    public static String parseDateToString(Date date) {
        return simpleDateFormat.format(date);
    }
}