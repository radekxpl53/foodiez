package org.foodiez.util;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static String format(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime().format(FORMATTER) : "";
    }
}