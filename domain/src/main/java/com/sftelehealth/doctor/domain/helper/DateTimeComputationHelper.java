package com.sftelehealth.doctor.domain.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by rahul on 15/11/16.
 */

public class DateTimeComputationHelper {

    private static String YEAR_TEXT = "year";
    private static String MONTH_TEXT = "month";
    private static String DAY_TEXT = "day";

    //private static final SimpleDateFormat sdf = DateTimeHelper.ALMOST_ISO_8601_FORMATTER;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
    private static final TimeZone timeZone = TimeZone.getDefault(); //TimeZone.getTimeZone("GMT");

    Calendar cal;

    public DateTimeComputationHelper() {
        cal = Calendar.getInstance();
    }

    public String getElapsedTimeString(Date elapsedDate) {

        Calendar cal = Calendar.getInstance(timeZone);
        SimpleDateFormat sdfLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        sdfLocal.setTimeZone(cal.getTimeZone());

        /*try {
            elapsedDate = getTimeZoneAppliedDate(sdf.format(elapsedDate), cal.getTimeZone());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        elapsedDate = convertGMTToIST(elapsedDate);

        Date currentDate = new Date();

        long timeBetweenInMillis = currentDate.getTime() - elapsedDate.getTime();

        // Timber.d("Current time: " + currentDate + ", time from server: " + elapsedDate);


        /*if(years != 0) {
            //return years + "Y " + months % 12 + "M";
            dateText = years + ((years==1)? " yr" : " yrs");
        } else if(months != 0) {
            //return months + "M " + days % 30 + "d";
            dateText = months + ((months==1)? " mth" : " mths");
        } else if(days != 0) {
            //return days + "M " + hours % 24 + "h";
            dateText = days + ((days==1)? " day" : " days");
        } else if(hours != 0) {
            //return hours + "h " + minutes % 60 + "m";
            dateText = hours + ((hours==1)? " hr" : " hrs");
        } else if(minutes != 0) {
            //return minutes + "m " + seconds % 60 + "s";
            dateText = minutes + ((minutes==1)? " min" : " mins");
        } else if(seconds != 0) {
            //return seconds + "s";
            dateText = seconds + ((seconds==1)? " sec" : " secs");
        } else {
            return "unknown";
        }

        return dateText;*/


        return getTimeFormatted(timeBetweenInMillis);
    }


    public String getTimeFormatted(long timeBetweenInMillis) {

        long seconds = timeBetweenInMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = months / 12;
        
        // String time = years + ":" + days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
        // String dateText = "unknown";

        String timeText = "";
        if(hours != 0) {
            //return hours + "h " + minutes % 60 + "m";
            timeText = hours + ((hours==1)? " hr" : " hrs");
        }
        if(minutes != 0) {
            //return minutes + "m " + seconds % 60 + "s";
            timeText += minutes + ((minutes==1)? " min" : " mins");
        }

        return timeText;
    }

