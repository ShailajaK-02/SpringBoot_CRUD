package com.company.helper;

import com.company.entity.Developer;
import org.springframework.stereotype.Component;

@Component
//Because we will reuse this / if we mark it as component it will be loaded
public class GenerateDeveloperId {

    public static String generateId(Developer developer) {
        String fname = developer.getFname();     // "Shailaja"
        String lname = developer.getLname();     // "K"
        int yob = developer.getDob().getYear();            // 1998

        // Get first character of last name
        char fletter = lname.charAt(0);          // 'K'

        // Get last two digits as a two-digit string
        String digit = String.format("%02d", yob % 100);  // "98"

        // Concatenate and return final ID
        String devId = fletter + fname + digit;

        return devId;
    }
}
