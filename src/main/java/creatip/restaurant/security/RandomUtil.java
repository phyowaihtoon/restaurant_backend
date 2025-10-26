package creatip.restaurant.security;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

/**
 * Utilities for generating random values used across the application.
 */
public class RandomUtil {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Random random = new Random();

    private RandomUtil() {}

    public static int getRandomServerPort() {
        int r = random.nextInt(20000);
        // the first 1024 ports are usually reserved for the OS
        return r + 1024;
    }

    public static int getPositiveInt() {
        int r = random.nextInt();
        if (r < 0) {
            r = -r;
        }
        return r;
    }

    /**
     * Generate a secure random reset key for password resets.
     * Uses URL-safe base64 of 32 bytes (around 43 characters) to avoid punctuation issues.
     */
    public static String generateResetKey() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * Generate an activation key. Uses UUID for simplicity and readability.
     */
    public static String generateActivationKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate a random password suitable for initial passwords.
     * Contains upper/lower letters and digits. Length is 12 by default.
     */
    public static String generatePassword() {
        int length = 12;
        final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String lower = upper.toLowerCase();
        final String digits = "0123456789";
        final String all = upper + lower + digits;

        StringBuilder pw = new StringBuilder(length);
        // ensure at least one upper, one lower and one digit
        pw.append(upper.charAt(secureRandom.nextInt(upper.length())));
        pw.append(lower.charAt(secureRandom.nextInt(lower.length())));
        pw.append(digits.charAt(secureRandom.nextInt(digits.length())));

        for (int i = 3; i < length; i++) {
            pw.append(all.charAt(secureRandom.nextInt(all.length())));
        }

        // simple shuffle
        char[] pwdChars = pw.toString().toCharArray();
        for (int i = pwdChars.length - 1; i > 0; i--) {
            int j = secureRandom.nextInt(i + 1);
            char tmp = pwdChars[i];
            pwdChars[i] = pwdChars[j];
            pwdChars[j] = tmp;
        }
        return new String(pwdChars);
    }
}
