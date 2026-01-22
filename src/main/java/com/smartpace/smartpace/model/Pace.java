package com.smartpace.smartpace.model;

import java.util.regex.Pattern;
import lombok.Value;

@Value
public class Pace {

    private static final Pattern FORMAT_PATTERN = Pattern.compile("^(\\d+):(\\d{2})\\.(\\d)$");
    int deciseconds;
    String formattedString;

    public static Pace ofFormattedString(String formatted) {
        if (formatted == null || !FORMAT_PATTERN.matcher(formatted).matches()) {
            throw new IllegalArgumentException("Invalid pace format: " + formatted);
        }

        int deciseconds = parseFormattedString(formatted);
        return new Pace(deciseconds, formatted);
    }

    public static Pace ofDeciseconds(int deciseconds) {
        if (deciseconds < 0) {
            throw new IllegalArgumentException("Deciseconds cannot be negative: " + deciseconds);
        }
        String formatted = formatDeciseconds(deciseconds);
        return new Pace(deciseconds, formatted);
    }

    private static String formatDeciseconds(int deciseconds) {
        int totalseconds = deciseconds / 10;
        int minutes = totalseconds / 60;
        int seconds = totalseconds % 60;
        int tenths = deciseconds % 10;

        return String.format("%d:%02d.%d", minutes, seconds, tenths);
    }

    private static int parseFormattedString(String formatted) {
        String[] parts = formatted.split(":");
        int minutes = Integer.parseInt(parts[0]);
        String[] secondParts = parts[1].split("\\.");
        int seconds = Integer.parseInt(secondParts[0]);
        int tenths = Integer.parseInt(secondParts[1]);

        return (minutes * 60 + seconds) * 10 + tenths;
    }

}
