package com.sftelehealth.doctor.domain.helper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class for parsing and formatting dates and times.
 *
 * @author Tom Dignan
 */
public final class DateTimeHelper {

    public static final SimpleDateFormat YEAR_ONLY_DATE_FORMATTER =
            new SimpleDateFormat("yyyy");

    private static final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat TIME_FORMATTER =
            new SimpleDateFormat("hh:mm a");

    private static final SimpleDateFormat VERBOSE_LOCALE_DATE_FORMATTER =
            new SimpleDateFormat("MMM dd',' yyyy");

    private static final SimpleDateFormat VERBOSE_LOCALE_TIME_FORMATTER =
            new SimpleDateFormat("hh:mm a z");

    private static final SimpleDateFormat VERBOSE_LOCALE_TIME_FORMATTER_WITHOUT_TIMEZONE =
            new SimpleDateFormat("hh:mm a");

    private static final SimpleDateFormat VERBOSE_LOCALE_DATE_TIME_FORMATTER =
            new SimpleDateFormat("MMM  dd',' yyyy hh:mm a z");

    private static final SimpleDateFormat VERBOSE_LOCALE_DAY_TIME_FORMATTER =
            new SimpleDateFormat("dd MMM', 'hh:mm a");

    private static final SimpleDateFormat VERBOSE_LOCALE_TIME_DAY_FORMATTER =
            new SimpleDateFormat("hh:mm a', 'dd MMM");

    private static final SimpleDateFormat VERBOSE_LOCALE_DAY_TIME_YEAR_FORMATTER =
            new SimpleDateFormat("dd MMM yyyy', 'hh:mm a");

    private static final SimpleDateFormat ISO_8601_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
    public static final SimpleDateFormat ALMOST_ISO_8601_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");

    public static Date parseAlmostISO8601DateTimeWithTSeparator(String datetime) {
        // Timber.d("datetime="+ datetime);
        try {
            return ALMOST_ISO_8601_FORMATTER.parse(datetime);
        } catch (ParseException e) {
            // Timber.e("caught ParseException", e);
            return null;
        }
    }

    /**
     * Parses an ISO8601 formatted datetime and returns a
     * java.util.Date object for it, or NULL if parsing
     * the date fails.
     *
     * @param datetime
     * @return Date|null
     */
    public static Date parseFromISO8601(String datetime) {
        // Timber.d("datetime="+ datetime);
        try {
            return ISO_8601_FORMATTER.parse(datetime);
        } catch (ParseException e) {
            // Timber.e("caught ParseException", e);
            return null;
        }
    }

    public static String formatToIS08601(Date date) {
        return ISO_8601_FORMATTER.format(date);
    }

    public static String formatToAlmostIS08601(Date date) {
        return ALMOST_ISO_8601_FORMATTER.format(date);
    }

    public static Date parseOnlyDate(String date) {
        // Timber.d("date="+ date);
        try {
            return DATE_FORMATTER.parse(date);
        } catch (ParseException e) {
            // Timber.e("caught ParseException", e);
            return null;
        }
    }

    public static Date parseOnlyYear(String date) {
        // Timber.d("date="+ date);
        try {
            return YEAR_ONLY_DATE_FORMATTER.parse(date);
        } catch (ParseException e) {
            // Timber.e("caught ParseException", e);
            return null;
        }
    }

    /**
     * Tries to parse most date times that it is passed, using
     * some heuristics.
     *
     * @param datetime
     * @return
     */
    public static Date parseDateTime(String datetime) {
        if (datetime.contains("T")) {
            // Timber.d("parseDateTime(): Trying ISO8601 with a T separator");
            return parseAlmostISO8601DateTimeWithTSeparator(datetime);
        } else if (datetime.length() == 10 && datetime.contains("-")) {
            // Timber.d("parseDateTime(): Trying just yyyy-MM-dd date");
            return parseOnlyDate(datetime);
        } else {
            // Timber.d("parseDateTime(): Trying regular ISO8601");
            return parseFromISO8601(datetime);
        }
    }

    public static Date parseTime(String time) {
        try {
            return TIME_FORMATTER.parse(time);
        } catch (ParseException e) {
            // Timber.d("parseTime() caught ParseException", e);
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseDate(String date) {
        try {
            return DATE_FORMATTER.parse(date);
        } catch (ParseException e) {
            // Timber.d("parseDate() caught ParseException", e);
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseDateAndTime(String strDate, String strTime) {
        Date date = parseDate(strDate);
        Date time = parseTime(strTime);
        long dateMillis = date.getTime();
        long timeMillis = time.getTime();
        return new Date(dateMillis + timeMillis);
    }

    public static String pad(int value) {
        if (value < 10) {
            return "0" + value;
        } else {
            return "" + value;
        }
    }

    public static String toLocaleDateTime(Date date) {
        return VERBOSE_LOCALE_DATE_TIME_FORMATTER.format(date);
    }

    public static String toLocaleDate(Date date) {
        return VERBOSE_LOCALE_DATE_FORMATTER.format(date);
    }

    public static String toLocaleTimeWithTimeZone(Date date) {
        return VERBOSE_LOCALE_TIME_FORMATTER.format(date);
    }

    public static String toLocaleTime(Date date) {
        return VERBOSE_LOCALE_TIME_FORMATTER_WITHOUT_TIMEZONE.format(date);
    }

    public static String toLocaleDayWithTime(Date date) {
        return VERBOSE_LOCALE_DAY_TIME_FORMATTER.format(date);
    }

    public static String toLocaleTimeWithDay(Date date) {
        return VERBOSE_LOCALE_TIME_DAY_FORMATTER.format(date);
    }

    public static String toLocaleDayTimeYear(Date date) {
        return VERBOSE_LOCALE_DAY_TIME_YEAR_FORMATTER.format(date);
    }

    public static Date toLocalTime(Date date) {
        long millisUTC = date.getTime();
        TimeZone tz = TimeZone.getDefault();
        int tzOffset = tz.getOffset(millisUTC);
        if (tz.inDaylightTime(new Date(millisUTC))) {
            millisUTC -= tz.getDSTSavings();
        }
        return new Date(millisUTC + tzOffset);
    }

    public static long toLocalTime(long millisUTC) {
        // TODO: Refactor this and save it. Perfect DST code.
        TimeZone tz = TimeZone.getDefault();
        int tzOffset = tz.getOffset(millisUTC);
        if (tz.inDaylightTime(new Date(millisUTC))) {
            millisUTC -= tz.getDSTSavings();
        }
        return millisUTC + tzOffset;
    }
}