    public String getRemainingTimeString(Date remainingDate) {

        Calendar cal = Calendar.getInstance(timeZone);
        SimpleDateFormat sdfLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        sdfLocal.setTimeZone(cal.getTimeZone());

        Date currentDate = new Date();

        /*try {
            remainingDate = getTimeZoneAppliedDate(remainingDate.toString(), cal.getTimeZone());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        remainingDate = convertGMTToIST(remainingDate);

        long timeBetweenInMillis = remainingDate.getTime() - currentDate.getTime();

        // Timber.d("Current time: " + currentDate + ", time from server: " + remainingDate);

        long seconds = timeBetweenInMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = months / 12;
        String time = years + ":" + days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
        String dateText = "unknown";
        if(years != 0) {
            return years + "Y " + months % 12 + "M";
            //dateText = years + ((years==1)? " yr" : " yrs");
        } else if(months != 0) {
            return months + "M " + days % 30 + "d";
            //dateText = months + ((months==1)? " mth" : " mths");
        } else if(days != 0) {
            return days + "M " + hours % 24 + "h";
            //dateText = days + ((days==1)? " day" : " days");
        } else if(hours != 0) {
            return hours + "h " + minutes % 60 + "m";
            //dateText = hours + ((hours==1)? " hr" : " hrs");
        } else if(minutes != 0) {
            return minutes + "m " + seconds % 60 + "s";
            //dateText = minutes + ((minutes==1)? " min" : " mins");
        } else if(seconds != 0) {
            return seconds + "s";
            //dateText = seconds + ((seconds==1)? " sec" : " secs");
        } else {
            return "unknown";
        }

    }

    public String getRemainingTimeFormattedString(Date remainingDate) {

        Calendar cal = Calendar.getInstance(timeZone);
        SimpleDateFormat sdfLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        sdfLocal.setTimeZone(cal.getTimeZone());

        Date currentDate = new Date();

        // String trailingText = " left ";

        remainingDate = convertGMTToIST(remainingDate);

        long timeBetweenInMillis = remainingDate.getTime() - currentDate.getTime();

        // Timber.d("Current time: " + currentDate + ", time from server: " + remainingDate);

        long seconds = timeBetweenInMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = months / 12;
        String time = years + ":" + days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
        String dateText = "unknown";
        if(years != 0) {
            return years + "Y " + months % 12 + "M";
            //dateText = years + ((years==1)? " yr" : " yrs");
        } else if(months != 0) {
            return months + "M " + days % 30 + "d";
            //dateText = months + ((months==1)? " mth" : " mths");
        } else if(days != 0) {
            return days + "M " + hours % 24 + "h";
            //dateText = days + ((days==1)? " day" : " days");
        } else if(hours != 0) {
            return hours + " hour " + minutes % 60 + " min";
            //dateText = hours + ((hours==1)? " hr" : " hrs");
        } else if(minutes != 0) {
            return minutes + " min " + seconds % 60 + " sec";
            //dateText = minutes + ((minutes==1)? " min" : " mins");
        } else if(seconds != 0) {
            return seconds + " sec";
            //dateText = seconds + ((seconds==1)? " sec" : " secs");
        } else {
            return "unknown";
        }
    }

    public int getElapsedTimeInHours(Date elapsedDate) {

        Calendar cal = Calendar.getInstance(timeZone);
        SimpleDateFormat sdfLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        sdfLocal.setTimeZone(cal.getTimeZone());

        /*try {
            elapsedDate = getTimeZoneAppliedDate(sdf.format(elapsedDate), cal.getTimeZone());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        elapsedDate = convertGMTToIST(elapsedDate);

        Date currentDate = new Date();

        long timeBetweenInMillis = currentDate.getTime() - elapsedDate.getTime();

        // Timber.d("Current time: " + currentDate + ", time from server: " + elapsedDate);

        long seconds = timeBetweenInMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = months / 12;
        String time = years + ":" + days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
        String dateText = "unknown";

        return (int) hours;
    }

    public static Date getTimeZoneAppliedDate(final String str, final TimeZone tz)
            throws ParseException {

        sdf.setTimeZone(tz);
        return sdf.parse(str);
    }

    public Date convertGMTToIST(Date date) {

        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(date); // sets calendar time/date

        // cal.add(Calendar.HOUR, 5); // adds given hour
        // cal.add(Calendar.MINUTE, 30);
        cal.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(System.currentTimeMillis()));
        return cal.getTime();
        /*try {
            //Date tempDate = sdf.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }*/
    }

    /*public static Date getISTFormattedDateTime(Date date){

        return date;
    }*/

    public String getYearsPassedFromDate(Date date) {

        // passed date
        Calendar a = Calendar.getInstance();
        a.setTime(date);

        // current date
        Calendar b = Calendar.getInstance();
        b.setTime(new Date());

        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return String.valueOf(diff);
    }

    public String getAge(Date date) {

        String ageMetric = YEAR_TEXT;

        // passed date
        Calendar a = Calendar.getInstance();
        a.setTime(date);

        // current date
        Calendar b = Calendar.getInstance();
        b.setTime(new Date());

        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }

        // check if age is less than a year then calculate difference in months
        if (diff == 0) {
            if (b.get(YEAR) > a.get(YEAR))
                diff = (12 - a.get(MONTH)) + b.get(MONTH);
            else
                diff = b.get(MONTH) - a.get(MONTH);
            ageMetric = MONTH_TEXT;
        }

        if(diff == 0) {
            diff = b.get(Calendar.DAY_OF_MONTH) - a.get(Calendar.DAY_OF_MONTH);
            ageMetric = DAY_TEXT;
        }

        // calculate if year/month/day is more than one then change to plural form
        /*if(diff > 1)
            ageMetric += "s";*/

        return diff + " " + ageMetric;
    }

    public Date addHours(Date date) {

        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(date); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
        return cal.getTime();
    }


    public Date addTime(Date date, int timeInSecs) {

        float hours = 0;
        hours = timeInSecs / (60 * 60);

        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(date); // sets calendar time/date
        cal.add(Calendar.SECOND, timeInSecs); // adds one hour
        return cal.getTime();
    }

    public String getTimeDifferenceInHHmmssFormat(Date startTime, Date endTime) {

        Calendar cal = Calendar.getInstance(timeZone);
        SimpleDateFormat sdfLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        sdfLocal.setTimeZone(cal.getTimeZone());

        startTime = convertGMTToIST(startTime);
        endTime = convertGMTToIST(endTime);

        long timeBetweenInMillis = endTime.getTime() - startTime.getTime();

        // Timber.d("call start time: " + startTime + ", call end time: " + endTime);

        long seconds = timeBetweenInMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        //long days = hours / 24;

        String time = "";
        if(((int)hours) < 10)
            time = time + "0" + (int)hours;
        else
            time = time + (int)hours;

        if(((int)minutes) < 10)
            time = ":" +time + "0" + (int)minutes;
        else
            time = time + (int)minutes;

        if(((int)seconds) < 10)
            time = ":" + time + "0" + (int)minutes;
        else
            time = time + minutes;

        return time;
    }
}