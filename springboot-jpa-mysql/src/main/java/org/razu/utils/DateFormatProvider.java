package org.razu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateFormatProvider {

    private static final SimpleDateFormat DEFAULT_DATE_TIMEZONE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DEFAULT_TIME_FORMAT = DateTimeFormatter.ofPattern("h:mma");

    public static Date convertStringToDate(String dateStringValue) {
        Date date = null;
        try {
            date = DEFAULT_DATE_TIMEZONE_FORMAT.parse(dateStringValue);
        } catch (ParseException ex) {
            Logger.getLogger(DateFormatProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    public static String convertDateToString(Date date) {
        if (date == null) {
            return null;
        }
        return DEFAULT_DATE_FORMAT.format(date);
    }

    public static Date addMinuteInCurrentDate(long additionalMinute) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
        Calendar date = Calendar.getInstance();
        long currentTime = date.getTimeInMillis();
        Date afterAddingMins = new Date(currentTime + (additionalMinute * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    public static Date now() {
        return new Date();
    }

    public static String convertDateToOrdinalTimeDateString(Date date, ZoneId zoneId) {
        if (date == null) {
            return null;
        }
        LocalDateTime day = date.toInstant().atZone(zoneId != null ? zoneId : ZoneId.systemDefault()).toLocalDateTime();
        return day.format(DateTimeFormatter.ofPattern("h:mma d'" + getOrdinal(day.getDayOfMonth()) + "' MMM yyyy"));
    }

    public static String convertDateToOrdinalDateString(Date date, ZoneId zoneId) {
        if (date == null) {
            return null;
        }
        LocalDateTime day = date.toInstant().atZone(zoneId != null ? zoneId : ZoneId.systemDefault()).toLocalDateTime();
        return day.format(DateTimeFormatter.ofPattern("d'" + getOrdinal(day.getDayOfMonth()) + "' MMM yyyy"));
    }

    public static String converTimeRange(Date start, Date end, ZoneId zoneId) {
        if (start == null || end == null) {
            return null;
        }
        LocalDateTime startDate = start.toInstant().atZone(zoneId != null ? zoneId : ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDate = end.toInstant().atZone(zoneId != null ? zoneId : ZoneId.systemDefault()).toLocalDateTime();
        return startDate.format(DEFAULT_TIME_FORMAT) + " - " + endDate.format(DEFAULT_TIME_FORMAT);
    }

    private static String getOrdinal(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}
