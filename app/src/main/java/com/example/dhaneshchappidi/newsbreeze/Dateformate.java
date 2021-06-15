package com.example.dhaneshchappidi.newsbreeze;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dateformate {
    public static String Dateformate(String oldstringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat(" dd-MM-yyyy");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }
}
