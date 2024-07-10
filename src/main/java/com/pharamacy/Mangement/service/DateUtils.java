package com.pharamacy.Mangement.service;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
@Service
public class DateUtils {
    public static boolean isWithinDays(Date date, int days) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        Calendar targetDate = Calendar.getInstance();
        targetDate.setTime(date);
        targetDate.set(Calendar.HOUR_OF_DAY, 0);
        targetDate.set(Calendar.MINUTE, 0);
        targetDate.set(Calendar.SECOND, 0);
        targetDate.set(Calendar.MILLISECOND, 0);

        long difference = (targetDate.getTimeInMillis() - currentDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
        return difference <= days;
    }
}
