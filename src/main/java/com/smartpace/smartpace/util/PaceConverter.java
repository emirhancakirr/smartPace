package com.smartpace.smartpace.util;

import java.util.regex.Pattern;

public class PaceConverter {
    private static final Pattern FORMAT_PATTERN = Pattern.compile("^(\\d+):(\\d{2})(?:\\.(\\d))?$");

    public static String decisecondsToFormattedString(int deciseconds) {

        if(deciseconds < 0){
            throw new IllegalArgumentException("Deciseconds cannot be negative");
        }
        if (deciseconds > 599990) { // 999 dakika * 60 * 10 + 59 * 10 + 9
            throw new IllegalArgumentException("Deciseconds too large");
        }


        int seconds = deciseconds/10;
        int remainingDeciseconds = deciseconds%10;
        int minutes = seconds/60;
        int remainingSeconds = seconds%60;


        return String.format("%d:%s.%d", minutes,String.format("%02d", remainingSeconds),remainingDeciseconds);

    }


    public static int formattedStringToDeciseconds(String pace) {

        if (pace == null || !FORMAT_PATTERN.matcher(pace).matches()) {
            throw new IllegalArgumentException("Invalid pace format: " + pace);
        }

        //get the minutes
        String[] splittedPace = pace.split(":");
        int minutes = Integer.valueOf(splittedPace[0]);

        
        //this part includes seconds and deciseconds
        int seconds;
        int deciseconds;
        if(splittedPace[1].contains(".")){
            splittedPace = splittedPace[1].split("\\.");
            seconds = Integer.valueOf(splittedPace[0]);
            deciseconds = Integer.valueOf(splittedPace[1]);
        }else{
            seconds = Integer.valueOf(splittedPace[1]); 
            deciseconds = 0; 
        }

        if(seconds> 59){
            throw new IllegalArgumentException("Seconds cannot be greater than 59");
        }

        if(deciseconds> 9){
            throw new IllegalArgumentException("Deciseconds cannot be greater than 9");
        }

        return ((minutes*60 + seconds)*10)+deciseconds;

    }

}
