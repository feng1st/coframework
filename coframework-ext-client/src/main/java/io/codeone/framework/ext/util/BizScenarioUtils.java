package io.codeone.framework.ext.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BizScenarioUtils {

    public final String ANY = "*";

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

    public boolean isAny(String code) {
        return code == null || code.isEmpty() || ANY.equals(code);
    }

    public String emptyToAny(String code) {
        if (code == null || code.isEmpty()) {
            return ANY;
        }
        return code;
    }

    public void validate(String code) {
        if (!isValid(code)) {
            throw new IllegalArgumentException(String.format(
                    "Invalid code '%s', should be '*', or '.' separated alphabets, numbers, '-' and '_'",
                    code));
        }
    }

    public String join(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        if (array.length == 1) {
            return array[0];
        }
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (String code : array) {
            if (isAny(code)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                builder.append(SEPARATOR);
            }
            builder.append(code);
        }
        return builder.toString();
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
