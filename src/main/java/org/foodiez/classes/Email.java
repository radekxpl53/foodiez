package org.foodiez.classes;

import java.util.regex.Pattern;

public class Email {
    public static boolean isValid(String emailAddress) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern p = Pattern.compile(emailRegex);

        return emailAddress != null && p.matcher(emailAddress).matches();
    }
}
