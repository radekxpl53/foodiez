package org.foodiez.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Security {
    public static String hashPasswd(String plainPassword) {
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }

    public static boolean checkPasswd(String passwd, String hashedPasswd) {
        BCrypt.Result result = BCrypt.verifyer().verify(passwd.toCharArray(), hashedPasswd);
        return !result.verified;
    }
}
