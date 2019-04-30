package org.razu.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptHashGenerator {

    public static String passwordHash(String passwordPlaintext) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(passwordPlaintext);
        return hashedPassword;
    }

    public static boolean checkPassword(String userPassword, String storedHash) {
        boolean passwordVerified = false;
        if (null == storedHash || !storedHash.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }
        passwordVerified = BCrypt.checkpw(userPassword, storedHash);
        return (passwordVerified);
    }
}
