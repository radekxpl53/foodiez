package org.foodiez.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumber {
    public static boolean isValid(String phoneNumber) {
        Pattern p = Pattern.compile("^\\d{9}$");

        return phoneNumber != null && p.matcher(phoneNumber).matches();
    }
}
