package com.webtv.commons;

import java.security.SecureRandom;

public class CodeGenerator {
    public static final int MAX_CODE = 10000000;
    public static int getCode(int maxCode) {
        return new SecureRandom().nextInt(maxCode);
    }

    public static String getCodeAsString(int maxCode) {
        return String.format("%06d", getCode(maxCode));
    }

    public static String getCodeAsString() {
        return String.format("%06d", getCode(MAX_CODE));
    }
}