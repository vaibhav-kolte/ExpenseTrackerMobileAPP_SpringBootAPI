package com.myproject.expensetacker.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static String formatDate(String date) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return offsetDateTime.format(formatter);
    }
}
