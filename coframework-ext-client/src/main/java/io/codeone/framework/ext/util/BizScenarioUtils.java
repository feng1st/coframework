package io.codeone.framework.ext.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class for handling business identity and scenario logic.
 *
 * <p>This class provides methods for validating, normalizing, and constructing
 * hierarchical business identity and scenario strings, along with wildcard handling.
 */
@UtilityClass
public class BizScenarioUtils {

    /**
     * Represents a wildcard value used to denote any business identity or scenario.
     */
    public final String ANY = "*";

    /**
     * Separator character used to denote hierarchical levels in business identity
     * or scenario strings.
     */
    public final char SEPARATOR = '.';

    private final boolean[] IS_VALID = new boolean[256];

    static {
        for (char c = '0'; c <= '9'; c++) {
            IS_VALID[c] = true;
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            IS_VALID[c] = true;
        }
        for (char c = 'a'; c <= 'z'; c++) {
            IS_VALID[c] = true;
        }
        IS_VALID['-'] = true;
        IS_VALID['_'] = true;
    }

    /**
     * Checks whether the given code represents a wildcard or is empty.
     *
     * @param code the code to check
     * @return {@code true} if the code is null, empty, or {@code "*"}
     */
    public boolean isAny(String code) {
        return code == null || code.isEmpty() || ANY.equals(code);
    }

    /**
     * Normalizes an empty or null code to the wildcard {@code "*"}.
     *
     * @param code the code to normalize
     * @return the normalized code or {@code "*"} if the input is empty or null
     */
    public String emptyToAny(String code) {
        if (code == null || code.isEmpty()) {
            return ANY;
        }
        return code;
    }

    /**
     * Validates whether the given code adheres to the expected format.
     *
     * <p>The code must consist of alphanumeric characters, hyphens, underscores,
     * and periods as separators.
     *
     * @param code the code to validate
     * @throws IllegalArgumentException if the code is invalid
     */
    public void validate(String code) {
        if (!isValid(code)) {
            throw new IllegalArgumentException(String.format(
                    "Invalid code \"%s\". A valid code must be \"*\" or consist of '.' separated sequences of letters, digits, '-', and '_'.",
                    code));
        }
    }

    /**
     * Joins an array of strings into a single hierarchical string using the separator
     * {@code '.'}.
     *
     * @param array the array to join
     * @return the joined hierarchical string
     */
    public String join(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        if (array.length == 1) {
            return array[0];
        }
        StringBuilder sb = new StringBuilder();
        for (String code : array) {
            if (isAny(code)) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(SEPARATOR);
            }
            sb.append(code);
        }
        return sb.toString();
    }

    private boolean isValid(String code) {
        if (isAny(code)) {
            return true;
        }
        int lastSegLen = 0;
        for (int i = 0, len = code.length(); i < len; i++) {
            char c = code.charAt(i);
            if (c == SEPARATOR) {
                if (lastSegLen == 0) {
                    return false;
                }
                lastSegLen = 0;
            } else if (c < IS_VALID.length && IS_VALID[c]) {
                lastSegLen++;
            } else {
                return false;
            }
        }
        return lastSegLen > 0;
    }
}
