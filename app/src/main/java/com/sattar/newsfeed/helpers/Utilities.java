package com.sattar.newsfeed.helpers;

/**
 * Created By Sattar 2/18/2019
 */
public class Utilities {

    public static String getDateTime(String dateStr) {
        if (dateStr != null) {
            String[] dateTime = dateStr.split("T");
            try {
                dateTime[1] = dateTime[1].substring(0, 5);
                return dateTime[0] + " " + dateTime[1];
            } catch (Exception e) {
                return dateStr;
            }
        }
        return "";
    }
}
