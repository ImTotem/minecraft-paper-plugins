package com.imtotem.glowing.time;

import org.bukkit.ChatColor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTime {
    public static String now() {
        LocalDateTime now = LocalDateTime.now();

        String formattedNow = now.format(DateTimeFormatter.ofPattern("hh:mm:ss yyyy-MM-dd"));

        String a = "오전";
        if ( now.getHour() >= 12 ) {
            a = "오후";
        }
        formattedNow = a + " " + formattedNow;

        ChatColor chatColor = ChatColor.WHITE;
        String dayOfTheWeek = "";
        int dayOfWeek = now.getDayOfWeek().getValue();
        switch ( dayOfWeek ) {
            case 1:
                dayOfTheWeek = "월";
                break;
            case 2:
                dayOfTheWeek = "화";
                break;
            case 3:
                dayOfTheWeek = "수";
                break;
            case 4:
                dayOfTheWeek = "목";
                break;
            case 5:
                dayOfTheWeek = "금";
                break;
            case 6:
                dayOfTheWeek = "토";
                break;
            case 7:
                dayOfTheWeek = "일";
                chatColor = ChatColor.RED;
                break;
        }

        formattedNow += " " + chatColor + dayOfTheWeek + "요일";

        return formattedNow;
    }
}
