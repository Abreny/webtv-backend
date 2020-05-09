package com.webtv.commons;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtils {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final int LEFT_LIMIT = 97;
    private static final int RIGHT_LIMIT = 122;
    private static volatile SecureRandom NUMBER_GENERATOR = null;
    private static final long MSB = 0x8000000000000000L;

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
    public static String randomUniqueString() {
        SecureRandom ng = NUMBER_GENERATOR;
        if (ng == null) {
            NUMBER_GENERATOR = ng = new SecureRandom();
        }

        return Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
    }
    public static String password() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy").withZone(ZoneId.systemDefault());
        return String.format("$user%s", formatter.format(Instant.now()));
    }
}