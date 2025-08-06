package com.company.helper;

import com.company.entity.Developer;

public class GenerateDeveloperId {

    public static String generateId(Developer developer) {
        String fname = developer.getFname();     // "Shailaja"
        String lname = developer.getLname();     // "K"
        int yob = developer.getYOB();            // 1998

        // Get first character of last name
        char fletter = lname.charAt(0);          // 'K'

        // Get last two digits as a two-digit string
        String digit = String.format("%02d", yob % 100);  // "98"

        // Concatenate and return final ID
        String devId = fletter + fname + digit;

        return devId;
    }
}
