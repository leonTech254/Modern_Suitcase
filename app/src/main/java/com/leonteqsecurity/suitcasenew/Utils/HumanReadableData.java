package com.leonteqsecurity.suitcasenew.Utils;

import android.os.Build;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HumanReadableData {

    public static String formatDateTime(String date) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Define a custom date and time format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            try {
                // Parse the input date string
                LocalDateTime dateTime = LocalDateTime.parse(date);

                // Format the parsed date and time using the defined formatter
                return dateTime.format(formatter);
            } catch (Exception e) {
                // Handle parsing errors, e.g., invalid date format
                e.printStackTrace();
            }
        }

        // Return null if parsing and formatting are not possible
        return null;
    }
}
