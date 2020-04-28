package com.webtv.commons;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtils {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final int LEFT_LIMIT = 97;
    private static final int RIGHT_LIMIT = 122;

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static boolean isBlank(String str) {
        return null == str || str.trim().isEmpty();
    }

    public static String randomString(int length) {
        Random random = new SecureRandom();
        return random.ints(LEFT_LIMIT, RIGHT_LIMIT + 1).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}