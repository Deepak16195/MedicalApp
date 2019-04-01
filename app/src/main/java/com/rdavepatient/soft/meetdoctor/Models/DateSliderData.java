package com.rdavepatient.soft.meetdoctor.Models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by user on 18-08-2017.
 */

public class DateSliderData {

    public  static List<Date> DatetoSend = new ArrayList<>();

    public static ArrayList<DateModel> getData() {
        ArrayList<DateModel> data = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 10);
        dt = c.getTime();
        DatetoSend =  getDatesBetweenUsingJava7(date,dt);



        String[] Date = {
                "07 MAR",
                "08 MAR",
                "09 MAR",
                "10 MAR",
                "11 MAR",
                "12 MAR",
                "13 MAR",
                "14 MAR",
                "15 MAR",
        };

        String[] Price = {
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Subday",
                "Monday",
                "Tuesday",
        };


        for (int i = 0; i < Date.length; i++) {
            DateModel current = new DateModel();
            current.setDate(Date[i]);
            current.setPrice(Price[i]);
            data.add(current);
        }

        return data;
    }





    public static List<Date> getDatesBetweenUsingJava7(
            Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }





}