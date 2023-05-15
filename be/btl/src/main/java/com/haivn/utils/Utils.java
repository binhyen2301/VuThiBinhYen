package com.haivn.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {
    public static String getDateTimeByInstant(Instant instant) {
        Date myDate = Date.from(instant);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return formatter.format(myDate);
    }


    public static String getDateTimeCurrent() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    public static boolean checkDate(String dateString){
        LocalDateTime dateTime = null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try{
            dateTime = LocalDateTime.parse(dateString);
            if(dateTime==null){
                return false;
            }
            return true;
        }catch (Exception e){

        }
        return false;
    }

    public static String getLocalDateTime(String dateStr) {
        LocalDateTime dateTime = null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try{
            dateTime = LocalDateTime.parse(dateStr);
            return dateTimeFormatter.format(dateTime);
        }catch (Exception e){

        }
        return null;
    }

    public static String getTimeCurrent() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    public static String getDateCurrent() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }
}
