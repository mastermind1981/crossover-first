package com.aurea.deadcode.detector.infra.util;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateUtils {

    public static LocalDateTime getDateTimeFromTimestamp(final Long timestamp) {
        validateTimestamp(timestamp);
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                TimeZone.getDefault().toZoneId());
    }

    public static LocalDate getDateFromTimestamp(final Long timestamp) {
        validateTimestamp(timestamp);
        return getDateTimeFromTimestamp(timestamp).toLocalDate();
    }

    public static String getFormattedDateTime(final LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MM/dd/YYY HH:mm:ss"));
    }

    public static String getFormattedDateTime(final Long timestamp) {
        return getDateTimeFromTimestamp(timestamp).format(DateTimeFormatter.ofPattern("MM/dd/YYY HH:mm:ss"));
    }

    private static void validateTimestamp(final Long timestamp) {
        if (timestamp == null || timestamp == 0)
            throw new InvalidParameterException("Invalid given timestamp.");
    }
}