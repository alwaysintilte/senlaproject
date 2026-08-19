package com.senla.project.utils.hash;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.MessageDigest;

public class HashUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String encode(String input) {
        if(input == null) {
            return null;
        }
        return encoder.encode(input);
    }
    public static boolean matches(String rawPassword, String hashedPassword) {
        if(rawPassword == null || hashedPassword == null) {
            return false;
        }
        return encoder.matches(rawPassword, hashedPassword);
    }
}
