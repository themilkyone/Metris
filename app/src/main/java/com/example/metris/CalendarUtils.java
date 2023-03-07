package com.example.metris;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class CalendarUtils
{
    public static LocalDate selectedDate;

    public static String weekDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("W").withLocale(Locale.ENGLISH);
        return date.format(formatter);
    }

    public static String monthDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH);
        return date.format(formatter);
    }

    public static String monthDateInt(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM").withLocale(Locale.ENGLISH);
        return date.format(formatter);
    }

    public static String dayName(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE").withLocale(Locale.ENGLISH);
        return date.format(formatter);
    }

    public static int dayInt(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");
        return Integer.parseInt(date.format(formatter));
    }

    }