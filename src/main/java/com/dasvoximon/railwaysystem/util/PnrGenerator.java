package com.dasvoximon.railwaysystem.util;

import java.security.SecureRandom;

public class PnrGenerator {
    private static final String ALPHANUM = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // avoid 0/O, 1/I
    private static final SecureRandom RND = new SecureRandom();

    public static String generate(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(ALPHANUM.charAt(RND.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }
}
