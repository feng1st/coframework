package io.codeone.framework.common.log.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

/**
 * Utilities for logfmt format encoding. Implements robust character escaping:
 * <ul>
 *   <li>Escapes \r, \n and \t
 *   <li>Replaces other ASCII control characters (0x00-0x1F) with spaces
 *   <li>Escapes double quotes (") and backslashes (\)
 *   <li>Preserves UTF-8 characters
 *   <li>Adds quotes for values containing spaces, equals signs, or special characters
 * </ul>
 */
@UtilityClass
public class LogFmtUtils {

    /**
     * Lookup table for ASCII characters (0-127) defining their logfmt-safe representations
     */
    private final String[] ESCAPING_TABLE = new String[128];

    static {
        // Initialize all ASCII control characters (0x00-0x1F) to spaces
        Arrays.fill(ESCAPING_TABLE, 0, 0x20, " ");

        // Override specific escape sequences
        ESCAPING_TABLE['\r'] = "\\r";
        ESCAPING_TABLE['\n'] = "\\n";
        ESCAPING_TABLE['\t'] = "\\t";
        ESCAPING_TABLE['"'] = "\\\"";
        ESCAPING_TABLE['\\'] = "\\\\";

        // Preserve but flag these characters for quoting
        ESCAPING_TABLE[' '] = " ";
        ESCAPING_TABLE['='] = "=";
    }

    /**
     * Encodes a string value following logfmt conventions with enhanced safety:
     *
     * <pre>{@code
     * encodeLogFmtValue(null)           -> "null"
     * encodeLogFmtValue("hello")        -> "hello"
     * encodeLogFmtValue("你好")         -> "你好"
     * encodeLogFmtValue("hello\nworld") -> "\"hello\\nworld\""
     * encodeLogFmtValue("hello world")  -> "\"hello world\""
     * encodeLogFmtValue("key=value")    -> "\"key=value\""
     * encodeLogFmtValue("co\u0001te")   -> "\"co te\""
     * }</pre>
     *
     * @param value original string value (may be null)
     * @return encoded value safe for logfmt output. Returns "null" for null input
     */
    public String encodeLogFmtValue(String value) {
        if (value == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        boolean needsQuotes = false;

        for (char c : value.toCharArray()) {
            if (c < ESCAPING_TABLE.length) {
                String escaped = ESCAPING_TABLE[c];
                if (escaped != null) {
                    sb.append(escaped);
                    needsQuotes = true;
                    continue;
                }
            }
            sb.append(c);
        }

        // Force quotes for empty strings or values needing escaping
        if (sb.length() == 0 || needsQuotes) {
            return "\"" + sb + "\"";
        }
        return sb.toString();
    }
}
